// Package
package combat_plc_tester.model.IO;

// Imports
import java.io.Serializable;

/**
 * Class: InputReal
 *
 * Purpose: Represents a concrete implementation of an input specifically 
 * designed for handling Siemens REAL inputs in a PLC system. 
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
public class InputReal extends Input<Float> implements Serializable{
    
    private static final long serialVersionUID = 207L;
    
    private float initialvalue;                 // InputReal initial value.
    private float value;                        // InputReal value.

    public InputReal() {
        super();
    }

    /**
     * Copy Constructor: InputReal
     *
     * Creates a new InputReal object by copying the properties of an
     * existing Input object. This constructor initializes the base properties
     * from the `Input` class and assigns the initial value specific to the
     * `InputByte` implementation.
     *
     * @param copy The Input object to copy. The following properties are
     * inherited and initialized from the parent class: - inputID: The unique
     * identifier of the input. - name: The name of the input. - startaddress:
     * The start address of the input in the data block. - bitaddress: The bit
     * address of the input in the data block. Additionally, the `initialvalue`
     * is cast to a float to match the `InputReal` type.
     */
    public InputReal(Input copy) {
        super(copy);
        this.initialvalue = (float)copy.getInitialValue();
    }

    @Override
    public Float getValue() {
        return value;
    }

    @Override
    public void setValue(Float value) {
        this.value = value;
    }

    @Override
    public Float getInitialValue() {
        return initialvalue;
    }

    @Override
    public void setInitialValue(Float initialvalue) {
        this.initialvalue = initialvalue;
    }
}

