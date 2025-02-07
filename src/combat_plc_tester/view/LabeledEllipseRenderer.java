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
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.io.Serializable;
import javax.swing.JLabel;

/**
 * Class: LabeledEllipseRenderer
 * 
 * Purpose:
 * Implements the graphical representation of a state using the Bridge Pattern. 
 * This class acts as the concrete implementation for rendering graphical elements 
 * (ellipses and labels) that represent states in a state diagram. The graphical 
 * attributes and their rendering logic are separated from the model.
 * 
 * Design Patterns: 
 * - Bridge Pattern: Decouples the abstraction (graphical representation) from its 
 *   implementation (rendering logic), allowing both to evolve independently.
 * 
 * Notes:
 * - This class implements the Serializable interface to enable the storage
 *   of fields and objects as a bytestream.
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class LabeledEllipseRenderer implements GraphElementRenderer, Serializable{

    private static final long serialVersionUID = 301L;
    private transient Ellipse2D.Double ellipse;                 // The graphical representation of a state.
    private String labeledtext;                                 // The graphical representation of the text.
    private Color shapecolor;                                   // The graphical color of the state.
    private Color labeledtextcolor;                             // The graphical color of the text.

    /**
     * Renders an ellipse with the specified dimensions.
     * 
     * @param x      the x-coordinate of the ellipse.
     * @param y      the y-coordinate of the ellipse.
     * @param width  the width of the ellipse.
     * @param height the height of the ellipse.
     */
    @Override 
    public void renderLabeledEllipse(double x, double y, double width, double height) {
        ellipse = new Ellipse2D.Double();
        ellipse.x = x;
        ellipse.y = y;
        ellipse.width = width;
        ellipse.height = height;
    }

    @Override
    public void renderLabeledText(double x, double y, double width, double height) {}

    @Override
    public JLabel getText() {
       return null;
    }  
    
    @Override
    public Ellipse2D.Double getEllips(){
         return ellipse;
     }
    
    @Override
    public void renderLabeledPath(double startX, double startY, double ctrlX1, double ctrlY1,
                                  double ctrlX2, double ctrlY2, double endX, double endY) {}
    
    @Override
    public Path2D.Double getPath(){
         return null;
    }
    
    @Override
    public void setLabeledText(String labeledtext){
        this.labeledtext = labeledtext;
    }
    
    @Override
    public String getLabeledText(){
       return this.labeledtext;
    }
    
    @Override 
    public void setShapeColor(Color shapecolor){
        this.shapecolor = shapecolor;
    }
    
    @Override 
    public void setLabeledTextColor(Color labeledtextcolor){
        this.labeledtextcolor = labeledtextcolor;
    }
    
    /**
    * Draws the ellipse and labeled text on the specified `Graphics` object.
    * 
    * Workflow:
    * - This method uses a `Graphics2D` object to perform advanced 2D rendering.
    * - Sets the stroke and fill color for the ellipse.
    * - Fills the ellipse using the predefined dimensions (`Ellipse2D.Double`).
    * - Configures the font and calculates the position to center the label within the ellipse.
    * - Renders the label text in the center of the ellipse.
    * 
    * @param g the `Graphics` object used to draw the graphical representation.
    */
    @Override
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g2.setColor(this.shapecolor);
        g2.fill(ellipse);
        Font font = new Font("Arial", Font.PLAIN, 14);
        g2.setFont(font);
        int textX = (int) (ellipse.getX() + (ellipse.getWidth() / 2) - g2.getFontMetrics().stringWidth(labeledtext) / 2);
        int textY = (int) (ellipse.getY() + (ellipse.getHeight() / 2) + g2.getFontMetrics().getAscent() / 2);
        g2.setColor(this.labeledtextcolor);
        g2.drawString(labeledtext, textX, textY);
    }
}
 

