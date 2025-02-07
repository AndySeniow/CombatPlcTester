// Package
package combat_plc_tester.controller;

// Imports
import combat_plc_tester.model.moore.GraphElement;
import combat_plc_tester.model.moore.Label;

/**
 * Class: UpdateLabelCommand
 * 
 * Purpose:
 * Implements the `Command` interface to encapsulate the operation of updating 
 * a label's text and position in the testing environment.
 *
 * Design Patterns:
 * - **Command Pattern:**
 *   - Encapsulates the label update operation, enabling execution, undo, and redo functionality.
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class UpdateLabelCommand implements Command{

    private TestController testcontroller;              // The test controller.
    private GraphElement graphlabel;                    // The graph label.
    private String firsttext;                           // Text before undo.
    private String secondtext;                          // Text after undo.
    private double firstx;                              // The X-coordinate of the label before undo.
    private double firsty;                              // The Y-coordinate of the label before undo.
    private double secondx;                             // The X-coordinate of the label after undo.
    private double secondy;                             // The Y-coordinate of the label after undo.
    
    public UpdateLabelCommand(TestController testcontroller, GraphElement graphlabel) {
        this.testcontroller = testcontroller;
        this.graphlabel = graphlabel;
    }

    @Override
    public void execute() {
        Label label = (Label) graphlabel;
        this.firsttext = label.getText();
        this.firstx = label.getX();
        this.firsty = label.getY();
    }

    @Override
    public void undo() {
        Label label = (Label) graphlabel;
        this.secondtext = label.getText();
        this.secondx = label.getX();
        this.secondy = label.getY();
        label.setText(firsttext);
        label.setX(firstx);
        label.setY(firsty);
    }

    @Override
    public void redo() {
        Label label = (Label) graphlabel;
        label.setText(secondtext);
        label.setX(secondx);
        label.setY(secondy);
    }
}
