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
package combat_plc_tester.model.moore;

// Imports
import java.awt.Color;
import java.awt.Graphics;

/**
 * Interface: GraphElementRenderer
 *
 * Purpose: This interface defines the methods for the graphical representation
 * of the components of a Moore-machine, such as states (represented as
 * ellipses), transitions (represented as paths), and labels (represented as
 * text). It acts as the implementation side of the Bridge Pattern, decoupling
 * the abstraction (graph elements) from their graphical representation.
 *
 * Design Patterns: - Bridge: Separates the abstraction (graph elements like
 * State, Transition, and Label) from their graphical representation, allowing
 * for flexible rendering implementations.
 *
 * Notes: - This interface provides a flexible framework for rendering
 * Moore-machine components, enabling the graphical representation to be
 * customized or replaced without altering the core logic of the model.
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public interface GraphElementRenderer<T> {

    /**
     * Renders a labeled text at the specified position and size.
     *
     * @param x The x-coordinate of the text box.
     * @param y The y-coordinate of the text box.
     * @param width The width of the text box.
     * @param height The height of the text box.
     */
    void renderLabeledText(double x, double y, double width, double height);

    /**
     * Retrieves the graphical object representing the labeled text.
     *
     * @return The rendered text object of type T.
     */
    T getText();

    /**
     * Renders a labeled ellipse at the specified position and size.
     *
     * @param x The x-coordinate of the ellipse.
     * @param y The y-coordinate of the ellipse.
     * @param width The width of the ellipse.
     * @param height The height of the ellipse.
     */
    void renderLabeledEllipse(double x, double y, double width, double height);

    /**
     * Retrieves the graphical object representing the labeled ellipse.
     *
     * @return The rendered ellipse object of type T.
     */
    T getEllips();

    /**
     * Renders a labeled path using the specified start point, control points
     * for curvature, and end point.
     *
     * @param startX The x-coordinate of the starting point of the path.
     * @param startY The y-coordinate of the starting point of the path.
     * @param ctrlX1 The x-coordinate of the first control point.
     * @param ctrlY1 The y-coordinate of the first control point.
     * @param ctrlX2 The x-coordinate of the second control point.
     * @param ctrlY2 The y-coordinate of the second control point.
     * @param endX The x-coordinate of the ending point of the path.
     * @param endY The y-coordinate of the ending point of the path.
     */
    void renderLabeledPath(double startX, double startY, double ctrlX1, double ctrlY1,
            double ctrlX2, double ctrlY2, double endX, double endY);

    /**
     * Retrieves the graphical object representing the labeled path.
     *
     * @return The rendered path object of type T.
     */
    T getPath();

    /**
     * Sets the text label to be displayed with the graphical element.
     *
     * @param labeledtext The text to be displayed.
     */
    void setLabeledText(String labeledtext);

    /**
     * Retrieves the current text label associated with the graphical element.
     *
     * @return The text label as a String.
     */
    String getLabeledText();

    /**
     * Sets the color of the graphical shape.
     *
     * @param shapecolor The color to apply to the shape.
     */
    void setShapeColor(Color shapecolor);

    /**
     * Sets the color of the text label.
     *
     * @param labeledtextcolor The color to apply to the text label.
     */
    void setLabeledTextColor(Color labeledtextcolor);

    /**
     * Draws the graphical element on the provided Graphics object.
     *
     * @param g The Graphics object used to render the graphical element.
     */
    void draw(Graphics g);
}
