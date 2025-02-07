// Package
package combat_plc_tester.controller;

// Imports
import combat_plc_tester.model.moore.GraphElementRenderer;
import combat_plc_tester.model.IO.Input;
import combat_plc_tester.model.moore.Transition;
import java.util.Iterator;

/**
 * Class: AddSequentialTransitionCommand
 * 
 * Purpose:
 * Implements the `Command` interface to encapsulate the process of adding a sequential transition
 * to a testing framework. This class represents a command that can execute, undo, and redo the creation
 * of a transition between states in a graphical testing environment.
 *
 * Design Patterns:
 * - **Command Pattern:**
 *   - Provides mechanisms for executing, undoing, and redoing this action.
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class AddSequentialTransitionCommand implements Command{
    
    private TestController testcontroller;              // The test controller.
    private Transition transition;                      // The transition model object.
    private String startStateID;                        // Start state ID.
    private String endStateID;                          // End state ID.
    private double startX;                              // Start X-coordinate of the transition.
    private double startY;                              // Start Y-coordinate of the transition.
    private double ctrlX1;                              // Second X-coordinate of the transtion.
    private double ctrlY1;                              // Second Y-coordinate of the transtion.
    private double ctrlX2;                              // Third X-coordinate of the transtion.
    private double ctrlY2;                              // Third Y-coordinate of the transtion.
    private double endX;                                // End X-coordinate of the transition.
    private double endY;                                // End Y-coordinate of the transition.
    private GraphElementRenderer graphelementrenderer;  // Renderer for the graphical representation as a path.
    
    /**
    * Constructor for the AddSequentialTransitionCommand.
    * 
    * Initializes the command with necessary parameters to add a sequential transition
    * between two states in the test controller.
    *
    * @param testcontroller TestController - The controller managing the transitions.
    * @param startStateID String - Identifier of the start state.
    * @param endStateId String - Identifier of the end state.
    * @param startX double - X-coordinate of the start point.
    * @param startY double - Y-coordinate of the start point.
    * @param ctrlX1 double - X-coordinate of the first control point.
    * @param ctrlY1 double - Y-coordinate of the first control point.
    * @param ctrlX2 double - X-coordinate of the second control point.
    * @param ctrlY2 double - Y-coordinate of the second control point.
    * @param endX double - X-coordinate of the end point.
    * @param endY double - Y-coordinate of the end point.
    * @param graphelementrenderer GraphElementRenderer - Renderer for the transition's graphical representation.
    */
    public AddSequentialTransitionCommand(TestController testcontroller, String startStateID, String endStateId, double startX, double startY, 
                                double ctrlX1, double ctrlY1, double ctrlX2, double ctrlY2, 
                                double endX, double endY, GraphElementRenderer graphelementrenderer){
        this.testcontroller = testcontroller;
        this.startStateID = startStateID;
        this.endStateID = endStateId;
        this.startX = startX;
        this.startY = startY;
        this.ctrlX1 = ctrlX1;
        this.ctrlY1 = ctrlY1;
        this.ctrlX2 = ctrlX2;
        this.ctrlY2 = ctrlY2;
        this.endX = endX;
        this.endY = endY;
        this.graphelementrenderer = graphelementrenderer;
    }
    
    /**
    * Executes the command to add a combinatorial transition.
    * 
    * Calls the TestController's method to create and add the transition using the provided
    * parameters.
    */
    @Override
    public void execute() {
        transition = testcontroller.addSequentialTransition(startStateID, endStateID, startX, startY,
                ctrlX1, ctrlY1, ctrlX2, ctrlY2,
                endX, endY, graphelementrenderer);
    }

    @Override
    public void undo() {
        testcontroller.deleteTransition(transition.getTransitionID());
    }

    /**
    * Redoes the addition of the sequential transition without any inputs.
    * 
    * This method ensures that all inputs associated with the transition 
    * are cleared before re-adding it to the test controller. It iterates through the input list,
    * removing all elements to ensure the transition is added in a 
    * clean state.
    */
    @Override
    public void redo() {
        Iterator<Input> iteratorinputs = transition.getInputs().iterator();
        while (iteratorinputs.hasNext()) {
            iteratorinputs.next();
            iteratorinputs.remove();
        }
        testcontroller.addTransition(transition);
    }
}
