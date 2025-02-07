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
 * - **Command Pattern:**
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
       Point2D.Double snappedpoint = GridUtils.converttoPoint2D(e.getPoint());
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