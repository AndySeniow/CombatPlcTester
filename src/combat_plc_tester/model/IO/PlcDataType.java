// Package
package combat_plc_tester.model.IO;

/**
 * Enum: PlcDataType
 *
 * Purpose: Defines the various Siemens PLC data types that are supported within 
 * the application. Each value in this enum represents a specific data type used 
 * in PLC programming.
 *
 * Supported Data Types:
 * - BIT: Represents a single bit, typically used for Boolean operations.
 * - BOOLEAN: Represents a logical true or false value.
 * - BYTE: Represents an 8-bit value.
 * - WORD: Represents a 16-bit value.
 * - DWORD: Represents a 32-bit value.
 * - INT: Represents a 16-bit signed integer.
 * - DINT: Represents a 32-bit signed integer.
 * - REAL: Represents a 32-bit floating-point number.
 * 
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public enum PlcDataType {
    BIT, BOOLEAN, BYTE, WORD, DWORD, INT, DINT, REAL;
}

