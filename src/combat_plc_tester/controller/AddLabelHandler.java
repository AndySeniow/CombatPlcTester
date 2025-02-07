// Package
package combat_plc_tester.controller;

// Imports
import combat_plc_tester.model.moore.GraphElementRenderer;
import combat_plc_tester.view.LabeledTextRenderer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import javax.swing.JOptionPane;

/**
 * Class: AddLabelHandler
 * 
 * Purpose:
 * Implements the `StateHandler` interface to handle mouse events for adding a label to a testing environment.
 * The label is represented as a rectangle containing text and can be dynamically placed based on user interaction.
 * This class uses the Strategy Pattern to define specific behavior for label creation in the testing framework.
 *
 * Design Patterns:
 * - **Strategy Pattern:**
 *   - Defines a specific implementation of `StateHandler` to handle the process of creating and placing labels.
 *   - Allows dynamic selection of behavior at runtime by swapping `StateHandler` implementations.
 * - **Command Pattern (Collaboration):**
 *   - Works in conjunction with the `AddLabelCommand` to encapsulate the creation of labels as commands, 
 *     enabling undo/redo functionality and separation of concerns.
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class AddLabelHandler implements StateHandler{

    private TestController testcontroller;              // The test controller.
    
    public AddLabelHandler(TestController testcontroller){
        this.testcontroller = testcontroller;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    /**
    * Handles the mouse click event to add a label to the testing environment.
    * This method captures the click location, prompts the user for label text, 
    * and creates a command to render the label at the specified location.
    *
    * Parameters:
    * @param e The `MouseEvent` object containing details of the mouse click event.
    *      
    */
    @Override
    public void mouseClicked(MouseEvent e) {
       Point2D.Double snappedpoint = GridUtils.convertToPoint2D(e.getPoint());
        String text = JOptionPane.showInputDialog("Enter text:");
        if (text == null || text.trim().isEmpty()){
            text = "labeltext";
        }
        GraphElementRenderer labeledtextrenderer = new LabeledTextRenderer();
        labeledtextrenderer.setShapeColor(Color.LIGHT_GRAY);
        labeledtextrenderer.setLabeledTextColor(Color.WHITE);
        Command addlabelcommand = new AddLabelCommand(testcontroller, text, snappedpoint.getX(), snappedpoint.getY(), 100, 20, labeledtextrenderer);
        addlabelcommand.execute(); 
        testcontroller.addCommand(addlabelcommand);
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}
    
    @Override
    public void keyPressed(KeyEvent e) {}
    
    @Override
    public void paintComponent(Graphics2D g){}
     
}