// Package
package combat_plc_tester.controller;

// Imports
import combat_plc_tester.model.moore.GraphElementRenderer;
import combat_plc_tester.view.LabeledEllipseRenderer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

/**
 * Class: AddStateHandler
 * 
 * Purpose:
 * Implements the `StateHandler` interface to handle mouse events for adding a state to a testing environment.
 * The state will be represented as an ellipse with the state ID.
 * 
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class AddStateHandler implements StateHandler{

    private TestController testcontroller;              // The test controller.
    
    public AddStateHandler(TestController testcontroller){
        this.testcontroller = testcontroller;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    /**
    * Handles the mouse click event to add a new state in the testing environment.
    *
    * @param e The `MouseEvent` object containing details of the mouse click event.
    *          Used to retrieve the raw click location.
    */
    @Override
    public void mouseClicked(MouseEvent e) {
        Point2D.Double snappedpoint = GridUtils.snapToGrid(GridUtils.convertToPoint2D(e.getPoint()));
        GraphElementRenderer labeledellipserenderer = new LabeledEllipseRenderer();
        labeledellipserenderer.setShapeColor(Color.BLACK);
        labeledellipserenderer.setLabeledTextColor(Color.WHITE);
        Command addstatecommand = new AddStateCommand(testcontroller, snappedpoint.getX()-15, snappedpoint.getY()-15, 30, 30, labeledellipserenderer);
        addstatecommand.execute(); 
        testcontroller.addCommand(addstatecommand);
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}
    
    @Override
    public void mouseMoved(MouseEvent e) {}
    
    @Override
    public void paintComponent(Graphics2D g){}
    
}
