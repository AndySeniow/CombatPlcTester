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
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.io.Serializable;
import javax.swing.JLabel;

/**
 * Class: LabeledPathRenderer
 *
 * Purpose:
 * This class serves as the graphical representation of a transition in a path, implementing 
 * the Bridge pattern to decouple the abstraction (the transition concept) from its graphical 
 * implementation. It defines the visual appearance of transitions, including lines and an 
 * arrowhead, represented by a small circle at the endpoint.
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
public class LabeledPathRenderer implements GraphElementRenderer, Serializable {

    private static final long serialVersionUID = 302L;
    private transient Path2D.Double path;                      // The graphical representation of a transition.
    private String labeledtext;                                // The graphical representation of the text.
    private Color shapecolor;                                  // The graphical color of the transition.
    private Color labeledtextcolor;                            // The graphical color of the text.
    
    @Override
    public void renderLabeledText(double x, double y, double width, double height) {    
    }

    @Override
    public JLabel getText() {
       return null;
    }
    
    @Override
    public void renderLabeledEllipse(double x, double y, double width, double height) {
    }

    @Override
    public Ellipse2D.Double getEllips(){
         return null;
     }
    
    /**
    * Creates a labeled path representing a transition in the graphical model.
    * 
    * Workflow:
    * 1. **Initialize Path**:
    *    - Starts the path at the specified starting coordinates (`startX`, `startY`).
    * 2. **Define Line Segments**:
    *    - Creating and drawing a path consisting of (at most) three line segments.
    * 3. **Determine Arrowhead Position as a circle**:
    *    - Calculates the position of a circle near the endpoint.
    * 4. **Append Arrowhead as a circle**:
    *    - Adds a circular arrowhead to the path (`circleX`, `circleY`).
    * 
    * Notes:
    * - The method ensures that redundant control points are ignored.
    * - The arrowhead is represented is a small circle at the end of the transition path.
    * 
    * @param startX The X-coordinate of the starting point of the path.
    * @param startY The Y-coordinate of the starting point of the path.
    * @param ctrlX1 The X-coordinate of the first control point.
    * @param ctrlY1 The Y-coordinate of the first control point.
    * @param ctrlX2 The X-coordinate of the second control point.
    * @param ctrlY2 The Y-coordinate of the second control point.
    * @param endX The X-coordinate of the endpoint of the path.
    * @param endY The Y-coordinate of the endpoint of the path.
    */
    @Override
    public void renderLabeledPath(double startX, double startY, double ctrlX1, double ctrlY1,
            double ctrlX2, double ctrlY2, double endX, double endY) {
        //Creating and drawing a path consisting of three line segments.
        path = new Path2D.Double();
        //Draw first linesegment.
        path.moveTo(startX, startY);
        if (!(ctrlX1 == endX && ctrlY1 == endY)) {
            path.lineTo(ctrlX1, ctrlY1);
        }
        //Draw second linesegment.
        if (!(ctrlX2 == endX && ctrlY2 == endY)) {
            path.lineTo(ctrlX2, ctrlY2);
        }
        //Draw third linesegment.
        path.lineTo(endX, endY);
        
        // Find the last control point used before the end point, to calculate direction.
        double laststartX = startX;
        double laststartY = startY;
        if (!(ctrlX2 == endX && ctrlY2 == endY)) {
            laststartX = ctrlX2;
            laststartY = ctrlY2;
        } else if (!(ctrlX1 == endX && ctrlY1 == endY)) {
            laststartX = ctrlX1;
            laststartY = ctrlY1;
        }
        
        //Calculate the distance between the last control point and the end point.
        double differenceX = endX - laststartX;
        double differenceY = endY - laststartY;
        
        // Use of the Pythagorean formula to calculate the distance.
        // If the last control points are equal to the endpoint, the distance may still be non-zero.
        double distance = Math.sqrt(differenceX * differenceX + differenceY * differenceY);
        if (distance == 0) {
            distance = 1;
        }
        
        // Calculate the coordinates for the arrowhead to stay slightly away from the endpoint (marge).
        // The arrowhead (circle) must not intersect with the state (circle).
        double marginX = (30 / distance) * differenceX;
        double marginY = (30 / distance) * differenceY;
        
        // Calculate the coordinates for the arrowhead as a circle.
        double circleX = endX - marginX;
        double circleY = endY - marginY;
        double circleradius = 5;
        Ellipse2D.Double circle = new Ellipse2D.Double(circleX - circleradius, circleY - circleradius, circleradius * 2, circleradius * 2);
        path.append(circle, false);
    }

    @Override
    public Path2D.Double getPath(){
         return path;
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
    * Renders a graphical representation of a transition path and its associated label.
    *  
    * Workflow:
    * 1. **Graphics Configuration**:
    *    - Sets up a `Graphics2D` object with specific stroke and color properties.
    *    - Uses a `BasicStroke` with specified width to ensure the path is visually distinct.
    * 2. **Path Drawing**:
    *    - Draws the transition path (`path`) using the configured `Graphics2D` object.
    * 3. **Font and Text Configuration**:
    *    - Configures the font settings for the label text.
    * 4. **Middlepoint Calculation**:
    *    - Calculates the middlepoint of the transition path using the `calculateMiddlePoint()` method.
    * 5. **Label Drawing**:
    *    - If a middlepoint is calculated, renders the label text near the midpoint,
    *
    * @param g the `Graphics` object used for rendering the path and text.
    */
    @Override
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        BasicStroke stroke = new BasicStroke(3);
        g2.setStroke(stroke);
        g2.setStroke(new BasicStroke(2));
        g2.setColor(this.shapecolor);
        g2.draw(path);
        Font font = new Font("Arial", Font.PLAIN, 14);
            g2.setFont(font);
            Point2D middlepoint = calculateMiddlePoint(path);
            if (middlepoint != null) {
                g2.setColor(this.labeledtextcolor);
                g2.drawString(labeledtext, (float) middlepoint.getX()-15, (float) middlepoint.getY()-15);
            }     
    }
    
    /**
     * Calculates a point on the path depending on how many line segments are
     * present:
     *
     * Workflow: 
     * 1. **Path Traversal**: - Uses a `PathIterator` to iterate
     * through all segments of the given path.
     *
     * 2. **Linesegments**: - `firstpoint`: the initial `moveTo` point. -
     * `secondpoint`, `thirdpoint`, `fourthpoint`: successive points from up to
     * three `lineTo` segments.
     *
     * 3. **Point Selection Logic**: 
     * - If 3 segments are present (4 points): return middlepoint between second and third point. 
     * - If 2 segments are present (3 points): return middlepoint of second point. 
     * - If 1 segment is present (2 points): return middlepoint between first and second point. 
     * - If fewer than 2 points: return `null`.
     *
     * @param curve the `Path2D.Double` object containing 1 to 3 line segments.
     * @return the calculated middlepoint depending on the number of segments,
     * or `null` if invalid.
     */
    private Point2D calculateMiddlePoint(Path2D.Double curve) {
        PathIterator iterator = curve.getPathIterator(null);
        //Recommended coordinates for a path.
        double[] coordinates = new double[6];
        Point2D firstpoint = null;
        Point2D secondpoint = null;
        Point2D thirdpoint = null;
        Point2D fourthpoint = null;
        //Count of linesegments.
        int linesegmentcount = 0;
        while (!iterator.isDone()) {
            int type = iterator.currentSegment(coordinates);
            //Startingpoint of the path.
            if (type == PathIterator.SEG_MOVETO) {
                if (firstpoint == null) {
                    firstpoint = new Point2D.Double(coordinates[0], coordinates[1]);
                }
            //Next coordinates of the linesegments.
            } else if (type == PathIterator.SEG_LINETO) {
                linesegmentcount++;
                if (linesegmentcount == 1) {
                    secondpoint = new Point2D.Double(coordinates[0], coordinates[1]);
                } else if (linesegmentcount == 2) {
                    thirdpoint = new Point2D.Double(coordinates[0], coordinates[1]);
                } else if (linesegmentcount == 3) {
                    fourthpoint = new Point2D.Double(coordinates[0], coordinates[1]);
                }
            }
            iterator.next();
        }
        //Calculate middlepoint of path of 3 linesegments. 
        if (firstpoint != null && fourthpoint != null) {
            double midX = (secondpoint.getX() + thirdpoint.getX()) / 2;
            double midY = (secondpoint.getY() + thirdpoint.getY()) / 2;
            return new Point2D.Double(midX, midY);
        //Calculate middlepoint of path of 2 linesegments.
        } else if (firstpoint != null && thirdpoint != null) {
            double midX = (secondpoint.getX() + secondpoint.getX()) / 2;
            double midY = (secondpoint.getY() + secondpoint.getY()) / 2;
            return new Point2D.Double(midX, midY);
        //Calculate middlepoint of path of 1 linesegment.
        } else if (firstpoint != null && secondpoint != null) {
            double midX = (firstpoint.getX() + secondpoint.getX()) / 2;
            double midY = (firstpoint.getY() + secondpoint.getY()) / 2;
            return new Point2D.Double(midX, midY);
        }
        return null;
    }
}
