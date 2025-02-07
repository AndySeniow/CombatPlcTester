// Package
package combat_plc_tester.model.IO;

// Imports
import java.io.Serializable;

/**
 * Class: InputDInt
 *
 * Purpose: Represents a concrete implementation of an input specifically 
 * designed for handling Siemens DINT inputs in a PLC system. 
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
public class InputDInt extends Input<Integer> implements Serializable{
    
    private static final long serialVersionUID = 206L;
    
    private int initialvalue;               // InputDInt initial value.
    private int value;                      // InputDInt value.

    public InputDInt() {
        super();
    }

    /**
     * Copy Constructor: InputDInt
     *
     * Creates a new InputDInt object by copying the properties of an
     * existing Input object. This constructor initializes the base properties
     * from the `Input` class and assigns the initial value specific to the
     * `InputDInt` implementation.
     *
     * @param copy The Input object to copy. The following properties are
     * inherited and initialized from the parent class: - inputID: The unique
     * identifier of the input. - name: The name of the input. - startaddress:
     * The start address of the input in the data block. - bitaddress: The bit
     * address of the input in the data block. Additionally, the `initialvalue`
     * is cast to a int to match the `InputDInt` type.
     */
    public InputDInt(Input copy) {
        super(copy);
        this.initialvalue = (int)copy.getInitialValue();
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getInitialValue() {
        return initialvalue;
    }

    @Override
    public void setInitialValue(Integer initialvalue) {
        this.initialvalue = initialvalue;
    }
}

