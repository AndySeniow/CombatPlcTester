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
import combat_plc_tester.model.moore.Label;
import combat_plc_tester.model.moore.State;
import combat_plc_tester.model.moore.Transition;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * Class: UpdateHandler
 * 
 * Purpose:
 * Implements the `StateHandler` interface to manage interactions with graphical elements in the testing environment.
 * This class allows users to select, move, update, and delete states, transitions, and labels in a graphical interface.
 * By utilizing the Strategy Pattern.
 *
 * Design Patterns:
 * - **Strategy Pattern:**
 *   - Implements `StateHandler` to define behavior for managing graphical elements.
 *   - Allows dynamic swapping of handlers to enable different interaction behaviors in the environment.
 * - **Command Pattern:**
 *   - Encapsulates modifications (updating or deleting elements) into commands, enabling:
 *
 * Workflow:
 * 1. The user selects a graphical element (state, transition, or label) by clicking on it.
 * 2. The handler provides visual feedback for the selected element, such as highlighting or changing colors.
 * 3. Users can move the selected element by dragging it, with real-time visual updates.
 * 4. The user can delete the selected element by pressing a delete key or triggering a delete action.
 * 5. Updates (text changes, positional adjustments) or deletions are encapsulated into a command (`UpdateCommand` or `DeleteCommand`).
 * 6. The command is passed to a controller (`TestController`) for execution, modifying the model.
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class UpdateDeleteHandler implements StateHandler{
    
   private TestController testcontroller;                                       // The test controller.
   private GraphElement selectedgraphstate;                                     // Selected state.
   private GraphElement selectedgraphtransition;                                // Selected transition.
   private GraphElement selectedgraphlabel;                                     // Selected label.   
   private List<GraphElement> selectedgraphtransitions = new ArrayList<>();     // List of selected transitions (if a state is selected).
   private Point2D.Double firstselectedctrlpoint = new Point2D.Double();        // First selected ctrl point.
   private Point2D.Double secondselectedctrlpoint = new Point2D.Double();       // Second selected ctrl point.
   private boolean stateIsmoved = false;                                        // Is state moved.
   private boolean transitionIsmoved = false;                                   // Is transition moved.
   private boolean _firstselectedctrlpoint = false;                             // Checks the first selected ctrl point.
   private boolean _secondselectedctrlpoint = false;                            // Checks the second selected ctrl point.
    
    public UpdateDeleteHandler(TestController testcontroller){
        this.testcontroller = testcontroller;
    }
    
    /**
    * Handles mouse press events to select and update graphical elements (states, transitions, or labels) in the testing environment.
    * Highlights the selected element and prepares it for further interaction or modification.
    *
    * Workflow:
    * - Snaps the mouse click to the grid and checks if the point intersects a state, transition, or label.
    * - Highlights the selected element by changing its color.
    * - Updates the `TestController` with the selected element for tracking and further actions.
    * - Executes commands to update the selected element (state, transition, or label) if applicable.
    *
    * @param e The `MouseEvent` object containing details of the mouse press event.
    */
    @Override
    public void mousePressed(MouseEvent e) {
        Point2D.Double snappedpoint = GridUtils.snapongrid(GridUtils.converttoPoint2D(e.getPoint()));
        Point2D.Double snappedpointLabel = GridUtils.converttoPoint2D(e.getPoint());
        for (GraphElement graphstate : testcontroller.getStateGraphElementList()) {
            Ellipse2D.Double circle = (Ellipse2D.Double) graphstate.getGraph().getEllips();
            if (circle.contains(snappedpoint)) {
                selectedgraphstate = graphstate;
                graphstate.getGraph().setShapeColor(Color.yellow);
                testcontroller.handleSelectedState(graphstate.getGraph().getLabeledText());
                break;
            } else {
                selectedgraphstate = null;
                graphstate.getGraph().setShapeColor(Color.BLACK);
            }
        }
        selectedgraphtransitions.clear();
        for (GraphElement graphtransition : testcontroller.getTransitionGraphElementList()) {
            Path2D.Double path = (Path2D.Double) graphtransition.getGraph().getPath();
            if (contains(path, snappedpoint, 10)) {
                graphtransition.getGraph().setShapeColor(Color.ORANGE);
                selectedgraphtransitions.add(graphtransition);
                testcontroller.handleSelectedTransition(graphtransition.getGraph().getLabeledText());
            } else {
                graphtransition.getGraph().setShapeColor(Color.GRAY);
            }
        }
        if (selectedgraphstate == null && selectedgraphtransitions.size() == 1) {
            selectedgraphtransition = selectedgraphtransitions.get(0);
            Transition selectedtransition = (Transition) selectedgraphtransition;
            firstselectedctrlpoint.setLocation(selectedtransition.getControlX1(), selectedtransition.getControlY1());
            secondselectedctrlpoint.setLocation(selectedtransition.getControlX2(), selectedtransition.getControlY2());
        } else {
            selectedgraphtransition = null;
        }
        if (selectedgraphstate == null & selectedgraphtransition == null && selectedgraphtransitions.isEmpty()) {
            for (GraphElement graphlabel : testcontroller.getLabelGraphElementList()) {
                JLabel label = (JLabel) graphlabel.getGraph().getText();
                if (label.getX() <= snappedpointLabel.getX() && (label.getX() + label.getWidth()) >= snappedpointLabel.getX()
                        && label.getY() <= snappedpointLabel.getY() && (label.getY() + label.getHeight()) >= snappedpointLabel.getY()) {
                    selectedgraphlabel = graphlabel;
                    graphlabel.getGraph().setShapeColor(Color.ORANGE);
                    break;
                } else {
                    selectedgraphlabel = null;
                    graphlabel.getGraph().setShapeColor(Color.LIGHT_GRAY);
                }
            }
        }
        if (selectedgraphstate != null) {
            Command updatestatecommand = new UpdateStateCommand(testcontroller, selectedgraphstate, selectedgraphtransitions);
            updatestatecommand.execute();
            testcontroller.addCommand(updatestatecommand);
            testcontroller.handleSelectedState(selectedgraphstate.getGraph().getLabeledText());
        } else if (selectedgraphtransition != null) {
            Command updatetransitioncommand = new UpdateTransitionCommand(testcontroller, selectedgraphtransition);
            updatetransitioncommand.execute();
            testcontroller.addCommand(updatetransitioncommand);
            testcontroller.handleSelectedTransition(selectedgraphtransition.getGraph().getLabeledText());
        } else if (selectedgraphlabel != null) {
            Command updatelablecommand = new UpdateLabelCommand(testcontroller, selectedgraphlabel);
            updatelablecommand.execute();
            testcontroller.addCommand(updatelablecommand);
        }
    }

    /**
    * Handles mouse release events to reset control point selection flags after interacting with transitions.
    *
    * Workflow:
    * - If a transition is selected, resets the flags for the first and second control points.
    * - No action is taken if a state is selected.
    *
    * @param e The `MouseEvent` object containing details of the mouse release event.
    */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (selectedgraphstate != null) {
        } else if (selectedgraphtransition != null) {
            _firstselectedctrlpoint = false;
            _secondselectedctrlpoint = false;
        }
    }

    /**
    * Handles mouse click events to enable text editing for a selected label when double-clicked.
    *
    * Workflow:
    * - Checks for a double-click and ensures a label is selected.
    * - Prompts the user to enter new text via a dialog box.
    * - Updates the label text and executes an update command to reflect the changes.
    *
    * @param e The `MouseEvent` object containing details of the mouse click event.
    */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2 && selectedgraphlabel != null) {
            String text = JOptionPane.showInputDialog("Enter text:");
            if (text != null && !text.trim().isEmpty()) {
                Label label = (Label) selectedgraphlabel;
                label.setText(text);
                Command updatelablecommand = new UpdateLabelCommand(testcontroller, label);
                updatelablecommand.execute();
                testcontroller.addCommand(updatelablecommand);
            }
        }
    }

    /**
    * Handles mouse drag events to move the selected graphical element (state, transition, or label) within the testing environment.
    *
    * Workflow:
    * - Snaps the drag point to the grid for precise alignment.
    * - Moves the selected state, transition, or label based on the drag position:
    *   - Updates the position of the state and connected transitions.
    *   - Adjusts control points for transitions.
    *   - Updates the position of the label.
    *
    * @param e The `MouseEvent` object containing details of the mouse drag event.
    */
    @Override
    public void mouseDragged(MouseEvent e) {
        Point2D.Double snappedPoint = GridUtils.snapongrid(GridUtils.converttoPoint2D(e.getPoint()));
        Point2D.Double snappedPointLabel = GridUtils.converttoPoint2D(e.getPoint());
        if (selectedgraphstate != null) {
            moveSelectedState(snappedPoint.getX(), snappedPoint.getY());
        } else if (selectedgraphstate == null && selectedgraphtransition != null) {
            moveSelectedTransition(snappedPoint.getX(), snappedPoint.getY());
        } else if (selectedgraphlabel != null) {
            moveSelectedLabel(snappedPointLabel.getX(), snappedPointLabel.getY());
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {}
    
    /**
    * Handles key press events to delete the currently selected graphical element (state, transition, or label) when the Delete key is pressed.
    *
    * Workflow:
    * - Checks if the Delete key is pressed.
    * - Deletes the selected element:
    *   - Removes a state, transition, or label based on the current selection.
    *   - Executes the corresponding delete command to apply the changes.
    *   - Tracks the operation in the `TestController` for undo/redo functionality.
    *
    * @param e The `KeyEvent` object containing details of the key press event.
    */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE && selectedgraphstate != null) {
            State state = (State) selectedgraphstate;
            Command deletestatecommand = new DeleteStateCommand(testcontroller, state.getStateID());
            deletestatecommand.execute();
            testcontroller.addCommand(deletestatecommand);
        } else if (e.getKeyCode() == KeyEvent.VK_DELETE && selectedgraphtransition != null) {
            Transition transition = (Transition) selectedgraphtransition;
            Command deletetransitioncommand = new DeleteTransitionCommand(testcontroller, transition.getTransitionID());
            deletetransitioncommand.execute();
            testcontroller.addCommand(deletetransitioncommand);
        } else if (e.getKeyCode() == KeyEvent.VK_DELETE && selectedgraphlabel != null) {
            Label label = (Label) selectedgraphlabel;
            Command deletelablecommand = new DeleteLabelCommand(testcontroller, label.getLabelID());
            deletelablecommand.execute();
            testcontroller.addCommand(deletelablecommand);
        }
    }
     
    /**
    * Provides visual feedback for the selected transition by rendering its control points as small rectangles.
    *
    * Workflow:
    * - Checks if a transition is selected.
    * - Draws filled rectangles at the positions of the transition's control points.
    *
    * @param g The `Graphics2D` object used for rendering the graphical elements.
    */
    @Override
    public void paintComponent(Graphics2D g) {
        if (selectedgraphtransition != null) {
            Transition selectedtransition = (Transition) selectedgraphtransition;
            g.fill(new Rectangle.Double(selectedtransition.getControlX1() - 8 / 2, selectedtransition.getControlY1() - 8 / 2, 8, 8));
            g.fill(new Rectangle.Double(selectedtransition.getControlX2() - 8 / 2, selectedtransition.getControlY2() - 8 / 2, 8, 8));
        }
    }

    /**
    * Moves the selected state to a new position and updates the positions of any connected transitions.
    *
    * Workflow:
    * - Creates a point with the new coordinates.
    * - Iterates through the connected transitions:
    *   - Updates the start or end points of the transition if they are linked to the selected state.
    * - Updates the position of the selected state to the new coordinates.
    *
    * @param x The new x-coordinate for the state.
    * @param y The new y-coordinate for the state.
    */
    private void moveSelectedState(double x, double y) {
        State selectedState = (State) selectedgraphstate;
        Point2D.Double point = new Point2D.Double(x, y);
        if (!selectedgraphtransitions.isEmpty()) {
            for (GraphElement graphlement : selectedgraphtransitions) {
                Transition selectedTransition = (Transition) graphlement;
                if ((selectedState.getX() + 15 == selectedTransition.getStartX() && selectedState.getY() + 15 == selectedTransition.getStartY())) {
                    selectedTransition.setStartX(point.getX());
                    selectedTransition.setStartY(point.getY());
                }
                if ((selectedState.getX() + 15 == selectedTransition.getEndX() && selectedState.getY() + 15 == selectedTransition.getEndY())) {
                    selectedTransition.setEndX(point.getX());
                    selectedTransition.setEndY(point.getY());
                }
            }
        }
        selectedState.setX(point.getX() - 15);
        selectedState.setY(point.getY() - 15);
    }

    /**
    * Moves the control points of the selected transition based on the new mouse position.
    *
    * Workflow:
    * - Creates a point with the new coordinates.
    * - Checks if the point is within a close distance (20 units) of either the first or second control point:
    *   - Activates the corresponding control point flag if within range.
    * - Updates the position of the control points based on the active flag:
    *   - Adjusts the first control point if `_firstselectedctrlpoint` is true.
    *   - Adjusts the second control point if `_secondselectedctrlpoint` is true.
    *
    * @param x The new x-coordinate for the control point.
    * @param y The new y-coordinate for the control point.
    */
    private void moveSelectedTransition(double x, double y){
        Transition selectedtransition = (Transition) selectedgraphtransition;
         Point2D.Double point = new Point2D.Double(x, y);
        if (point.distance(firstselectedctrlpoint) < 20) {  
            _firstselectedctrlpoint = true;      
        } else if (point.distance(secondselectedctrlpoint) < 20) {    
            _secondselectedctrlpoint = true;       
        }   
        if(_firstselectedctrlpoint){
        selectedtransition.setControlX1(point.getX());
            selectedtransition.setControlY1(point.getY());
        }
        if(_secondselectedctrlpoint){
            selectedtransition.setControlX2(point.getX());
            selectedtransition.setControlY2(point.getY());
        }
    }
    
    /**
    * Determines if a given point lies within a specified tolerance of any line segment in a path.
    *
    * Workflow:
    * - Iterates through the segments of the given `Path2D.Double` object:
    *   - For `SEG_MOVETO` segments, initializes the starting point of a line segment.
    *   - For `SEG_LINETO` segments, creates a line and checks the distance from the point to the line.
    * - Returns `true` if the point is within the specified tolerance of any line segment, otherwise `false`.
    *
    * @param path The `Path2D.Double` object representing the path to check.
    * @param p The `Point2D` object representing the point to test.
    * @param tolerance The allowable distance from the path within which the point is considered "contained."
    * @return `true` if the point lies within the tolerance of any line segment in the path; `false` otherwise.
    */
    public boolean contains(Path2D.Double path, Point2D p, double tolerance) {
        PathIterator iterator = path.getPathIterator(null);
        double[] coords = new double[6];
        double[] firstcoord = new double[2];
        boolean first = true;
        while (!iterator.isDone()) {
            int segmenttype = iterator.currentSegment(coords);
            if (segmenttype == PathIterator.SEG_MOVETO) {
                firstcoord[0] = coords[0];
                firstcoord[1] = coords[1];
                first = false;
            } else if (segmenttype == PathIterator.SEG_LINETO) {
                Line2D line = new Line2D.Double(firstcoord[0], firstcoord[1], coords[0], coords[1]);
                if (line.ptSegDist(p) <= tolerance) {
                    return true;
                }
                firstcoord[0] = coords[0];
                firstcoord[1] = coords[1];
            }
            iterator.next();
        }
        return false;
    }
    
    /**
    * Updates the position of the selected label to new coordinates.
    *
    * @param x The new x-coordinate for the label.
    * @param y The new y-coordinate for the label.
    */
    public void moveSelectedLabel(double x, double y){
        Label label = (Label)selectedgraphlabel;
        label.setX((int)x);
        label.setY((int)y);
    } 
}

