// Package
package combat_plc_tester.model.IO;

/**
 * Class: InputValidationException
 *
 * Purpose: Represents an exception that is thrown when an input violates 
 * validation rules or business rules. 
 *
 * Constructors:
 * - InputValidationException(String message): Accepts a descriptive error 
 *   message detailing the cause of the exception.
 * - InputValidationException(String message, Throwable cause): Accepts both 
 *   a descriptive error message and a root cause (another throwable) to 
 *   provide additional context for the error.
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class InputValidationException extends Exception{
    public InputValidationException(String message) {
        super(message);
    }
    
    public InputValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}


