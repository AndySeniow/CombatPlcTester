// Package
package combat_plc_tester.controller;

/**
 * Interface: StateTestObserver
 * 
 * Purpose:
 * Defines the Observer interface for the Observer Pattern to monitor and report 
 * updates on state testing results. Implementing classes can subscribe to changes 
 * or events during the testing process and react accordingly.
 *
 * Design Pattern:
 * - **Observer Pattern:**
 * - Enables a publish-subscribe mechanism where `StateTestObserver` implementations 
 *     can receive notifications (test results or progress updates) from a subject 
 *     managing the testing process.
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public interface StateTestObserver {
    void update(String message);
}
