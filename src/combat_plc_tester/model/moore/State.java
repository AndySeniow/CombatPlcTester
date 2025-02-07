// Package
package combat_plc_tester.model.moore;

// Imports
import combat_plc_tester.model.IO.Output;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class: State
 *
 * Purpose: Represents a state in the modified Moore-machine. This class extends
 * the abstract GraphElement and provides specific functionality and attributes
 * related to a state.
 *
 * Design Patterns: - Bridge: Decouples the abstraction (State) from its
 * graphical implementation.
 *
 * Notes: 
 * - This class implements the Serializable interface to enable
 * the storage of fields and objects as a bytestream.
 * - The state is graphically represented as an ellipse with its unique ID.
 * 
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class State extends GraphElement implements Serializable {

    private static final long serialVersionUID = 110L;
   
    private String name;                    // State name.
    private String stateID;                 // State unique ID.
    private static int number = 0;          // ID counter.
    private boolean startstate = false;     // Start state indicator.
    private List<Output> outputs;           // Outputs list.
    private int timercondition;             // Timer condition (ms).
    private int timerconditiontolerance;    // Timer condition tolerance (ms).
    private double x;                       // X-coordinate of the ellipse.
    private double y;                       // Y-coordinate of the ellipse.
    private double width;                   // Graphical width of the ellipse.
    private double height;                  // Graphical height of the ellipse.
  
    /**
     * Constructor: State
     *
     * Creates a new State object with the specified name, position,
     * and dimensions. If this is the first state created, it is marked as the
     * start state ("S0"). Each state is assigned a unique ID based on an
     * incrementing counter.
     * 
     * Notes: The state is graphically represented as an ellipse with its unique ID 
     * 
     * @param x The x-coordinate of the state's graphical representation.
     * @param y The y-coordinate of the state's graphical representation.
     * @param width The width of the state's graphical representation.
     * @param height The height of the state's graphical representation.
     */
    public State(double x, double y, double width, double height) {
        if (number == 0) {
            this.startstate = true;
        }
        this.stateID = "S" + number;
        number += 1;
        this.outputs = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public static int getObjectCount(){
        return number;
    }
    
    public static void setObjectcount(int objectcount){
        number = objectcount;
    }
    
    public int getTimerCondition() {
        return this.timercondition;
    }

    /**
     * Sets the timer condition for the state.
     * If the timer condition is valid (not negative).
     * 
     * @param timercondition The timer condition in milliseconds.
     */
    public void setTimerCondition(int timercondition) {
        this.timercondition = timercondition;
    }

    public int getTimerConditionTolerance() {
        return this.timerconditiontolerance;
    }

    /**
     * Sets the timer condition tolerance for the state. If the timer condition
     * tolerance is valid (not negative).
     * 
     * @param timerconditiontolerance The timer condition tolerance in
     * milliseconds.
     */
    public void setTimerConditionTolerance(int timerconditiontolerance) {
        this.timerconditiontolerance = timerconditiontolerance;
    }

    public void addOutput(Output output) {
        this.outputs.add(output);
    }

    public void deleteOutput(Output output) {
         this.outputs.remove(output);
    }
    
    public List<Output> getOutputs() {
        return this.outputs;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStateID() {
        return this.stateID;
    }

    public boolean isStartstate() {
        return this.startstate;
    }

    public void setStartstate(boolean startstate) {
        this.startstate = startstate;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Retrieves the GraphElementRenderer and renders the graphical
     * representation of this state.
     *
     * @return The GraphElementRenderer used for rendering this state.
     */
    @Override
    public GraphElementRenderer getGraph() {
        renderer.renderLabeledEllipse(x, y, width, height);
        renderer.setLabeledText(stateID);
        return renderer;
    }
}
