// Package
package combat_plc_tester.model.moore;

// Imports
import combat_plc_tester.model.IO.Input;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class: Transition
 *
 * Purpose: Represents a transition in the modified Moore-machine. This class 
 * extends the abstract GraphElement and provides specific functionality and 
 * attributes related to a transition. 
 *
 * Design Patterns:
 * - Bridge: Decouples the abstraction (Transition) from its graphical implementation.
 * 
 * Notes: 
 * - The transition contains the IDs of the states where it starts and ends.
 * - The Transition is graphically represented as a path with 4 points and its unique ID.
 * - This class implements the Serializable interface to enable
 * the storage of fields and objects as a bytestream.
 * 
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public abstract class Transition extends GraphElement implements Serializable{

    private static final long serialVersionUID = 120L;
    
    private String name;                    // Transition name.
    private String startStateID;            // Start state ID.
    private String endStateID;              // End state ID.
    private String transitionID;            // Transition unique ID.
    private static int number = 0;          // ID counter.
    private boolean aftertimecondition;     // After time condition.
    private double startX;                  // Start X-coordinate of the transition.
    private double startY;                  // Start Y-coordinate of the transition.
    private double ctrlX1;                  // Second X-coordinate of the transtion.
    private double ctrlY1;                  // Second Y-coordinate of the transtion.
    private double ctrlX2;                  // Third X-coordinate of the transtion.
    private double ctrlY2;                  // Third Y-coordinate of the transtion.
    private double endX;                    // End X-coordinate of the transition.
    private double endY;                    // End Y-coordinate of the transition.
    private List<Input> inputs;             // List of inputs.

    /**
     * Constructor: Transition
     *
     * Initializes a new Transition object with specified properties,
     * including start and end state IDs, a unique transition ID, input list,
     * and graphical properties to represent the transition as a path with
     * control points.
     *
     * @param startStateID The ID of the state where the transition starts.
     * @param endStateID The ID of the state where the transition ends.
     * @param prefixID A prefix used to generate the unique transition ID.
     * @param startX The x-coordinate of the start point of the transition.
     * @param startY The y-coordinate of the start point of the transition.
     * @param ctrlX1 The x-coordinate of the first control point for the
     * transition path.
     * @param ctrlY1 The y-coordinate of the first control point for the
     * transition path.
     * @param ctrlX2 The x-coordinate of the second control point for the
     * transition path.
     * @param ctrlY2 The y-coordinate of the second control point for the
     * transition path.
     * @param endX The x-coordinate of the end point of the transition.
     * @param endY The y-coordinate of the end point of the transition.
     */
    public Transition(String startStateID, String endStateID, String prefixID, double startX, double startY, 
                      double ctrlX1, double ctrlY1, double ctrlX2, double ctrlY2, 
                      double endX, double endY) {
        this.transitionID = prefixID + number;
        number += 1;
        this.inputs = new ArrayList<>();
        this.aftertimecondition = false;
        this.startStateID = startStateID;
        this.endStateID = endStateID;
        this.startX = startX;
        this.startY = startY;
        this.ctrlX1 = ctrlX1;
        this.ctrlY1 = ctrlY1;
        this.ctrlX2 = ctrlX2;
        this.ctrlY2 = ctrlY2;
        this.endX = endX;
        this.endY = endY;
    }
    
    public static int getObjectCount(){
        return number;
    }
    
    public static void setObjectcount(int objectcount){
        number = objectcount;
    }
    
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getStartStateID() {
        return this.startStateID;
    }

    public void setStartStateID(String startStateID) {
        this.startStateID = startStateID;
    }

    public String getEndStateID() {
        return this.endStateID;
    }

    public void setEndStateID(String endStateID) {
        this.endStateID = endStateID;
    }

    public String getTransitionID() {
        return this.transitionID;
    }

    public void addInput(Input input) {
        this.inputs.add(input);
    }

    public void deleteInput(Input input){
        this.inputs.remove(input);
    }
    
    public List<Input> getInputs() {
        return this.inputs;
    }

    public void setAfterTimeCondition(boolean aftertimecondition) {
        this.aftertimecondition = aftertimecondition;
    }

    public boolean getAfterTimeCondition() {
        return this.aftertimecondition;
    }
    
     public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public double getControlX1() {
        return ctrlX1;
    }

    public void setControlX1(double ctrlX1) {
        this.ctrlX1 = ctrlX1;
    }

    public double getControlY1() {
        return ctrlY1;
    }

    public void setControlY1(double ctrlY1) {
        this.ctrlY1 = ctrlY1;
    }

     public double getControlX2() {
        return ctrlX2;
    }

    public void setControlX2(double ctrlX2) {
        this.ctrlX2 = ctrlX2;
    }

    public double getControlY2() {
        return ctrlY2;
    }

    public void setControlY2(double ctrlY2) {
        this.ctrlY2 = ctrlY2;
    }
    
    public double getEndX() {
        return endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public double getEndY() {
        return endY;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }
    
    /**
     * Retrieves the GraphElementRenderer and renders the graphical
     * representation of this transition.
     *
     * @return The GraphElementRenderer used for rendering this state.
     */
    @Override
    public GraphElementRenderer getGraph() {
        renderer.renderLabeledPath(startX, startY, ctrlX1, ctrlY1, ctrlX2, ctrlY2, endX, endY);
         renderer.setLabeledText(transitionID);
         return renderer;
     }  
}
