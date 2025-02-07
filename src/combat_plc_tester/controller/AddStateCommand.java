// Package
package combat_plc_tester.controller;

// Imports
import combat_plc_tester.model.moore.GraphElementRenderer;
import combat_plc_tester.model.moore.State;
import combat_plc_tester.model.IO.Output;
import java.util.Iterator;

/**
 * Class: AddStateCommand
 * 
 * Purpose:
 * Implements the `Command` interface to encapsulate the process of adding a state
 * to a testing framework. This class represents a command that can execute, undo, and redo the creation
 * of a state in a graphical testing environment.
 *
 * Design Patterns:
 * - **Command Pattern:**
 *   - Provides mechanisms for executing, undoing, and redoing this action.
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class AddStateCommand implements Command {
   
    private TestController testcontroller;                  // The test controller.
    private State state;                                    // The state model object.
    private double x;                                       // X-coordinate of the ellipse.
    private double y;                                       // Y-coordinate of the ellipse.
    private double width;                                   // Graphical width of the ellipse.
    private double height;                                  // Graphical height of the ellipse.
    private GraphElementRenderer graphelementrenderer;      // Renderer for the graphical representation as an ellipse.
 
    /**
    * Constructor: Initializes a command to add a new state to the test controller.
    * 
    * @param testcontroller TestController - The controller responsible for managing the states and transitions.
    * @param x double - The X-coordinate for the top-left position of the state.
    * @param y double - The Y-coordinate for the top-left position of the state.
    * @param width double - The width of the state.
    * @param height double - The height of the state.
    * @param graphelementrenderer GraphElementRenderer - The renderer for the graphical representation of the state.
    */
    public AddStateCommand(TestController testcontroller, double x, double y, double width, double height, GraphElementRenderer graphelementrenderer) {
      
        this.testcontroller = testcontroller;
        this.x = x;                                         
        this.y = y;                                         
        this.width = width;                                 
        this.height = height;                               
        this.graphelementrenderer = graphelementrenderer;   
    }
    
    /**
    * Executes the command to add a state.
    * 
    * Calls the TestController's method to create and add the state using the provided
    * parameters.
    */
    @Override
    public void execute() {
        this.state = testcontroller.addState(x, y, width, height, graphelementrenderer);
    }

    @Override
    public void undo() {
        testcontroller.deleteState(state.getStateID());
    }

    /**
    * Redoes the addition of the state without any outputs.
    * 
    * This method ensures that all outputs associated with the state 
    * are cleared before re-adding it to the test controller. It iterates through the output list,
    * removing all elements to ensure the state is added in a 
    * clean state.
    */
    @Override
    public void redo() {
        Iterator<Output> iterator = state.getOutputs().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
        testcontroller.addState(state);
    }
}
