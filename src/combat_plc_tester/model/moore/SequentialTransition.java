// Package
package combat_plc_tester.model.moore;

// Imports
import java.io.Serializable;

/**
 * Class: SequentialTransition
 *
 * Purpose: Represents a sequential transition in the modified Moore-machine. 
 * A sequential transition is a transition from one state to another and contains specific inputs.
 * 
 * Notes: 
 * - This class implements the Serializable interface to enable
 * the storage of fields and objects as a bytestream.
 * 
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class SequentialTransition extends Transition implements Serializable{
    
    private static final long serialVersionUID = 121L;

    /**
     * Constructor: SequentialTransition
     *
     * Creates a SequentialTransition object with specific start and
     * end states, as well as graphical properties for rendering the transition
     * path.
     *
     * @param startStateID The ID of the state where the transition starts.
     * @param endStateID The ID of the state where the transition ends.
     * @param startX The x-coordinate of the starting point of the transition.
     * @param startY The y-coordinate of the starting point of the transition.
     * @param ctrlX1 The x-coordinate of the first control point.
     * @param ctrlY1 The y-coordinate of the first control point.
     * @param ctrlX2 The x-coordinate of the second control point.
     * @param ctrlY2 The y-coordinate of the second control point.
     * @param endX The x-coordinate of the end point of the transition.
     * @param endY The y-coordinate of the end point of the transition.
     */
    public SequentialTransition(String startStateID, String endStateID, double startX, double startY,
            double ctrlX1, double ctrlY1, double ctrlX2, double ctrlY2,
            double endX, double endY) {
        super(startStateID, endStateID, "sT", startX, startY, ctrlX1, ctrlY1, ctrlX2, ctrlY2, endX, endY);
    }

    /**
     * Constructor: SequentialTransition
     *
     * Creates a SequentialTransition object with only the start and
     * end states specified. 
     *
     * @param startStateID The ID of the state where the transition starts.
     * @param endStateID The ID of the state where the transition ends.
     */
    public SequentialTransition(String startStateID, String endStateID) {
        super(startStateID, endStateID, "sT", 0, 0, 0, 0, 0, 0, 0, 0);
    }

}
