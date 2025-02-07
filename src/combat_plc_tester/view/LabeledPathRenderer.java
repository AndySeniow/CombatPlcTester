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
    *    - Adds line segments to the path for the provided control points (`ctrlX1`, `ctrlY1` 
    *      and `ctrlX2`, `ctrlY2`), ensuring these points are not redundant with the endpoint.
    * 3. **Determine Arrowhead Position**:
    *    - Calculates the position and orientation of an arrowhead near the endpoint based 
    *      on the vector direction of the last segment.
    * 4. **Append Arrowhead**:
    *    - Adds a circular arrowhead to the path at the computed position (`circleX`, `circleY`).
    * 
    * Notes:
    * - This method ensures that redundant control points are ignored to avoid unnecessary 
    *   or degenerate segments in the path.
    * - The arrowhead is represented by a small circle at the end of the transition path.
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
        path = new Path2D.Double();
        path.moveTo(startX, startY);
        if (!(ctrlX1 == endX && ctrlY1 == endY)) {
            path.lineTo(ctrlX1, ctrlY1);
        }
        if (!(ctrlX2 == endX && ctrlY2 == endY)) {
            path.lineTo(ctrlX2, ctrlY2);
        }
        path.lineTo(endX, endY);
        double laststartX = startX;
        double laststartY = startY;
        if (!(ctrlX2 == endX && ctrlY2 == endY)) {
            laststartX = ctrlX2;
            laststartY = ctrlY2;
        } else if (!(ctrlX1 == endX && ctrlY1 == endY)) {
            laststartX = ctrlX1;
            laststartY = ctrlY1;
        }
        double dx = endX - laststartX;
        double dy = endY - laststartY;
        double length = Math.sqrt(dx * dx + dy * dy);
        if (length == 0) {
            length = 1;
        }
        double offsetX = (30 / length) * dx;
        double offsetY = (30 / length) * dy;
        double circleX = endX - offsetX;
        double circleY = endY - offsetY;
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
    * 4. **Midpoint Calculation**:
    *    - Calculates the midpoint of the transition path using the `getMidPoint()` method.
    * 5. **Label Drawing**:
    *    - If a midpoint is calculated, renders the label text near the midpoint,
    *      offset to avoid overlapping with the path.
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
            Point2D midpoint = getMidPoint(path);
            if (midpoint != null) {
                g2.setColor(this.labeledtextcolor);
                g2.drawString(labeledtext, (float) midpoint.getX()-15, (float) midpoint.getY()-15);
            }     
    }
    
    /**
    * Calculates the midpoint of a transition's path.
    *
    * Workflow:
    * 1. **Path Iteration**:
    *    - Iterates over the segments of the curve using a `PathIterator`.
    *    - Extracts coordinates for each segment (`SEG_MOVETO` or `SEG_LINETO`).
    * 2. **Key Points Extraction**:
    *    - Captures the coordinates of the first and second segments that represent points along the curve.
    *    - Stops after capturing the second point.
    * 3. **Midpoint Calculation**:
    *    - Computes the midpoint coordinates as the average of the x and y coordinates of the two points.
    *    - Returns the midpoint as a `Point2D.Double` object.
    *
    * @param curve the `Path2D.Double` object representing the transition's path.
    * @return a `Point2D.Double` object representing the midpoint of the path, or `null` if the path is invalid or insufficient.
    */
    private Point2D getMidPoint(Path2D.Double curve) {
        PathIterator iterator = curve.getPathIterator(null);
        double[] coords = new double[6];
        Point2D firstpoint = null;
        Point2D secondpoint = null;
        int segmentcount = 0;
        while (!iterator.isDone()) {
            int type = iterator.currentSegment(coords);
            if (type == PathIterator.SEG_MOVETO || type == PathIterator.SEG_LINETO) {
                segmentcount++;
                if (segmentcount == 2) {
                    firstpoint = new Point2D.Double(coords[0], coords[1]);
                } else if (segmentcount == 3) {
                    secondpoint = new Point2D.Double(coords[0], coords[1]);
                    break;
                }
            }
            iterator.next();
        }
        if (firstpoint != null && secondpoint != null) {
            double midX = (firstpoint.getX() + secondpoint.getX()) / 2;
            double midY = (firstpoint.getY() + secondpoint.getY()) / 2;
            return new Point2D.Double(midX, midY);
        }
        return null;
    }
}
