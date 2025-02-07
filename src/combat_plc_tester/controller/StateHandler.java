// Package
package combat_plc_tester.controller;

// Imports
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Interface: StateHandler
 *
 * Purpose:
 * Defines the Strategy design pattern, which encapsulates a family of algorithms, 
 * allowing them to be interchangeable. This interface serves as a contract for handling 
 * user interactions and rendering components in a graphical user interface (GUI). 
 *
 * Design Patterns:
 * - **Strategy Pattern:**
 *   - Enables dynamic selection of behavior at runtime by decoupling the context 
 *     from specific implementations of state handling.
 *   - Concrete implementations of `StateHandler` 
 *     define the specific behavior for handling events and rendering.
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public interface StateHandler {
   
    public void mousePressed(MouseEvent e);
    public void mouseReleased(MouseEvent e);
    public void mouseClicked(MouseEvent e);
    public void mouseDragged(MouseEvent e);
    public void mouseMoved(MouseEvent e);
    public void keyPressed(KeyEvent e);
    public void paintComponent(Graphics2D g);
    
}

