// Package
package combat_plc_tester.controller;

// Imports
import combat_plc_tester.model.moore.CombinatorialTransition;
import combat_plc_tester.model.moore.GraphElement;
import combat_plc_tester.model.IO.Input;
import combat_plc_tester.model.moore.SequentialTransition;
import combat_plc_tester.model.moore.Transition;
import java.util.Iterator;

/**
 * Class: DeleteTransitionCommand
 * 
 * Purpose:
 * Implements the `Command` interface to encapsulate the action of deleting a transition
 * from the graphical testing environment. 
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class DeleteTransitionCommand implements Command{

    private TestController testcontroller;          // The test controller.
    private String transitionID;                    // The transtion unique identifier.
    private Transition transition;                  // The transition object of the model.
    
    /**
    * Constructor: DeleteTransitionCommand
    * 
    * Initializes the `DeleteTransitionCommand` with the test controller and the unique identifier of the transition to be deleted.
    *
    * @param testcontroller The `TestController` instance managing the testing environment.
    * @param transitionID The unique identifier of the transition to be deleted.
    */
    public DeleteTransitionCommand(TestController testcontroller, String transitionID){
        this.testcontroller = testcontroller;
        this.transitionID = transitionID;
    }
    
    /**
    * Executes the command to delete a transition.
    * 
    * Calls the TestController's method to create and add the transition using the provided
    * parameters.
    */
    @Override
    public void execute() {
      for(GraphElement graphtransition : this.testcontroller.getTransitionGraphElementList()){
     Transition transitioninlist = (Transition) graphtransition;
     if(transitioninlist.getTransitionID().equals(transitionID)){
         transition = transitioninlist;
         break;
     }
     }
     testcontroller.deleteTransition(transitionID);
    }

    /**
    * Undoes the deleting of the transition without any inputs.
    * 
    * This method ensures that all inputs associated with the transition 
    * are cleared before re-adding it to the test controller. It iterates through the input list,
    * removing all elements to ensure the transition is added in a 
    * clean state.
    */
    @Override
    public void undo() {
        Iterator<Input> iteratorinputs = transition.getInputs().iterator();
        while (iteratorinputs.hasNext()) {
            iteratorinputs.next();
            iteratorinputs.remove();
        }
        if (transition instanceof CombinatorialTransition) {
            CombinatorialTransition combinatorialtransition = (CombinatorialTransition) transition;

            Iterator<SequentialTransition> iteratortranstions = combinatorialtransition.getSequentialTransitionsToExclude().iterator();
            while (iteratortranstions.hasNext()) {
                iteratortranstions.next();
                iteratortranstions.remove();
            }
        }
        this.testcontroller.getTransitionGraphElementList().add(transition);
    }

    @Override
    public void redo() {
        testcontroller.deleteTransition(transitionID);
    }

}
