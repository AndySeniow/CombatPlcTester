// Package
package combat_plc_tester.model.IO;

// Imports
import java.io.Serializable;

/**
 * Class: OutputBit
 *
 * Purpose: Represents a concrete implementation of an output specifically 
 * designed for handling Siemens Boolean outputs in a PLC system. 
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
public class OutputBit extends Output<Boolean> implements Serializable{

    private static final long serialVersionUID = 201L;
    
    private boolean initialvalue;                   // OutputBit initial value.
    private boolean valuebeforetimecondition;       // OutputBit value before the time condition.
    private boolean valueaftertimecondition;        // OutputBit value after the time condition.

    public OutputBit() {
        super();
    }

    /**
     * Copy Constructor: OutputBit
     *
     * Creates a new OutputBit object by copying the properties of an
     * existing Output object. This constructor initializes the base properties
     * from the `Output` class and assigns the initial value specific to the
     * `OutputBit` implementation.
     *
     * @param copy The Output object to copy. The following properties are
     * inherited and initialized from the parent class: - outputID: The unique
     * identifier of the output. - name: The name of the output. - startaddress:
     * The start address of the output in the data block. - bitaddress: The bit
     * address of the output in the data block. Additionally, the `initialvalue`
     * is cast to a boolean to match the `OutputBit` type.
     */
    public OutputBit(Output copy) {
        super(copy);
        this.initialvalue = (boolean)copy.getInitialValue();
    }

    @Override
    public Boolean getValueBeforeTimeCondition() {
        return valuebeforetimecondition;
    }

    @Override
    public void setValueBeforeTimeCondition(Boolean valuebeforetimecondition) {
        this.valuebeforetimecondition = valuebeforetimecondition;
    }

    @Override
    public Boolean getValueAfterTimeCondition() {
        return valueaftertimecondition;
    }

    @Override
    public void setValueAfterTimeCondition(Boolean valueaftertimecondition) {
        this.valueaftertimecondition = valueaftertimecondition;
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
