// Package
package combat_plc_tester.model.moore;

// Imports
import Moka7.S7Client;
import combat_plc_tester.controller.StateTestObserver;
import combat_plc_tester.model.IO.Output;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;

/**
 * Abstract Class: StateTest
 *
 * Purpose:
 * - Provides a framework for performing state tests on a Siemens PLC.
 * - Implements the **Template Method design pattern** to define a consistent structure for state testing.
 * - Subclasses are required to implement specific steps in the test process, ensuring flexibility for different test types.
 *
 * Design Patterns:
 * - **Template Method Pattern:**
 *   - The `doStateTest()` method provides a fixed sequence of operations:
 *     1. Initialize outputs for testing (`initializeOutputs()`).
 *     2. Read output values from the PLC (`readPlcOutputs()`).
 *     3. Compare state outputs with PLC outputs, both before and after a specified time condition (`compareStateOutputs()`).
 * If TimerCondition - TimerConditionTolerance <= 2 * cycletime, then there are four reading times:
 * 1. First, immediately read and compare the output values with the values before the time condition.
 * 2. Wait for (TimerCondition - TimerConditionTolerance) / 2.
 * 3. Second, read and compare the output values with the values before the time condition.
 * 4. Wait for (TimerCondition - TimerConditionTolerance) / 2.
 * 5. Third, read and compare the output values with the values before the time condition.
 * 6. Wait for the remaining time.
 * 7. Finally, read and compare the output values with the values after the time condition.
 *   - Subclasses define the specific implementation for these steps through abstract methods.
 * - **Observer Pattern:**
 *   - Allows observers to monitor the progress or results of a state test through methods for adding, removing, 
 *     and notifying observers.
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public abstract class StateTest {

    // S7 Siemens PLC client used to read outputs and communicate with the PLC.
    protected S7Client plcclient;
    // The data block in the PLC where the output values will be read from.
    protected short databuildingblocknumberoutputs;
    // The list of all output objects used in the test.
    protected List<Output> outputList;
    // A map containing the start address of each output as the key and the corresponding output value in byte form as the value.
    protected HashMap<Short, byte[]> outputvaluemap = new HashMap<>();
    // Indicates whether the output values after the specified time should be compared with the PLC values.
    protected boolean aftertimecondition;
    // The tolerance (in milliseconds) for comparing the output value of the state with the corresponding value from the PLC.
    protected int timetolerancecondition;
    // The cycle time (in milliseconds) of the PLC.
    protected long cycletime;
    // The state object used to retrieve the outputs for reading to the PLC.
    protected State state;
    // A list of observers to notify about the results of the test, or to provide updates on PLC readings and comparisons with state outputs.
    protected List<StateTestObserver> statetestobservers;
    
    public final boolean doStateTest() {
        // If the aftertimecondition is false, no timing read and comparison 
        // of the output values between the state's outputs and the PLC is required.
        if (!aftertimecondition) {
            handleOutputs();
            readOutputsFromPlc();
            return compareStateOutputs(false);
            // Else timing read and comparison is required.
        } else {
            // Start time.
            long startTime = System.currentTimeMillis();
            // First time : Read the output values from the PLC and compare them with the expected output values of the PLC.
            // Here, the before-time values will be compared.
            handleOutputs();
            readOutputsFromPlc();
            if (!compareStateOutputs(false)) {
                return false;
            }
            if (state.getTimerCondition() - state.getTimerConditionTolerance() >= 2 * cycletime) {
                try {
                    // Wait on TimerCondition - TimerConditionTolerance.
                    Thread.sleep((state.getTimerCondition() - state.getTimerConditionTolerance()) / 2);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                // Second time : Read the output values from the PLC and compare them with the expected output values of the PLC.
                // Here, the before-time values will be compared.
                handleOutputs();
                readOutputsFromPlc();
                if (!compareStateOutputs(false)) {
                    return false;
                }
                try {
                    // Wait on TimerCondition - TimerConditionTolerance.
                    Thread.sleep((state.getTimerCondition() - state.getTimerConditionTolerance()) / 2);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                // Third time : Read the output values from the PLC and compare them with the expected output values of the PLC.
                // Here, the before-time values will be compared.
                handleOutputs();
                readOutputsFromPlc();
                if (!compareStateOutputs(false)) {
                    return false;
                }
            }
            // Wait on timer condition form the starting point.
            try {
                // Wait on the rest of the ellapsed time and the Timercondition + Timerconditiontolerance
                long result = System.currentTimeMillis() - startTime;
               Thread.sleep(state.getTimerCondition() - result + state.getTimerConditionTolerance());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // Last time : Read the output values from the PLC and compare them with the expected output values of the PLC.
            // Here, the after-time values will be compared.
            handleOutputs();
            readOutputsFromPlc();
            return compareStateOutputs(true);
        }
    }

    /**
     * Sets the PLC client for communication.
     *
     * @param plcclient S7Client - The PLC client.
     */
    public final void setPlcClient(S7Client plcclient) {
        this.plcclient = plcclient;
    }

    public final S7Client getPlcClient() {
        return plcclient;
    }

     /**
     * Sets the data building block number for outputs.
     *
     * @param databuildingblocknumberoutputs short - The data block number.
     */
    public final void setDataBuildingBlockNumberOutputs(short databuildingblocknumberoutputs) {
        this.databuildingblocknumberoutputs = databuildingblocknumberoutputs;
    }

    public final short getDataBuildingBlockNumberOutputs() {
        return databuildingblocknumberoutputs;
    }
    
    /**
     * Sets the list of outputs for the state test.
     *
     * @param outputList List<Output> - The list of outputs.
     */
    public final void setOutputList(List<Output> outputList) {
        this.outputList = outputList;
    }

    /**
     * Sets the map of input values for the state test.
     *
     * @param outputvaluemap HashMap<Short, byte[]> - The map of output addresses to values.
     */
    public final void setOutputvaluemap(HashMap<Short, byte[]> outputvaluemap) {
        this.outputvaluemap = outputvaluemap;
    }

    /**
    * Sets the after-time condition flag for the state test.
    *
    * @param aftertimecondition boolean - Indicates whether the after-time condition is enabled:
    *                              - `true`: Enables after-time condition for output comparison.
    *                              - `false`: Disables after-time condition.
    */
    public final void setAftertimecondition(boolean aftertimecondition) {
        this.aftertimecondition = aftertimecondition;
    }
    
    /**
     * Sets the state to be tested.
     *
     * @param state State - The state object.
     */
    public final void setState(State state) {
        this.state = state;
    }
    
    /**
     * Retrieves the current UTC timestamp for logging purposes.
     *
     * @return OffsetDateTime - The current UTC timestamp.
     */
    public final OffsetDateTime getTimestamp() {
        return OffsetDateTime.now(ZoneOffset.UTC);
    }

    /**
     * Sets the cycle time for the PLC test execution.
    *
    * @param cycletime long - The cycle time in milliseconds.
    */
    public final void setCycleTime(long cycletime)
    {
       this.cycletime = cycletime;
    }

    /**
     * Adds an observer to monitor the state test.
     *
     * @param statetestobserver StateTestObserver - The observer to add.
     */
    public abstract void addStateTestObserver(StateTestObserver statetestobserver);
    
    /**
     * Removes an observer from monitoring the state test.
     *
     * @param statetestobserver StateTestObserver - The observer to remove.
     */
    public abstract void removeStateTestObserver(StateTestObserver statetestobserver);
    
    /**
     * Notifies all observers with a specific message.
     *
     * @param message String - The message to notify observers with.
     */
    public abstract void notifyStateTestObservers(String message); 
    
    protected abstract void handleOutputs();
    protected abstract void readOutputsFromPlc();
    protected abstract boolean compareStateOutputs(boolean aftertime); 
    
}
