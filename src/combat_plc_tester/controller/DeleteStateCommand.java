// Package
package combat_plc_tester.controller;

// Imports
import combat_plc_tester.model.moore.GraphElement;
import combat_plc_tester.model.IO.Output;
import combat_plc_tester.model.moore.State;
import java.util.Iterator;

/**
 * Class: DeleteStateCommand
 * 
 * Purpose:
 * Implements the `Command` interface to encapsulate the action of deleting a state
 * from the graphical testing environment. 
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class DeleteStateCommand implements Command{

    private TestController testcontroller;      // The test controller.
    private State state;                        // The state object of the model.
    private String stateID;                     // The state unique identifier.
   
    /**
    * Constructor: DeleteStateCommand
    * 
    * Initializes the `DeleteStateCommand` with the test controller and the unique identifier of the state to be deleted.
    *
    * @param testcontroller The `TestController` instance managing the testing environment.
    * @param stateID The unique identifier of the state to be deleted.
    */
    public DeleteStateCommand(TestController testcontroller, String stateID){
        this.testcontroller = testcontroller;
        this.stateID = stateID;
    }
 
    /**
    * Executes the command to delete a state.
    * 
    * Calls the TestController's method to create and add the state using the provided
    * parameters.
    */
    @Override
    public void execute() {
        for (GraphElement graphstate : this.testcontroller.getStateGraphElementList()) {
            State stateinlist = (State) graphstate;
            if (stateinlist.getStateID().equals(stateID)) {
                state = stateinlist;
                break;
            }
        }
        testcontroller.deleteState(stateID);
    }

    /**
    * Undoes the deleting of the state without any outputs.
    * 
    * This method ensures that all outputs associated with the state 
    * are cleared before re-adding it to the test controller. It iterates through the output list,
    * removing all elements to ensure the state is added in a 
    * clean state.
    */
    @Override
    public void undo() {
        Iterator<Output> iterator = state.getOutputs().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
        testcontroller.getStateGraphElementList().add(state);
    }

    @Override
    public void redo() {
        testcontroller.deleteState(stateID);
    }
}
