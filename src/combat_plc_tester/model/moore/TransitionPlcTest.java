// Package
package combat_plc_tester.model.moore;

// Imports
import combat_plc_tester.model.IO.InputDWord;
import combat_plc_tester.model.IO.InputReal;
import combat_plc_tester.model.IO.InputByte;
import combat_plc_tester.model.IO.InputWord;
import combat_plc_tester.model.IO.InputBit;
import combat_plc_tester.model.IO.InputDInt;
import combat_plc_tester.model.IO.InputInt;
import combat_plc_tester.model.IO.Input;
import Moka7.S7;
import combat_plc_tester.controller.TransitionTestObserver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class: TransitionPlcTest
 * 
 * Purpose: implements the `TransitionTest` abstract class using the Template Method pattern.
 * It provides concrete implementations for handling inputs, processing transition inputs, 
 * and writing data to a Siemens S7 PLC data building block using the S7Client library from Moka7.
 *
 *  Design Patterns:
 * - **Template Method Pattern:**
 *  Concrete implementation for TransitionPlcTest.
 * 
 * Key Responsibilities:
 * 1. Manages communication with a Siemens PLC via the S7Client class.
 * 2. Ensures that data is structured into a single contiguous byte array.
 * 3. Implements methods to notify observers about the status and details of the current transition test.
 * 
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class TransitionPlcTest extends TransitionTest {
    
    public TransitionPlcTest(){
         this.transitiontestobservers = new ArrayList<>();
    }
    
    @Override
    public void addTransitionTestObserver(TransitionTestObserver transitiontestobserver){
        transitiontestobservers.add(transitiontestobserver);
    }
    
    @Override
    public void removeTransitionTestObserver(TransitionTestObserver transitiontestobserver){
        transitiontestobservers.remove(transitiontestobserver);
    }
    
    @Override
    public void notifyTransitionTestObservers(String message){
         for (TransitionTestObserver transitiontestobserver : transitiontestobservers) {
            transitiontestobserver.update(message);
        }
    }
    
    /**
     * Handles the initialization and preparation of PLC inputs.
     *
     * Processes the `inputList` to ensure that all inputs are set
     * to their initial values and updates the `inputvaluemap` with the
     * corresponding byte representation for each input type.
     *
     * The method performs the following tasks: 
     * 1. Iterates through the list of `Input` objects (`inputList`). 
     * 2. For each input: - Checks if its starting address already exists 
     * in the `inputvaluemap`. - If the address exists, updates the 
     * corresponding byte array with the input's initial value. 
     * - If the address does not exist, creates a new byte array and
     * initializes it with the input's value. 
     * 3. Handles different input types (`InputBit`, `InputByte`, `InputWord`, etc.) 
     * and applies the appropriate encoding using the Moka7 library methods 
     * (`S7.SetBitAt`, `S7.SetWordAt`, etc.). 
     * 4. Updates the `inputvaluemap` with the modified or newly created
     * byte arrays.
     */
    @Override
    protected void handleInputs() {
        for (Input input : inputList) {
            if (inputvaluemap.containsKey(input.getStartAddress())) {
                if (input instanceof InputBit) {
                    input.setValue(input.getInitialValue());
                    byte[] db1ReadBuffer = inputvaluemap.get(input.getStartAddress());
                    S7.SetBitAt(db1ReadBuffer, 0, input.getBitAddress(), (boolean) input.getValue());
                    inputvaluemap.put(input.getStartAddress(), db1ReadBuffer);
                } else if (input instanceof InputByte) {
                    input.setValue(input.getInitialValue());
                    byte[] db1ReadBuffer = inputvaluemap.get(input.getStartAddress());
                    db1ReadBuffer[0] = (byte) ((short) input.getValue() & 0xFF);
                    inputvaluemap.put(input.getStartAddress(), db1ReadBuffer);
                } else if (input instanceof InputWord) {
                    input.setValue(input.getInitialValue());
                    byte[] db1ReadBuffer = inputvaluemap.get(input.getStartAddress());
                    S7.SetWordAt(db1ReadBuffer, 0, (int) input.getValue());
                    inputvaluemap.put(input.getStartAddress(), db1ReadBuffer);
                } else if (input instanceof InputDWord) {
                    input.setValue(input.getInitialValue());
                    byte[] db1ReadBuffer = inputvaluemap.get(input.getStartAddress());
                    S7.SetDWordAt(db1ReadBuffer, 0, (long) input.getValue());
                    inputvaluemap.put(input.getStartAddress(), db1ReadBuffer);
                } else if (input instanceof InputInt) {
                    input.setValue(input.getInitialValue());
                    byte[] db1ReadBuffer = inputvaluemap.get(input.getStartAddress());
                    S7.SetShortAt(db1ReadBuffer, 0, (short) input.getValue());
                    inputvaluemap.put(input.getStartAddress(), db1ReadBuffer);
                } else if (input instanceof InputDInt) {
                    input.setValue(input.getInitialValue());
                    byte[] db1ReadBuffer = inputvaluemap.get(input.getStartAddress());
                    S7.SetDIntAt(db1ReadBuffer, 0, (int) input.getValue());
                    inputvaluemap.put(input.getStartAddress(), db1ReadBuffer);
                } else if (input instanceof InputReal) {
                    input.setValue(input.getInitialValue());
                    byte[] db1ReadBuffer = inputvaluemap.get(input.getStartAddress());
                    S7.SetFloatAt(db1ReadBuffer, 0, (float) input.getValue());
                    inputvaluemap.put(input.getStartAddress(), db1ReadBuffer);
                }
            } else {
                if (input instanceof InputBit) {
                    input.setValue(input.getInitialValue());
                    byte[] db1ReadBuffer = new byte[1];
                    S7.SetBitAt(db1ReadBuffer, 0, input.getBitAddress(), (boolean) input.getValue());
                    inputvaluemap.put(input.getStartAddress(), db1ReadBuffer);

                } else if (input instanceof InputByte) {
                    input.setValue(input.getInitialValue());
                    byte[] db1ReadBuffer = new byte[1];
                    db1ReadBuffer[0] = (byte) ((short) input.getValue() & 0xFF);
                    inputvaluemap.put(input.getStartAddress(), db1ReadBuffer);
                } else if (input instanceof InputWord) {
                    input.setValue(input.getInitialValue());
                    byte[] db1ReadBuffer = new byte[4];
                    S7.SetShortAt(db1ReadBuffer, 0, (int) input.getValue());
                    inputvaluemap.put(input.getStartAddress(), db1ReadBuffer);
                } else if (input instanceof InputDWord) {
                    input.setValue(input.getInitialValue());
                    byte[] db1ReadBuffer = new byte[8];
                    S7.SetDWordAt(db1ReadBuffer, 0, (long) input.getValue());
                    inputvaluemap.put(input.getStartAddress(), db1ReadBuffer);
                } else if (input instanceof InputInt) {
                    input.setValue(input.getInitialValue());
                    byte[] db1ReadBuffer = new byte[2];
                    S7.SetShortAt(db1ReadBuffer, 0, (short) input.getValue());
                    inputvaluemap.put(input.getStartAddress(), db1ReadBuffer);
                } else if (input instanceof InputDInt) {
                    input.setValue(input.getInitialValue());
                    byte[] db1ReadBuffer = new byte[4];
                    S7.SetDIntAt(db1ReadBuffer, 0, (int) input.getValue());
                    inputvaluemap.put(input.getStartAddress(), db1ReadBuffer);
                } else if (input instanceof InputReal) {
                    input.setValue(input.getInitialValue());
                    byte[] db1ReadBuffer = new byte[4];
                    S7.SetFloatAt(db1ReadBuffer, 0, (float) input.getValue());
                    inputvaluemap.put(input.getStartAddress(), db1ReadBuffer);
                }
            }
        }
    }

    @Override
    protected void handleTransitionInputs() {
        List<Input> transitioninputList = transition.getInputs();
        for (Input transitioninput : transitioninputList) {
            if (transitioninput instanceof InputBit) {
                byte[] bytevaluebuffer = inputvaluemap.get(transitioninput.getStartAddress());
                S7.SetBitAt(bytevaluebuffer, 0, transitioninput.getBitAddress(), (boolean) transitioninput.getValue());
                inputvaluemap.put(transitioninput.getStartAddress(), bytevaluebuffer);
            } else if (transitioninput instanceof InputByte) {
                byte[] bytevaluebuffer = inputvaluemap.get(transitioninput.getStartAddress());
                bytevaluebuffer[0] = (byte) ((short) transitioninput.getValue() & 0xFF);
                inputvaluemap.put(transitioninput.getStartAddress(), bytevaluebuffer);
            } else if (transitioninput instanceof InputWord) {
                byte[] bytevaluebuffer = inputvaluemap.get(transitioninput.getStartAddress());
                S7.SetWordAt(bytevaluebuffer, 0, (int) transitioninput.getValue());
                inputvaluemap.put(transitioninput.getStartAddress(), bytevaluebuffer);
            } else if (transitioninput instanceof InputInt) {
                byte[] bytevaluebuffer = inputvaluemap.get(transitioninput.getStartAddress());
                S7.SetShortAt(bytevaluebuffer, 0, (short) transitioninput.getValue());
                inputvaluemap.put(transitioninput.getStartAddress(), bytevaluebuffer);
            } else if (transitioninput instanceof InputDInt) {
                byte[] bytevaluebuffer = inputvaluemap.get(transitioninput.getStartAddress());
                S7.SetDIntAt(bytevaluebuffer, 0, (int) transitioninput.getValue());
                inputvaluemap.put(transitioninput.getStartAddress(), bytevaluebuffer);
            } else if (transitioninput instanceof InputReal) {
                byte[] bytevaluebuffer = inputvaluemap.get(transitioninput.getStartAddress());
                S7.SetFloatAt(bytevaluebuffer, 0, (float) transitioninput.getValue());
                inputvaluemap.put(transitioninput.getStartAddress(), bytevaluebuffer);
            }
        }
    }

     /**
     * Processes the inputs associated with a transition by updating the
     * respective data buffer and write the data to the S7 PLC data building block.
     * 
     * Iterates through the inputs of the transition and modifies their 
     * corresponding values in the `inputvaluemap`. 
     * Each input type (Bit, Byte, Word, etc.) is handled
     * specifically to update the buffer with the correct data representation.
     *
     * The method controls the pdu length. If it data to  the max PDU size it wil
     * convert the data into bytesarrays 
     * 
     * - InputBit: Updates a specific bit within the buffer. 
     * - InputByte: Updates the first byte of the buffer. 
     * - InputWord: Sets a word (16-bit value) in the buffer. 
     * - InputInt: Sets a short (16-bit signed value) in the buffer. 
     * - InputDInt: Sets a double integer (32-bit signed value) in the buffer. 
     * - InputReal: Sets a floating-point value (32-bit) in the buffer.
     */
    @Override
    protected void writeInputsToPlc() {
        notifyTransitionTestObservers("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
        notifyTransitionTestObservers("TransitionID: " + transition.getTransitionID());  
        // Convert the bytes in the input value map into a single contiguous block of bytes.
        byte[] bytes = convertToByteArray(inputvaluemap);
            // Writing the data to a S7 PLC data building block.
            plcclient.WriteArea(S7.S7AreaDB, databuildingblocknumberinputs, 0, bytes.length, bytes);
        for (Input input : transition.getInputs()) {
            notifyTransitionTestObservers("InputName: " + input.getName());
            notifyTransitionTestObservers("Input Adress: " + input.getStartAddress());
            if (input instanceof InputBit) {
                notifyTransitionTestObservers("Input BitAdress: " + input.getBitAddress());
            }
            notifyTransitionTestObservers("Input Value: " + input.getValue());
        }
        notifyTransitionTestObservers("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
       
    }
    
    /**
    * Combines all data from the inputValueMap into a single contiguous byte array, 
    * starting at address 0 and continuing up to the highest address in the input.
    * Any gaps between data blocks are filled with 0x00.
    * 
    * This ensures that all data is placed in the correct order without segmentation 
    * or size limitations on the final byte array.
    *
    * @param inputValueMap A map where the start addresses serve as keys and the corresponding byte arrays as values.
    * @return A single byte array containing
    */
    public byte[] convertToByteArray(HashMap<Short, byte[]> inputValueMap) {
        int maxaddress = 0;
        for (Map.Entry<Short, byte[]> entry : inputValueMap.entrySet()) {
            short startAddress = entry.getKey();
            int endAddress = startAddress + entry.getValue().length;
            maxaddress = Math.max(maxaddress, endAddress);
        }
        byte[] byteArray = new byte[maxaddress];
        Arrays.fill(byteArray, (byte) 0x00);
        for (Map.Entry<Short, byte[]> entry : inputValueMap.entrySet()) {
            short startAddress = entry.getKey();
            byte[] value = entry.getValue();
            System.arraycopy(value, 0, byteArray, startAddress, value.length);
        }
        return byteArray;
    }
}
