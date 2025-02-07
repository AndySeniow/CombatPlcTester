// Package
package combat_plc_tester.model.moore;

// Imports
import Moka7.S7Client;
import combat_plc_tester.controller.TransitionTestObserver;
import combat_plc_tester.model.IO.Input;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;

/**
 * Abstract Class: TransitionTest
 *
 * Purpose:
 * - Provides a framework for performing transition tests on a Siemens PLC.
 * - Implements the **Template Method design pattern** to define a consistent structure for transition testing.
 * - Subclasses are required to implement specific steps in the test process, ensuring flexibility for different test types.
 *
 * Design Patterns:
 * - **Template Method Pattern:**
 *   - The `doTransitionTest()` method provides a fixed sequence of operations:
 *     1. Handling generic inputs (`handleInputs()`).
 *     2. Handling transition-specific inputs (`handleTransitionInputs()`).
 *     3. Writing the inputs to the PLC (`writeInputsToPlc()`).
 *   - Subclasses define the specific implementation for these steps through abstract methods.
 * - **Observer Pattern:**
 *   - Allows observers to monitor the progress or results of a transition test through methods for adding, removing, 
 *     and notifying observers.
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public abstract class TransitionTest {

    // S7 Siemens PLC client to write the inputs.
    protected S7Client plcclient;
    // The data building block in the plc where the inputs will be write to.
    protected short databuildingblocknumberinputs;
    // The input list of all the inputs used in the test.
    protected List<Input> inputList;
    // A map with the start address of the input followed by the input value in byte form.
    protected HashMap<Short, byte[]> inputvaluemap = new HashMap<>();
    // The transition to use to get the inputs to write to the PLC.
    protected Transition transition;
    // List of observers to inform result of the test or the write information.
    protected List<TransitionTestObserver> transitiontestobservers;

    /**
     * Template Method: Executes the transition test workflow.
     * 1. Handles generic inputs (`handleInputs()`).
     * 2. Handles transition-specific inputs (`handleTransitionInputs()`).
     * 3. Writes the inputs to the PLC (`writeInputsToPlc()`).
     */
    public void doTransitionTest() {
        handleInputs();
        handleTransitionInputs();
        writeInputsToPlc();
    }

    /**
     * Sets the PLC client for communication.
     *
     * @param plcclient S7Client - The PLC client.
     */
    public void setPlcClient(S7Client plcclient) {
        this.plcclient = plcclient;
    }

    public S7Client getPlcClient() {
        return plcclient;
    }

    /**
     * Sets the data building block number for inputs.
     *
     * @param databuildingblocknumberinputs short - The data block number.
     */
    public void setDataBuildingBlockNumberInputs(short databuildingblocknumberinputs) {
        this.databuildingblocknumberinputs = databuildingblocknumberinputs;
    }

    public short getDataBuildingBlockNumberInputs() {
        return databuildingblocknumberinputs;
    }
    
    /**
     * Sets the list of inputs for the transition test.
     *
     * @param inputList List<Input> - The list of inputs.
     */
    public void setInputList(List<Input> inputList) {
        this.inputList = inputList;
    }

    /**
     * Sets the map of input values for the transition test.
     *
     * @param inputvaluemap HashMap<Short, byte[]> - The map of input addresses to values.
     */
    public void setInputvaluemap(HashMap<Short, byte[]> inputvaluemap) {
        this.inputvaluemap = inputvaluemap;
    }

    /**
     * Sets the transition to be tested.
     *
     * @param transition Transition - The transition object.
     */
    public void setTransition(Transition transition) {
        this.transition = transition;
    }

    /**
     * Retrieves the current UTC timestamp for logging purposes.
     *
     * @return OffsetDateTime - The current UTC timestamp.
     */
     public OffsetDateTime getTimestamp() {
        return OffsetDateTime.now(ZoneOffset.UTC);
    }
    
     /**
     * Adds an observer to monitor the transition test.
     *
     * @param transitiontestobserver TransitionTestObserver - The observer to add.
     */
    public abstract void addTransitionTestObserver(TransitionTestObserver transitiontestobserver);
    
    /**
     * Removes an observer from monitoring the transition test.
     *
     * @param transitiontestobserver TransitionTestObserver - The observer to remove.
     */
    public abstract void removeTransitionTestObserver(TransitionTestObserver transitiontestobserver);
    
    /**
     * Notifies all observers with a specific message.
     *
     * @param message String - The message to notify observers with.
     */
    public abstract void notifyTransitionTestObservers(String message); 
    
    protected abstract void handleInputs();
    protected abstract void handleTransitionInputs(); 
    protected abstract void writeInputsToPlc();  
}
