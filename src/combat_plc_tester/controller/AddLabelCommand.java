// Package
package combat_plc_tester.controller;

// Imports
import combat_plc_tester.model.moore.GraphElementRenderer;
import combat_plc_tester.model.moore.Label;

/**
 * Class: AddLabelCommand
 * 
 * Purpose:
 * Implements the `Command` interface to encapsulate the process of adding a label
 * to a testing framework. This class represents a command that can execute, undo, and redo the creation
 * of a label.
 *
 * Design Patterns:
 * - **Command Pattern:**
 *   - Provides mechanisms for executing, undoing, and redoing this action.
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class AddLabelCommand implements Command {    
    
    private TestController testcontroller;                      // The test controller.
    private Label label;                                        // The label model object.
    private String text;                                        // The text in the label.
    private double x;                                           // X-coordinate of the label.
    private double y;                                           // Y-coordinate of the ellipse.
    private double width;                                       // Graphical width of the label.
    private double height;                                      // Graphical height of the ellipse.
    private GraphElementRenderer graphelementrenderer;          // Renderer for the graphical representation as a label.
    
    /**
    * Constructor for the AddLabelCommand.
    * 
    * Initializes the command to add a label to the graphical interface within the test controller.
    *
    * @param testcontroller TestController - The controller managing the label addition.
    * @param text String - The text content of the label.
    * @param x double - The X-coordinate where the label will be placed.
    * @param y double - The Y-coordinate where the label will be placed.
    * @param width double - The width of the label.
    * @param height double - The height of the label.
    * @param graphelementrenderer GraphElementRenderer - Renderer for the label's graphical representation.
    */
    public AddLabelCommand(TestController testcontroller, String text, double x, double y, double width, double height, GraphElementRenderer graphelementrenderer) {
        this.testcontroller = testcontroller;
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.graphelementrenderer = graphelementrenderer;
    }

     /**
    * Executes the command to add a label.
    * 
    * Calls the TestController's method to create and add the label using the provided
    * parameters.
    */
    @Override
    public void execute() {
        this.label = testcontroller.addLabel(text, x, y, width, height, graphelementrenderer);
    }

    @Override
    public void undo() {
        testcontroller.deleteLabel(label.getLabelID());
    }

    @Override
    public void redo() {
        testcontroller.addLabel(label);
    }
}
