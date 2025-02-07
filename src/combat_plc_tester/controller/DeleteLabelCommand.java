// Package
package combat_plc_tester.controller;

// Imports
import combat_plc_tester.model.moore.GraphElement;
import combat_plc_tester.model.moore.Label;

/**
 * Class: DeleteLabelCommand
 * 
 * Purpose:
 * Implements the `Command` interface to encapsulate the action of deleting a label
 * from the graphical testing environment. 
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class DeleteLabelCommand implements Command {

    private Label label;                        // The label object of the model.
    private String labelID;                     // The label unique identifier.
    private TestController testcontroller;      // The test controller.

    /**
    * Constructor: DeleteLabelCommand
    * 
    * Initializes the `DeleteLabelCommand` with the test controller and the unique identifier of the label to be deleted.
    *
    * @param testcontroller The `TestController` instance managing the testing environment.
    * @param labelID The unique identifier of the label to be deleted.
    */
    public DeleteLabelCommand(TestController testcontroller, String labelID) {
        this.testcontroller = testcontroller;
        this.labelID = labelID;
    }

    /**
    * Executes the command to delete a label from the model. This involves finding the label 
    * by its unique identifier (`labelID`), storing a reference to it for undo operations, 
    * and deleting it from the test controller.
    */
    @Override
    public void execute() {
        for (GraphElement graphlabel : this.testcontroller.getLabelGraphElementList()) {
            Label labelinlist = (Label) graphlabel;
            if (labelinlist.getLabelID().equals(labelID)) {
                label = labelinlist;
                break;
            }
        }
        testcontroller.deleteLabel(labelID);
    }

    @Override
    public void undo() {
        this.testcontroller.getLabelGraphElementList().add(label);
    }

    @Override
    public void redo() {
        testcontroller.deleteLabel(labelID);
    }
}
