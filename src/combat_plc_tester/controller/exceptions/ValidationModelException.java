// Package
package combat_plc_tester.controller.exceptions;

/**
 * Class: ValidationModelException
 *
 * Purpose: 
 * Represents an exception that is thrown when input validation fails. 
 * This exception is used to indicate issues related to data validation. 
 *
 * - This exception enables a clean separation between validation concerns and 
 *   business logic.
 * 
 * Constructors:
 * - `ValidationModelException(String message)`: Accepts a descriptive error 
 *   message explaining why the validation failed.
 * - `ValidationModelException(String message, Throwable cause)`: Accepts both 
 *   a descriptive error message and the underlying cause of the exception.
 * 
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class ValidationModelException extends Exception{
    
    public ValidationModelException(String message) {
        super(message);
    }
    
    public ValidationModelException(String message, Throwable cause) {
        super(message, cause);
    }
}