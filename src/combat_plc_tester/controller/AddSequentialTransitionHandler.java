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
import combat_plc_tester.model.moore.GraphElement;
import combat_plc_tester.model.moore.GraphElementRenderer;
import combat_plc_tester.view.LabeledPathRenderer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 * Class: AddSequentialTransitionHandler
 * 
 * Purpose:
 * Implements the `StateHandler` interface to enable the creation of sequential transitions
 * in a testing framework. A transition is defined as a path consisting of four key points:
 * a starting point, two control points, and an endpoint.
 *
 * Design Patterns:
 * - **Strategy Pattern:**
 *   - Defines the behavior for creating sequential transitions as a specific implementation of `StateHandler`.
 *   - Enables dynamic behavior changes at runtime by allowing different handlers to be used for various states.
 * - **Command Pattern:**
 *   - Encapsulates the creation of a transition into an `AddSequentialTransitionHandler` object, 
 *     which can be executed, tracked, and undone.
 *
 * Workflow:
 * 1. The user clicks to define the starting state of the transition.
 * 2. Intermediate control points are specified through additional clicks or mouse movement.
 * 3. The final click defines the endpoint and completes the transition between two states.
 * 4. A command is created and executed to add the transition to the environment.
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class AddSequentialTransitionHandler implements StateHandler{

    private TestController testcontroller;      // The test controller.      
     private String startStateID;               // Start state ID.
    private String endStateID;                  // End state ID.
    private boolean firstpointSelected;         // First point of the path selected.
    private boolean secondpointSelected;        // second point of the path selected.
    private boolean thirdpointSelected;         // third point of the path selected.
    private boolean firststateSelected;         // First state of the path selected.
    private boolean secondstateSelected;        // Second state of the path selected.
    private double startX;                      // Start X-coordinate of the transition.
    private double startY;                      // Start Y-coordinate of the transition.
    private double ctrlX1;                      // Second X-coordinate of the transtion.
    private double ctrlY1;                      // Second Y-coordinate of the transtion.
    private double ctrlX2;                      // Third X-coordinate of the transtion.
    private double ctrlY2;                      // Third Y-coordinate of the transtion.
    private double endX;                        // End X-coordinate of the transition.
    private double endY;                        // End Y-coordinate of the transition.
   
    public AddSequentialTransitionHandler(TestController testcontroller){
        this.testcontroller = testcontroller;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    /**
    * Handles the mouse click event to add a new sequential transition in the testing environment.
    *
    * @param e The `MouseEvent` object containing details of the mouse click event.
    *          
    */
    @Override
    public void mouseClicked(MouseEvent e) {
        Point2D.Double snappedPoint = GridUtils.snapongrid(GridUtils.converttoPoint2D(e.getPoint()));
        executeAddTransition(snappedPoint.getX(), snappedPoint.getY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    /**
    * Dynamically updates the control points or endpoint of the transition path 
    * based on the current mouse position, aligning the point to the grid.
    *
    * Workflow:
    * - Updates the first control point if only the starting point is selected.
    * - Updates the second control point if the first control point is set.
    * - Updates the endpoint if all control points are set.
    *
    * @param e The `MouseEvent` containing the current mouse position.
    */
    @Override
    public void mouseMoved(MouseEvent e) {
        Point2D.Double snappedPoint = GridUtils.snapongrid(GridUtils.converttoPoint2D(e.getPoint()));
        if (firstpointSelected && !secondpointSelected) {
            updatefirstControlPoint(snappedPoint);
        } else if (firstpointSelected && secondpointSelected && !thirdpointSelected) {
            updatesecondControlPoint(snappedPoint);
        } else if (firstpointSelected && secondpointSelected && thirdpointSelected) {
            updatelastPoint(snappedPoint);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}
    
    /**
    * Renders the transition path on the graphical interface, visualizing the connection 
    * between the start point, two control points, and the endpoint as a continuous path.
    *
    * Workflow:
    * - Creates a new `Path2D.Double` object.
    * - Moves to the starting point of the transition.
    * - Draws straight lines connecting the starting point, control points, and endpoint.
    *
    * @param g The `Graphics2D` object used to draw the transition path.
    */
    @Override
    public void paintComponent(Graphics2D g) {
        Path2D.Double path = new Path2D.Double();
        path.moveTo(startX, startY);
        path.lineTo(ctrlX1, ctrlY1);
        path.lineTo(ctrlX2, ctrlY2);
        path.lineTo(endX, endY);
        g.draw(path);
    }
       
    private void executeAddTransition(double x, double y) {
        Point2D.Double point = new Point2D.Double(x, y);
        if (!firstpointSelected) {
            selectfirstState(point);

        } else if (firstpointSelected && !secondpointSelected) {
            if (ispointinState(point)) {
                completeTransition(point);
            } else {
                ctrlX1 = point.x;
                ctrlY1 = point.y;
                secondpointSelected = true;
            }
        } else if (secondpointSelected && !thirdpointSelected) {
            if (ispointinState(point)) {
                completeTransition(point);
            } else {
                ctrlX2 = point.x;
                ctrlY2 = point.y;
                thirdpointSelected = true;
            }
        } else if (thirdpointSelected) {
            selectsecondStateAndExecute(point);
        }
    }

    private boolean ispointinState(Point2D.Double point) {

        for (GraphElement graphstate : testcontroller.getStateGraphElementList()) {
            Ellipse2D.Double circle = (Ellipse2D.Double) graphstate.getGraph().getEllips();
            if (circle.contains(point)) {
                endStateID = graphstate.getGraph().getLabeledText();
                endX = point.x;
                endY = point.y;
                return true;
            }
        }
        return false;
    }

    private void completeTransition(Point2D.Double snappedPoint) {
        if (!secondpointSelected) {
            ctrlX1 = ctrlX2 = endX = snappedPoint.x;
            ctrlY1 = ctrlY2 = endY = snappedPoint.y;
        } else if (secondpointSelected && !thirdpointSelected) {
            ctrlX2 = endX = snappedPoint.x;
            ctrlY2 = endY = snappedPoint.y;
        }
        thirdpointSelected = true;
        selectsecondStateAndExecute(snappedPoint);
    }

    private void selectfirstState(Point2D.Double snappedPoint) {

        for (GraphElement graphstate : testcontroller.getStateGraphElementList()) {
            Ellipse2D.Double circle = (Ellipse2D.Double) graphstate.getGraph().getEllips();
            if (circle.contains(snappedPoint)) {
                firststateSelected = true;
                startStateID = graphstate.getGraph().getLabeledText();
            }
        }
        if (firststateSelected) {
            startX = snappedPoint.x;
            startY = snappedPoint.y;
            firstpointSelected = true;
        }
    }

    private void selectsecondStateAndExecute(Point2D.Double snappedPoint) {

        for (GraphElement graphstate : testcontroller.getStateGraphElementList()) {
            Ellipse2D.Double circle = (Ellipse2D.Double) graphstate.getGraph().getEllips();
            if (circle.contains(snappedPoint)) {
                secondstateSelected = true;
                endStateID = graphstate.getGraph().getLabeledText();
            }
        }
        if (secondstateSelected) {
            endX = snappedPoint.x;
            endY = snappedPoint.y;
            GraphElementRenderer labeledpathrenderer = new LabeledPathRenderer();
            labeledpathrenderer.setShapeColor(Color.GRAY);
            labeledpathrenderer.setLabeledTextColor(Color.GRAY);
            Command addtransitioncommand = new AddSequentialTransitionCommand(testcontroller, startStateID, endStateID, startX, startY, ctrlX1, ctrlY1, ctrlX2, ctrlY2, endX, endY, labeledpathrenderer);
            addtransitioncommand.execute();
            resetTransitionSelection();
            testcontroller.addCommand(addtransitioncommand);
        }
    }

    private void resetTransitionSelection() {
        startStateID = "";
        endStateID = "";
        firstpointSelected = false;
        secondpointSelected = false;
        thirdpointSelected = false;
        firststateSelected = false;
        secondstateSelected = false;
        startX = 0;
        startY = 0;
        endX = 0;
        endY = 0;
        ctrlX1 = 0;
        ctrlY1 = 0;
        ctrlX2 = 0;
        ctrlY2 = 0;
    }

    private void updatefirstControlPoint(Point2D.Double snappedPoint) {
        ctrlX1 = snappedPoint.x;
        ctrlY1 = snappedPoint.y;
        ctrlX2 = ctrlX1;
        ctrlY2 = ctrlY1;
        endX = ctrlX1;
        endY = ctrlY1;
    }

    private void updatesecondControlPoint(Point2D.Double snappedPoint) {
        ctrlX2 = snappedPoint.x;
        ctrlY2 = snappedPoint.y;
        endX = ctrlX1;
        endY = ctrlY1;
    }

    private void updatelastPoint(Point2D.Double snappedPoint) {
        endX = snappedPoint.x;
        endY = snappedPoint.y;
    }
}
