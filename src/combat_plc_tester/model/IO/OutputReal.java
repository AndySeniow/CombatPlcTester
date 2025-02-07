// Package
package combat_plc_tester.model.IO;

// Imports
import java.io.Serializable;

/**
 * Class: OutputReal
 *
 * Purpose: Represents a concrete implementation of an output specifically 
 * designed for handling Siemens REAL outputs in a PLC system. 
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
public class OutputReal extends Output<Float> implements Serializable{

    private static final long serialVersionUID = 207L;
    
    private float initialvalue;                     // OutputReal initial value.
    private float valuebeforetimecondition;         // OutputReal value before the time condition.
    private float valueaftertimecondition;          // OutputReal value after the time condition.

    public OutputReal() {
        super();
    }

    /**
     * Copy Constructor: OutputReal
     *
     * Creates a new OutputReal object by copying the properties of an
     * existing Output object. This constructor initializes the base properties
     * from the `Output` class and assigns the initial value specific to the
     * `OutputReal` implementation.
     *
     * @param copy The Output object to copy. The following properties are
     * inherited and initialized from the parent class: - outputID: The unique
     * identifier of the output. - name: The name of the output. - startaddress:
     * The start address of the output in the data block. - bitaddress: The bit
     * address of the output in the data block. Additionally, the `initialvalue`
     * is cast to a float to match the `OutputReal` type.
     */
    public OutputReal(Output copy) {
        super(copy);
        this.initialvalue = (float)copy.getInitialValue();
    }
    
    @Override
    public Float getValueBeforeTimeCondition() {
        return valuebeforetimecondition;
    }
    
    @Override
    public void setValueBeforeTimeCondition(Float valuebeforetimecondition) {
        this.valuebeforetimecondition = valuebeforetimecondition;
    }
    
    @Override
    public Float getValueAfterTimeCondition() {
        return valueaftertimecondition;
    }
    
    @Override
    public void setValueAfterTimeCondition(Float valueaftertimecondition) {
        this.valueaftertimecondition = valueaftertimecondition;
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
