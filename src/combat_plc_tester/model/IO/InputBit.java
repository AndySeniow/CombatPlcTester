// Package
package combat_plc_tester.model.IO;

// Imports
import java.io.Serializable;

/**
 * Class: InputBit
 *
 * Purpose: Represents a concrete implementation of an input specifically 
 * designed for handling Siemens Boolean inputs in a PLC system. 
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
public class InputBit extends Input<Boolean> implements Serializable {

    private static final long serialVersionUID = 201L;
   
    private boolean initialvalue;           // InputBit initial value.
    private boolean value;                  // InputBit value.

    public InputBit() {
        super();
    }

    /**
     * Copy Constructor: InputBit
     *
     * Creates a new InputBit object by copying the properties of an
     * existing Input object. This constructor initializes the base properties
     * from the `Input` class and assigns the initial value specific to the
     * `InputBit` implementation.
     *
     * @param copy The Input object to copy. The following properties are
     * inherited and initialized from the parent class: - inputID: The unique
     * identifier of the input. - name: The name of the input. - startaddress:
     * The start address of the input in the data block. - bitaddress: The bit
     * address of the input in the data block. Additionally, the `initialvalue`
     * is cast to a boolean to match the `InputBit` type.
     */
    public InputBit(Input copy) {
        super(copy);
        this.initialvalue = (boolean)copy.getInitialValue();
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void setValue(Boolean value) {
        this.value = value;
    }

    @Override
    public Boolean getInitialValue() {
        return initialvalue;
    }

    @Override
    public void setInitialValue(Boolean initialvalue) {
        this.initialvalue = initialvalue;
    }

}
