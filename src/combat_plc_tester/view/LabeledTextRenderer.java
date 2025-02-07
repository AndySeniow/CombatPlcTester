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
package combat_plc_tester.view;

// Imports
import combat_plc_tester.model.moore.GraphElementRenderer;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.io.Serializable;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

/**
 * Class: LabeledTextRenderer
 * 
 * Purpose: 
 * Implements the `GraphElementRenderer` interface as part of the Bridge Pattern. This class is responsible for
 * graphically representing labeled information using a `JLabel`. It provides methods to set and render graphical
 * attributes such as the label's text, position, size, color, and border.
 * 
 * Design Patterns: 
 * - Bridge Pattern: Decouples the graphical representation (this renderer) from the logical abstraction.
 *
 * 
 * Notes:
 * - This class implements the Serializable interface to enable the storage
 *   of fields and objects as a bytestream.
 * 
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class LabeledTextRenderer implements GraphElementRenderer, Serializable {

    private static final long serialVersionUID = 303L;
    private transient JLabel label;                    // Label component for displaying text in the graphical representation.
    private String labeledtext;                        // Text to be displayed in the label.
    private Color shapecolor;                          // Color of the graphical shape.
    private Color labeledtextcolor;                    // Color of the label text.
    private double x;                                  // X-coordinate for the position of the shape.
    private double y;                                  // Y-coordinate for the position of the shape.
    private double width;                              // Width of the graphical shape.
    private double height;                             // Height of the graphical shape.
    private Border border = BorderFactory.createLineBorder(Color.BLACK); // Border for the shape, defaulting to a black line.

    /**
    * Renders a labeled text representation by initializing a `JLabel` instance and 
    * setting its dimensions and position.
    * 
    * @param x      The X-coordinate of the label's position.
    * @param y      The Y-coordinate of the label's position.
    * @param width  The width of the label.
    * @param height The height of the label.
    */
    @Override
    public void renderLabeledText(double x, double y, double width, double height) {
        this.label = new JLabel();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
    * Configures and returns the `JLabel` instance representing the labeled text.
    *
    * @return The configured `JLabel` instance.
    */
    @Override
    public JLabel getText() {
        label.setText(labeledtext);
        label.setLocation((int) x, (int) y);
        label.setBorder(border);
        label.setSize(getTextWidth(label), (int) height);
        label.setOpaque(true);
        label.setBackground(shapecolor);
        label.setForeground(labeledtextcolor);
        return label;
    }

    @Override
    public void renderLabeledEllipse(double x, double y, double width, double height) {
    }

    @Override
    public Ellipse2D.Double getEllips() {
        return null;
    }

    @Override
    public void renderLabeledPath(double startX, double startY, double ctrlX1, double ctrlY1,
            double ctrlX2, double ctrlY2, double endX, double endY) {
    }

    @Override
    public Path2D.Double getPath() {
        return null;
    }

    @Override
    public void setLabeledText(String labeledtext) {
        this.labeledtext = labeledtext;
    }

    @Override
    public String getLabeledText() {
        return this.labeledtext;
    }

    @Override
    public void setShapeColor(Color shapecolor) {
        this.shapecolor = shapecolor;
    }

    @Override
    public void setLabeledTextColor(Color labeledtextcolor) {
        this.labeledtextcolor = labeledtextcolor;
    }

    @Override
    public void draw(Graphics g) {
    }

    /**
    * Calculates the width of the text displayed in the given `JLabel` based on its font and content.
    * 
    * @param label The `JLabel` whose text width is to be calculated.
    * @return The width of the label's text in pixels.
    */
    private int getTextWidth(JLabel label) {
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        return metrics.stringWidth(label.getText());
    }
}
