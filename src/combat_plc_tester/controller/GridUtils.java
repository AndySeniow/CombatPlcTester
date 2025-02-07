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
import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * Class: GridUtils
 * 
 * Purpose:
 * - Provides utility methods for working with points and grids in a graphical environment.
 * 
 * Key Responsibilities:
 * 1. Conversion:
 *    - Converts a `Point` object to a `Point2D.Double` object to work with floating-point precision.
 * 2. Grid Alignment:
 *    - Snaps a given `Point2D.Double` on the nearest point on a grid defined by a fixed grid size.
 * 
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class GridUtils {
  
    private static final double GRID_SIZE = 20.0;        // Size of the grid.

    /**
    * Converts an integer-based `Point` into a `Point2D.Double` with floating-point precision.
    *
    * @param point the `Point` to convert
    * @return a `Point2D.Double` with the same coordinates as the input point
    */
    public static Point2D.Double converttoPoint2D(Point point) {
        return new Point2D.Double(point.getX(), point.getY());
    }

    /**
    * Aligns a `Point2D.Double` on the nearest grid point based on a predefined grid size.
    *
    * @param point the `Point2D.Double` to snap to the grid
    * @return a new `Point2D.Double` snapped on the nearest grid point
    */
    public static Point2D.Double snapongrid(Point2D.Double point) {
        double x = Math.round(point.x / GRID_SIZE) * GRID_SIZE;
        double y = Math.round(point.y / GRID_SIZE) * GRID_SIZE;
        return new Point2D.Double(x, y);
    }
}
