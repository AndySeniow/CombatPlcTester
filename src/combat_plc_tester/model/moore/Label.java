// Package
package combat_plc_tester.model.moore;

// Imports
import java.io.Serializable;

/**
 * Class: Label
 *
 * Purpose: Represents a label in the modified Moore-machine. This class extends
 * the abstract GraphElement and provides specific functionality and attributes
 * related to labels, such as textual content and positioning.
 *
 * Design Patterns:
 * - Bridge: Decouples the abstraction (Label) from its graphical implementation.
 *
 * Notes:
 * - This class implements the Serializable interface to enable the storage
 *   of fields and objects as a bytestream.
 * - The label is graphically represented as text within the graphical model.
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class Label extends GraphElement implements Serializable{

    private static final long serialVersionUID = 103L;

    private String text;                    // Text.
    private String labelID;                 // Label unique ID.
    private static int number = 0;          // ID counter.
    private double x;                       // X-coordinate of the label.
    private double y;                       // Y-coordinate of the ellipse.
    private double width;                   // Graphical width of the label.
    private double height;                  // Graphical height of the ellipse.

    /**
     * Constructor: Label
     *
     * Initializes a new Label object with specified text, position,
     * and dimensions. Each label is assigned a unique ID for identification
     * within the modified Moore-machine.
     *
     * @param text The text content of the label.
     * @param x The x-coordinate of the label's position on the graphical
     * canvas.
     * @param y The y-coordinate of the label's position on the graphical
     * canvas.
     * @param width The width of the label's graphical representation.
     * @param height The height of the label's graphical representation.
     */
    public Label(String text, double x, double y, double width, double height) {      
        this.text = text;
        this.labelID = "L" + number;
        number += 1;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public String getLabelID() {
        return labelID;
    }

    public void setLabelID(String labelID) {
        this.labelID = labelID;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        return width;
    }

    public void setHeight(double height) {
        this.height = height;
    }
    
     /**
     * Retrieves the GraphElementRenderer and renders the graphical
     * representation of this label.
     *
     * @return The GraphElementRenderer used for rendering this label.
     */
    @Override
    public GraphElementRenderer getGraph() {
      renderer.renderLabeledText((int)x, (int)y, (int)width, (int)height);
      renderer.setLabeledText(text);
       return renderer;
    }  
}
