// Package
package combat_plc_tester.controller;

// Imports
import combat_plc_tester.view.TestView;
import javax.swing.SwingUtilities;

/**
 * Class: StatePlcTestObserver
 * 
 * Purpose:
 * Implements the `StateTestObserver` interface to observe and handle updates related to state testing 
 * for PLC systems. This class interacts with a `TestView` to display test results in the user interface, 
 * ensuring thread-safe updates.
 *
 * Design Pattern:
 * - **Observer Pattern:**
 *   - Acts as an observer, receiving updates from the subject and passing them to the `TestView`.
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class StatePlcTestObserver implements StateTestObserver{
    
    private TestView testview;                      // The test view;
    
    public StatePlcTestObserver(TestView testview){
        this.testview = testview;
    }
    
    /**
    * Handles notifications of test result updates and delegates the display of these results 
    * to the `TestView` component. Ensures thread-safe interaction with the Swing-based user interface.
    *
    * @param message A string containing the test result or update information to be displayed.
    */
    @Override
    public void update(String message) {
        SwingUtilities.invokeLater(() -> testview.viewTestResults(message));
    }   
}
