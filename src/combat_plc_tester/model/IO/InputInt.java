// Package
package combat_plc_tester.model.IO;

// Imports
import java.io.Serializable;

/**
 * Class: InputInt
 *
 * Purpose: Represents a concrete implementation of an input specifically 
 * designed for handling Siemens INT inputs in a PLC system. 
 *
 * Design Pattern:
 * - Abstract Factory: Serves as a concrete product in the Abstract Factory 
 *   Pattern.
 *
 * Notes:
 * - This class implements the Serializable interface to enable the
 * storage of fields and objects as a bytestream.
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class InputInt extends Input<Short> implements Serializable{
    
    private static final long serialVersionUID = 205L;
    
    private short initialvalue;             // InputInt initial value.
    private short value;                    // InputInt value.

    public InputInt() {
        super();
    }

    /**
     * Copy Constructor: InputInt
     *
     * Creates a new InputInt object by copying the properties of an
     * existing Input object. This constructor initializes the base properties
     * from the `Input` class and assigns the initial value specific to the
     * `InputByte` implementation.
     *
     * @param copy The Input object to copy. The following properties are
     * inherited and initialized from the parent class: - inputID: The unique
     * identifier of the input. - name: The name of the input. - startaddress:
     * The start address of the input in the data block. - bitaddress: The bit
     * address of the input in the data block. Additionally, the `initialvalue`
     * is cast to a short to match the `InputInt` type.
     */
    public InputInt(Input copy) {
        super(copy);
        this.initialvalue = (short)copy.getInitialValue();
    }

    @Override
    public Short getValue() {
        return value;
    }

    @Override
    public void setValue(Short value) {
        this.value = value;
    }

    @Override
    public Short getInitialValue() {
        return initialvalue;
    }

    @Override
    public void setInitialValue(Short initialvalue) {
        this.initialvalue = initialvalue;
    }
}

