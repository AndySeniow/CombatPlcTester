/**
 * This class contains the implementation of graphical user interface (GUI) concepts
 * used in the modeling tool, including rendering of states (as circles), transitions (as lines),
 * snapping to a grid, point manipulation, and interactive visual editing.
 *
 * The design and techniques were developed independently and are based on:
 * - Concepts from the official Oracle Java 2D Graphics documentation [1]
 * - Practical examples and explanations from Liang & Zhang [2] and Haase & Guy [3]
 * - Community-driven discussions from Stack Overflow (tag: java-2d) [4]
 *
 * These sources were consulted for conceptual understanding and inspiration.
 *
 * Features include:
 * - Shape rendering and transformations
 * - Custom painting techniques and layering
 * - Mouse interaction and snapping logic
 * - Grid drawing, selection, and element manipulation
 *
 * References:
 * [1] Oracle. *Trail: 2D Graphics*. Available at: https://docs.oracle.com/javase/tutorial/2d/index.html. Accessed: 2024-05-01.
 * [2] Y. D. Liang and H. Zhang, *Computer Graphics Using Java 2D and 3D*, Pearson Prentice Hall, 2006.
 * [3] C. Haase and R. Guy, *Filthy Rich Clients: Developing Animated and Graphical Effects for Desktop Java Applications*, Addison-Wesley, 2007.
 * [4] Stack Overflow. *Search results for "Java 2D"*. https://stackoverflow.com/questions/tagged/java-2d. Accessed: 2024-05-01.
 */

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
    *          
    */
    @Override
    public void mouseClicked(MouseEvent e) {
        Point2D.Double snappedpoint = GridUtils.snapongrid(GridUtils.converttoPoint2D(e.getPoint()));
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
