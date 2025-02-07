// Package
package combat_plc_tester.controller.exceptions;

/**
 * Class: BusinessModelException
 *
 * Purpose: Represents an exception that occurs when a business rule is violated 
 * or a model-level error is encountered. This exception is typically used at 
 * the controller level to centralize error handling and manage business rule 
 * validation, ensuring that the complexity is handled efficiently.
 *
 *
 * - This exception simplifies error handling by allowing the controller to 
 *   catch and process errors consistently, reducing complexity across the 
 *   application.
 * - Enables a clean separation of concerns by isolating business rule violations 
 *   from the underlying model logic.
 *
 * Constructors:
 * - `BusinessModelException(String message)`: Accepts a descriptive error 
 *   message detailing the reason for the exception.
 * - `BusinessModelException(String message, Throwable cause)`: Accepts both a 
 *   descriptive error message and a root cause (another throwable) to provide 
 *   additional context for the error.
 *
 */
public class BusinessModelException extends Exception{
    
    public BusinessModelException(String message) {
        super(message);
    }
    
    public BusinessModelException(String message, Throwable cause) {
        super(message, cause);
    }
}