// Package
package combat_plc_tester.model.IO;

/**
 * Class: OutputValidationException
 *
 * Purpose: Represents an exception that is thrown when an output violates 
 * validation rules or business rules.
 *
 * Constructors:
 * - OutputValidationException(String message): Accepts a descriptive error 
 *   message detailing the cause of the exception.
 * - OutputValidationException(String message, Throwable cause): Accepts both 
 *   a descriptive error message and a root cause (another throwable) to 
 *   provide additional context for the error.
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class OutputValidationException extends Exception{
    public OutputValidationException(String message) {
        super(message);
    }
    
    public OutputValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}


