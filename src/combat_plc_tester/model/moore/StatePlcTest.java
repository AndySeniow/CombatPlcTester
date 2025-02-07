// Package
package combat_plc_tester.model.moore;

// Imports
import combat_plc_tester.model.IO.OutputWord;
import combat_plc_tester.model.IO.OutputDInt;
import combat_plc_tester.model.IO.OutputByte;
import combat_plc_tester.model.IO.Output;
import combat_plc_tester.model.IO.OutputDWord;
import combat_plc_tester.model.IO.OutputReal;
import combat_plc_tester.model.IO.OutputInt;
import combat_plc_tester.model.IO.OutputBit;
import Moka7.S7;
import combat_plc_tester.controller.StateTestObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * Class: StatePlcTest
 *
 * Purpose: Implements the `StateTest` abstract class using the Template Method pattern.
 * This class provides concrete implementations for managing PLC outputs, 
 * reading data from a Siemens S7 PLC, and comparing the expected outputs of a state
 * with the actual outputs read from the PLC.
 *
 * Design Patterns:
 * - **Template Method Pattern:**
 *   - Provides concrete implementation for `StateTest` by overriding abstract methods such as:
 *     - `handleOutputs()`
 *     - `readOutputsFromPlc()`
 *     - `compareStateOutputs(boolean aftertime)`
 *   - Ensures a consistent workflow while allowing flexibility for specific output handling.
 * - **Observer Pattern:**
 *   - Manages a list of observers to notify them of the test progress, results, and details about the PLC outputs.
 *
 * Key Responsibilities:
 * 1. **PLC Communication:**
 *    - Uses the `S7Client` class from the Moka7 library to interact with a Siemens S7 PLC.
 *    - Reads output values from a designated data block in the PLC.
 * 2. Ensures that data is structured into a single contiguous byte array.
 * 3. **Output Handling:**
 *    - Initializes outputs with their initial values for "before" and "after" time conditions.
 *    - Compares the expected output values with the actual values read from the PLC.
 * 4. **Observer Notifications:**
 *    - Notifies observers with detailed information about output states, including mismatches.
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class StatePlcTest extends StateTest {
     
    public StatePlcTest(){
        this.statetestobservers = new ArrayList<>();
    }
     
    @Override
    public void addStateTestObserver(StateTestObserver statetestobserver){
        statetestobservers.add(statetestobserver);
    }
    
    @Override
    public void removeStateTestObserver(StateTestObserver statetestobserver){
        statetestobservers.remove(statetestobserver);
    }
    
    @Override
    public void notifyStateTestObservers(String message){
         for (StateTestObserver statetestobserver : statetestobservers) {
            statetestobserver.update(message);
        }
    }
    
   /**
    * Determines the size of the data required for a specific output type.
    *
    * Calculates the number of bytes needed to represent the given output
    *   based on its specific type (bit, byte, word, etc.).
    *
    * @param output Output - The output object for which the data size is to be determined.
    * @return int - The size in bytes required for the given output type.
    */
    private short getDataSize(Output output) {
    if (output instanceof OutputBit) return 1; // Bits = 1 byte 
    if (output instanceof OutputByte) return 1; // Byte = 1 byte
    if (output instanceof OutputWord) return 4; // Word = 2 bytes
    if (output instanceof OutputDWord) return 8; // DWord = 4 bytes
    if (output instanceof OutputInt) return 2; // Unsigned Integer = 4 bytes
    if (output instanceof OutputDInt) return 4; // Integer = 8 bytes
    if (output instanceof OutputReal) return 4; // Float = 8 bytes
    return 1; 
}
    
    /**
    * Handles the initialization of output values for the state test.
    *
    * Prepares the outputs by setting their initial values for both "before" and "after" time conditions.
    *
    * @override Indicates that this method overrides an abstract or inherited implementation from the parent class.
    */
    @Override
    protected void handleOutputs() {
        for (Output output : outputList) {
            output.setValueBeforeTimeCondition(output.getInitialValue());
            output.setValueAfterTimeCondition(output.getInitialValue());
        }
    }   
    
    /**
     * Reads output values from a Siemens S7 PLC using a data block and stores
     * them in a HashMap.
     *
     * This method reads all output data in a single, unrestricted read
     * operation, retrieving all bytes from the lowest to the highest address
     * found in the output list. The data is then stored in the output value map
     * (`outputvaluemap`), where each entry consists of a start address and its
     * corresponding byte values.
     *
     * The method ensures that all data is correctly mapped to its respective
     * addresses, allowing efficient retrieval and processing without segmenting
     * the read operation based on PDU size limitations.
     */
    @Override
    protected void readOutputsFromPlc() {
        short minaddress = 0;
        short maxaddress = 0;
        for (Output ou : outputList) {
            short startaddress = ou.getStartAddress();
            short size = getDataSize(ou);
            short endaddress = (short) (startaddress + size - 1);
            maxaddress = (short) Math.max(maxaddress, endaddress);
        }
        int bytesToRead = (maxaddress - minaddress) + 1;
        byte[] byteArray = new byte[bytesToRead];
        int result = plcclient.ReadArea(S7.S7AreaDB, databuildingblocknumberoutputs, minaddress, bytesToRead, byteArray);
        if (result != 0) {
            return;
        }
        for (Output ou : outputList) {
            short startaddress = ou.getStartAddress();
            short size = getDataSize(ou);
            int offset = startaddress - minaddress;
            byte[] byteValueBuffer = new byte[size];
            System.arraycopy(byteArray, offset, byteValueBuffer, 0, size);
            outputvaluemap.put(startaddress, byteValueBuffer);
        }
    }

    /**
    * Compares the expected output values of the state with the actual output values read from the PLC.
    * The method takes the value of the output in the PLC from the output value map and compares it with
    * the value of the outputs in the state or in the list of outputs of the test.
    * Validates the outputs of the PLC against the model's expected outputs before and/or after the specified time condition.
    * Provides detailed logging and notification for each output comparison, including mismatches.
    * Ensures that all output types are handled appropriately (bit, byte, word, etc.).
    * 
    * Parameters:
    * @param aftertime boolean - Determines whether to compare outputs for the "after time condition".
    *
    * Returns:
    * @return boolean - `true` if all outputs match the expected values; `false` if any mismatch is found.
    */
    @Override
    protected boolean compareStateOutputs(boolean aftertime) {
        // All outputs in the test.
        List<Output> copiedoutputList = new ArrayList<>(outputList);
        // All outputs in the state.
        List<Output> stateoutputList = state.getOutputs();
        boolean bitoutputvalue = false;
        // Iterate outputs in the test.
        for (Output ou : outputList) {
            byte[] bytes;
            if (ou instanceof OutputBit) {  
                bytes = outputvaluemap.get(ou.getStartAddress());
                short bitadres = ((OutputBit) ou).getBitAddress();
                for (Output outstate : stateoutputList) {
                    if (ou.getOutputID().equals(outstate.getOutputID())) {
                        copiedoutputList.remove(ou);
                        if (!aftertime) {
                            bitoutputvalue = (boolean) outstate.getValueBeforeTimeCondition();
                        } else {
                            bitoutputvalue = (boolean) outstate.getValueAfterTimeCondition();
                        }
                        boolean plcvalue = S7.GetBitAt(bytes, 0, bitadres);
                        notifyObservers(outstate);
                        notifyStateTestObservers("PLCvalue: " + plcvalue);
                        if (plcvalue == bitoutputvalue) {
                            notifyStateTestObservers("PLC value equal to Model value ");
                        } else {
                            notifyStateTestObservers("PLC value not equal to Model value ");
                        }
                        notifyStateTestObservers("-------------------------------------------------------------");
                        if (plcvalue != bitoutputvalue) {
                            return false;
                        }
                    }
                }
            } else if (ou instanceof OutputByte) {
                short plcvalues = (short) Byte.toUnsignedInt(outputvaluemap.get(ou.getStartAddress())[0]);
                for (Output outstate : stateoutputList) {
                    if (ou.getOutputID().equals(outstate.getOutputID())) {
                        copiedoutputList.remove(ou);
                        if (!aftertime) {
                            notifyObservers(outstate);
                            notifyStateTestObservers("PLCvalue: " + plcvalues);
                            if (plcvalues == (short) outstate.getValueBeforeTimeCondition()) {
                                notifyStateTestObservers("PLC value equal to Model value ");
                            } else {
                                notifyStateTestObservers("PLC value not equal to Model value ");
                            }
                            notifyStateTestObservers("-------------------------------------------------------------");
                            if (plcvalues != (short) outstate.getValueBeforeTimeCondition()) {
                                return false;
                            }
                        } else {
                            notifyObservers(outstate);
                            notifyStateTestObservers("PLCvalue: " + plcvalues);
                            if (plcvalues == (short) outstate.getValueAfterTimeCondition()) {
                                notifyStateTestObservers("PLC value equal to Model value ");
                            } else {
                                notifyStateTestObservers("PLC value not equal to Model value ");
                            }
                            notifyStateTestObservers("-------------------------------------------------------------");
                            if (plcvalues != (short) outstate.getValueAfterTimeCondition()) {
                                return false;
                            }
                        }
                    }
                }
            } else if (ou instanceof OutputWord) {
                byte[] plcvalues = outputvaluemap.get(ou.getStartAddress());
                for (Output outstate : stateoutputList) {
                    if (ou.getOutputID().equals(outstate.getOutputID())) {
                        copiedoutputList.remove(ou);
                        if (!aftertime) {
                            notifyObservers(outstate);
                            notifyStateTestObservers("PLC value: " + S7.GetWordAt(plcvalues, 0));
                            if (S7.GetWordAt(plcvalues, 0) == (int) outstate.getValueBeforeTimeCondition()) {
                                notifyStateTestObservers("PLC value equal to Model value ");
                            } else {
                                notifyStateTestObservers("PLC value not equal to Model value ");
                            }
                            notifyStateTestObservers("-------------------------------------------------------------");
                            if (S7.GetWordAt(plcvalues, 0) != (int) outstate.getValueBeforeTimeCondition()) {
                                return false;
                            }
                        } else {
                            notifyObservers(outstate);
                            notifyStateTestObservers("PLC value: " + S7.GetWordAt(plcvalues, 0));
                            if (S7.GetWordAt(plcvalues, 0) == (int) outstate.getValueAfterTimeCondition()) {
                                notifyStateTestObservers("PLC value equal to Model value ");
                            } else {
                                notifyStateTestObservers("PLC value not equal to Model value ");
                            }
                            notifyStateTestObservers("-------------------------------------------------------------");
                            if (S7.GetWordAt(plcvalues, 0) != (int) outstate.getValueAfterTimeCondition()) {
                                return false;
                            }
                        }
                    }
                }
            } else if (ou instanceof OutputDWord) {
                byte[] plcvalues = outputvaluemap.get(ou.getStartAddress());
                for (Output outstate : stateoutputList) {
                    if (ou.getOutputID().equals(outstate.getOutputID())) {
                        copiedoutputList.remove(ou);
                        if (!aftertime) {
                            notifyObservers(outstate);
                            notifyStateTestObservers("PLC value: " + S7.GetDWordAt(plcvalues, 0));
                            if (S7.GetDWordAt(plcvalues, 0) == (long) outstate.getValueBeforeTimeCondition()) {
                                notifyStateTestObservers("PLC value equal to Model value ");
                            } else {
                                notifyStateTestObservers("PLC value not equal to Model value ");
                            }
                            notifyStateTestObservers("-------------------------------------------------------------");
                            if (S7.GetDWordAt(plcvalues, 0) != (long) outstate.getValueBeforeTimeCondition()) {
                                return false;
                            }
                        } else {
                            notifyObservers(outstate);
                            notifyStateTestObservers("PLC value: " + S7.GetDWordAt(plcvalues, 0));
                            if (S7.GetDWordAt(plcvalues, 0) == (long) outstate.getValueAfterTimeCondition()) {
                                notifyStateTestObservers("PLC value equal to Model value ");
                            } else {
                                notifyStateTestObservers("PLC value not equal to Model value ");
                            }
                            notifyStateTestObservers("-------------------------------------------------------------");
                            if (S7.GetDWordAt(plcvalues, 0) != (long) outstate.getValueAfterTimeCondition()) {
                                return false;
                            }
                        }
                    }
                }
            } else if (ou instanceof OutputInt) {
                byte[] plcvalues = outputvaluemap.get(ou.getStartAddress());
                for (Output outstate : stateoutputList) {
                    if (ou.getOutputID().equals(outstate.getOutputID())) {
                        copiedoutputList.remove(ou);
                        if (!aftertime) {
                            notifyObservers(outstate);
                            notifyStateTestObservers("PLC value: " + S7.GetShortAt(plcvalues, 0));
                            if (S7.GetShortAt(plcvalues, 0) == (short) outstate.getValueBeforeTimeCondition()) {
                                notifyStateTestObservers("PLC value equal to Model value ");
                            } else {
                                notifyStateTestObservers("PLC value not equal to Model value ");
                            }
                            notifyStateTestObservers("-------------------------------------------------------------");
                            if (S7.GetShortAt(plcvalues, 0) != (short) outstate.getValueBeforeTimeCondition()) {
                                return false;
                            }
                        } else {
                            notifyObservers(outstate);
                            notifyStateTestObservers("PLC value: " + S7.GetShortAt(plcvalues, 0));
                            if (S7.GetShortAt(plcvalues, 0) == (short) outstate.getValueAfterTimeCondition()) {
                                notifyStateTestObservers("PLC value equal to Model value ");
                            } else {
                                notifyStateTestObservers("PLC value not equal to Model value ");
                            }
                            notifyStateTestObservers("-------------------------------------------------------------");
                            if (S7.GetShortAt(plcvalues, 0) != (short) outstate.getValueAfterTimeCondition()) {
                                return false;
                            }
                        }
                    }
                }
            } else if (ou instanceof OutputDInt) {
                byte[] plcvalues = outputvaluemap.get(ou.getStartAddress());
                for (Output outstate : stateoutputList) {
                    if (ou.getOutputID().equals(outstate.getOutputID())) {
                        copiedoutputList.remove(ou);
                        if (!aftertime) {
                            notifyObservers(outstate);
                            notifyStateTestObservers("PLC value: " + S7.GetDIntAt(plcvalues, 0));
                            if (S7.GetDIntAt(plcvalues, 0) == (int) outstate.getValueBeforeTimeCondition()) {
                                notifyStateTestObservers("PLC value equal to Model value ");
                            } else {
                                notifyStateTestObservers("PLC value not equal to Model value ");
                            }
                            notifyStateTestObservers("-------------------------------------------------------------");
                            if (S7.GetDIntAt(plcvalues, 0) != (int) outstate.getValueBeforeTimeCondition()) {
                                return false;
                            }
                        } else {
                            notifyObservers(outstate);
                            notifyStateTestObservers("PLC value: " + S7.GetDIntAt(plcvalues, 0));
                            if (S7.GetDIntAt(plcvalues, 0) == (int) outstate.getValueAfterTimeCondition()) {
                                notifyStateTestObservers("PLC value equal to Model value ");
                            } else {
                                notifyStateTestObservers("PLC value not equal to Model value ");
                            }
                            notifyStateTestObservers("-------------------------------------------------------------");
                            if (S7.GetDIntAt(plcvalues, 0) != (int) outstate.getValueAfterTimeCondition()) {
                                return false;
                            }
                        }
                    }
                }
            } else if (ou instanceof OutputReal) {
                byte[] plcvalues = outputvaluemap.get(ou.getStartAddress());
                for (Output outstate : stateoutputList) {
                    if (ou.getOutputID().equals(outstate.getOutputID())) {
                        copiedoutputList.remove(ou);
                        if (!aftertime) {
                            notifyObservers(outstate);
                            notifyStateTestObservers("PLC value: " + S7.GetFloatAt(plcvalues, 0));
                            if (S7.GetFloatAt(plcvalues, 0) == (float) outstate.getValueBeforeTimeCondition()) {
                                notifyStateTestObservers("PLC value equal to Model value ");
                            } else {
                                notifyStateTestObservers("PLC value not equal to Model value ");
                            }
                            notifyStateTestObservers("-------------------------------------------------------------");
                            if (S7.GetFloatAt(plcvalues, 0) != (float) outstate.getValueBeforeTimeCondition()) {
                                return false;
                            }
                        } else {
                            notifyObservers(outstate);
                            notifyStateTestObservers("PLC value: " + S7.GetFloatAt(plcvalues, 0));
                            if (S7.GetFloatAt(plcvalues, 0) == (float) outstate.getValueAfterTimeCondition()) {
                                notifyStateTestObservers("PLC value equal to Model value ");
                            } else {
                                notifyStateTestObservers("PLC value not equal to Model value ");
                            }
                            notifyStateTestObservers("-------------------------------------------------------------");
                            if (S7.GetFloatAt(plcvalues, 0) != (float) outstate.getValueAfterTimeCondition()) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        // Iterate outputs in the state.
        for (Output ou : copiedoutputList) {
            byte[] bytes;
            if (ou instanceof OutputBit) {
                bytes = outputvaluemap.get(ou.getStartAddress());
                short bitadres = ((OutputBit) ou).getBitAddress();
                boolean plcvalue = S7.GetBitAt(bytes, 0, bitadres);
                notifyObservers(ou);
                notifyStateTestObservers("PLC value: " + plcvalue);
                if (plcvalue == bitoutputvalue) {
                    notifyStateTestObservers("PLC value equal to Model value ");
                } else {
                    notifyStateTestObservers("PLC value not equal to Model value ");
                }
                if (plcvalue != (boolean) ou.getValueBeforeTimeCondition()) {
                    return false;
                }
            } else if (ou instanceof OutputByte) {
                short plcvalues = (short) Byte.toUnsignedInt(outputvaluemap.get(ou.getStartAddress())[0]);
                notifyObservers(ou);
                notifyStateTestObservers("PLCvalue: " + plcvalues);
                if (plcvalues == (short) ou.getValueBeforeTimeCondition()) {
                    notifyStateTestObservers("PLC value equal to Model value ");
                } else {
                    notifyStateTestObservers("PLC value not equal to Model value ");
                }
                if (plcvalues != (short) ou.getValueBeforeTimeCondition()) {
                    return false;
                }
            } else if (ou instanceof OutputWord) {
                byte[] plcvalues = outputvaluemap.get(ou.getStartAddress());
                notifyObservers(ou);
                notifyStateTestObservers("PLCvalue: " + S7.GetWordAt(plcvalues, 0));
                if (S7.GetWordAt(plcvalues, 0) == (int) ou.getValueBeforeTimeCondition()) {
                    notifyStateTestObservers("PLC value equal to Model value ");
                } else {
                    notifyStateTestObservers("PLC value not equal to Model value ");
                }
                if (S7.GetWordAt(plcvalues, 0) != (int) ou.getValueBeforeTimeCondition()) {
                    return false;
                }
            } else if (ou instanceof OutputDWord) {
                byte[] plcvalues = outputvaluemap.get(ou.getStartAddress());
                notifyObservers(ou);
                notifyStateTestObservers("PLCvalue: " + S7.GetDWordAt(plcvalues, 0));
                if (S7.GetDWordAt(plcvalues, 0) == (long) ou.getValueBeforeTimeCondition()) {
                    notifyStateTestObservers("PLC value equal to Model value ");
                } else {
                    notifyStateTestObservers("PLC value not equal to Model value ");
                }
                if (S7.GetDWordAt(plcvalues, 0) != (long) ou.getValueBeforeTimeCondition()) {
                    return false;
                }
            } else if (ou instanceof OutputInt) {
                byte[] plcvalues = outputvaluemap.get(ou.getStartAddress());
                notifyObservers(ou);
                notifyStateTestObservers("PLCvalue: " + S7.GetShortAt(plcvalues, 0));
                if (S7.GetShortAt(plcvalues, 0) == (short) ou.getValueBeforeTimeCondition()) {
                    notifyStateTestObservers("PLC value equal to Model value ");
                } else {
                    notifyStateTestObservers("PLC value not equal to Model value ");
                }
                if (S7.GetShortAt(plcvalues, 0) != (short) ou.getValueBeforeTimeCondition()) {
                    return false;
                }
            } else if (ou instanceof OutputDInt) {
                byte[] plcvalues = outputvaluemap.get(ou.getStartAddress());
                notifyObservers(ou);
                notifyStateTestObservers("PLCvalue: " + S7.GetDIntAt(plcvalues, 0));
                if (S7.GetDIntAt(plcvalues, 0) == (int) ou.getValueBeforeTimeCondition()) {
                    notifyStateTestObservers("PLC value equal to Model value ");
                } else {
                    notifyStateTestObservers("PLC value not equal to Model value ");
                }
                if (S7.GetDIntAt(plcvalues, 0) != (int) ou.getValueBeforeTimeCondition()) {
                    return false;
                }
            } else if (ou instanceof OutputReal) {
                byte[] plcvalues = outputvaluemap.get(ou.getStartAddress());
                notifyObservers(ou);
                notifyStateTestObservers("PLCvalue: " + S7.GetFloatAt(plcvalues, 0));
                if (S7.GetFloatAt(plcvalues, 0) == (float) ou.getValueBeforeTimeCondition()) {
                    notifyStateTestObservers("PLC value equal to Model value ");
                } else {
                    notifyStateTestObservers("PLC value not equal to Model value ");
                }
                if (S7.GetFloatAt(plcvalues, 0) != (float) ou.getValueBeforeTimeCondition()) {
                    return false;
                }
            }
        }
        return true;
    }
   
    /**
    * Notifies observers with detailed information about a specific output state.
    *
    * Parameters:
    * @param outputstate Output - The specific output object whose details are being logged and notified.
    */
    public void notifyObservers(Output outputstate) {
        notifyStateTestObservers("-------------------------------------------------------------");
        notifyStateTestObservers("StateID: " + state.getStateID());
        notifyStateTestObservers("OutputName: " + outputstate.getName());
        notifyStateTestObservers("OutputID: " + outputstate.getOutputID());
        notifyStateTestObservers("OutputStartAddress: " + outputstate.getStartAddress());
        notifyStateTestObservers("OutputBitAddress: " + outputstate.getBitAddress());
        notifyStateTestObservers("OutputInitialValue: " + outputstate.getInitialValue());
        notifyStateTestObservers("OutputValueBeforeTimeCondition: " + outputstate.getValueBeforeTimeCondition());
        notifyStateTestObservers("OutputValueAfterTimeCondition: " + outputstate.getValueAfterTimeCondition());
    }
}
