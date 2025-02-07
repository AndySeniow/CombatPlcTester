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
 *    - Snaps a given `Point2D.Double` to the nearest point on a grid defined by a fixed grid size.
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
    public static Point2D.Double convertToPoint2D(Point point) {
        return new Point2D.Double(point.getX(), point.getY());
    }

    /**
    * Aligns a `Point2D.Double` to the nearest grid point based on a predefined grid size.
    *
    * @param point the `Point2D.Double` to snap to the grid
    * @return a new `Point2D.Double` snapped to the nearest grid point
    */
    public static Point2D.Double snapToGrid(Point2D.Double point) {
        double x = Math.round(point.x / GRID_SIZE) * GRID_SIZE;
        double y = Math.round(point.y / GRID_SIZE) * GRID_SIZE;
        return new Point2D.Double(x, y);
    }
}
