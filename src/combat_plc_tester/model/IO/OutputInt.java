// Package
package combat_plc_tester.model.IO;

// Imports
import java.io.Serializable;

/**
 * Class: OutputInt
 *
 * Purpose: Represents a concrete implementation of an output specifically 
 * designed for handling Siemens INT outputs in a PLC system. 
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
public class OutputInt extends Output<Short> implements Serializable{

    private static final long serialVersionUID = 205L;
    
    private short initialvalue;
    private short valuebeforetimecondition;
    private short valueaftertimecondition;

    public OutputInt() {
        super();
    }

    /**
     * Copy Constructor: OutputInt
     *
     * Creates a new OutputInt object by copying the properties of an
     * existing Output object. This constructor initializes the base properties
     * from the `Output` class and assigns the initial value specific to the
     * `OutputInt` implementation.
     *
     * @param copy The Output object to copy. The following properties are
     * inherited and initialized from the parent class: - outputID: The unique
     * identifier of the output. - name: The name of the output. - startaddress:
     * The start address of the output in the data block. - bitaddress: The bit
     * address of the output in the data block. Additionally, the `initialvalue`
     * is cast to a short to match the `OutputInt` type.
     */
    public OutputInt(Output copy) {
        super(copy);
        this.initialvalue = (short)copy.getInitialValue();
    }
    
    @Override
    public Short getValueBeforeTimeCondition() {
        return valuebeforetimecondition;
    }
    
    @Override
    public void setValueBeforeTimeCondition(Short valuebeforetimecondition) {
        this.valuebeforetimecondition = valuebeforetimecondition;
    }
    
    @Override
    public Short getValueAfterTimeCondition() {
        return valueaftertimecondition;
    }
    
    @Override
    public void setValueAfterTimeCondition(Short valueaftertimecondition) {
        this.valueaftertimecondition = valueaftertimecondition;
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
