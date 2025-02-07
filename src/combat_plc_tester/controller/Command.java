// Package
package combat_plc_tester.controller;

/**
 * Interface: Command
 *
 * Purpose: 
 * Defines the Command design pattern, which encapsulates a request as an object, 
 * allowing for parameterization of clients with different requests, queuing of requests, 
 * and support for undoable operations. 
 *
 * Design Patterns:
 * - **Command Pattern:**
 *   - Allows for flexibility in executing, undoing, and redoing operations.
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public interface Command {
   public void execute();
   public void undo();
   public void redo();
}
