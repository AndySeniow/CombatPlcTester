// Package
package combat_plc_tester.controller;

// Imports
import combat_plc_tester.model.moore.GraphElement;
import combat_plc_tester.model.moore.Transition;

/**
 * Class: UpdateTransitionCommand
 * 
 * Purpose:
 * Implements the `Command` interface to manage updates to a transition in a graphical testing environment.
 *
 * Design Patterns:
 * - **Command Pattern:**
 *   - Encapsulates transition updates, allowing for execution, undo, and redo functionality.
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class UpdateTransitionCommand implements Command{

    private TestController testcontroller;          // The test controller.
    private GraphElement graphtransition;           // The graph transition.
    private double firststartX;                     // The first X-coordinate of the transition before undo.
    private double firststartY;                     // The first Y-coordinate of the transition before undo.
    private double firstctrlX1;                     // The first ctrl X1-coordinate of the transition before undo.
    private double firstctrlY1;                     // The first ctrlY1-coordinate of the transition before undo.
    private double firstctrlX2;                     // The first ctrl X2-coordinate of the transition before undo.
    private double firstctrlY2;                     // The first ctrl Y2-coordinate of the transition before undo.
    private double firstendX;                       // The first end X-coordinate of the transition before undo.
    private double firstendY;                       // The first end Y-coordinate of the transition before undo.
    private double secondstartX;                    // The second X-coordinate of the transition after undo.
    private double secondstartY;                    // The second Y-coordinate of the transition after undo.
    private double secondctrlX1;                    // The second ctrl X1-coordinate of the transition after undo.
    private double secondctrlY1;                    // The second ctrl Y1-coordinate of the transition after undo.
    private double secondctrlX2;                    // The second ctrl X2-coordinate of the transition after undo.
    private double secondctrlY2;                    // The second ctrl Y2-coordinate of the transition after undo.
    private double secondendX;                      // The second end X-coordinate of the transition after undo.
    private double secondendY;                      // The second end Y-coordinate of the transition after undo.
    
    public UpdateTransitionCommand(TestController testcontroller, GraphElement graphtransition) {
        this.testcontroller = testcontroller;
        this.graphtransition = graphtransition;
    }

    @Override
    public void execute() {
        Transition transition = (Transition) graphtransition;
        firststartX = transition.getStartX();
        firststartY = transition.getStartY();
        firstctrlX1 = transition.getControlX1();
        firstctrlY1 = transition.getControlY1();
        firstctrlX2 = transition.getControlX2();
        firstctrlY2 = transition.getControlY2();
        firstendX = transition.getEndX();
        firstendY = transition.getEndY();
    }

    @Override
    public void undo() {
        Transition transition = (Transition) graphtransition;
        secondstartX = transition.getStartX();;
        secondstartY = transition.getStartY();;
        secondctrlX1 = transition.getControlX1();
        secondctrlY1 = transition.getControlY1();
        secondctrlX2 = transition.getControlX2();
        secondctrlY2 = transition.getControlY2();
        secondendX = transition.getEndX();
        secondendY = transition.getEndY();

        transition.setStartX(firststartX);
        transition.setStartY(firststartY);
        transition.setControlX1(firstctrlX1);
        transition.setControlY1(firstctrlY1);
        transition.setControlX2(firstctrlX2);
        transition.setControlY2(firstctrlY2);
        transition.setEndX(firstendX);
        transition.setEndY(firstendY);
    }

    @Override
    public void redo() {
        Transition transition = (Transition) graphtransition;
        transition.setStartX(secondstartX);
        transition.setStartY(secondstartY);
        transition.setControlX1(secondctrlX1);
        transition.setControlY1(secondctrlY1);
        transition.setControlX2(secondctrlX2);
        transition.setControlY2(secondctrlY2);
        transition.setEndX(secondendX);
        transition.setEndY(secondendY);
    }
}
