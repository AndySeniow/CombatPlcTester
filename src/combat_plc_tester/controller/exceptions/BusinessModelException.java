// Package
package combat_plc_tester.controller.exceptions;

/**
 * Class: BusinessModelException
 *
 * Purpose: Represents an exception that occurs when a business rule is violated 
 * or a model-level error is encountered. This exception is typically used at 
 * the controller level to centralize error handling.
 *
 *
 * - This exception simplifies error handling by allowing the controller to 
 *   catch and process errors.
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