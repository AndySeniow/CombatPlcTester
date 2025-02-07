// Package
package combat_plc_tester.model.IO;

// Imports
import java.io.Serializable;

/**
 * Class: OutputDInt
 *
 * Purpose: Represents a concrete implementation of an output specifically 
 * designed for handling Siemens DINT outputs in a PLC system. 
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
public class OutputDInt extends Output<Integer> implements Serializable{

    private static final long serialVersionUID = 206L;
    
    private int initialvalue;                       // OutputDInt initial value.
    private int valuebeforetimecondition;           // OutputDInt value before the time condition.
    private int valueaftertimecondition;            // OutputDInt value after the time condition.

    public OutputDInt() {
        super();
    }

    /**
     * Copy Constructor: OutputDInt
     *
     * Creates a new OutputDInt object by copying the properties of an
     * existing Output object. This constructor initializes the base properties
     * from the `Output` class and assigns the initial value specific to the
     * `OutputDInt` implementation.
     *
     * @param copy The Output object to copy. The following properties are
     * inherited and initialized from the parent class: - outputID: The unique
     * identifier of the output. - name: The name of the output. - startaddress:
     * The start address of the output in the data block. - bitaddress: The bit
     * address of the output in the data block. Additionally, the `initialvalue`
     * is cast to a int to match the `OutputDInt` type.
     */
    public OutputDInt(Output copy) {
        super(copy);
        this.initialvalue = (int)copy.getInitialValue();
    }
    
    @Override
    public Integer getValueBeforeTimeCondition() {
        return valuebeforetimecondition;
    }
    
    @Override
    public void setValueBeforeTimeCondition(Integer valuebeforetimecondition) {
        this.valuebeforetimecondition = valuebeforetimecondition;
    }
    
    @Override
    public Integer getValueAfterTimeCondition() {
        return valueaftertimecondition;
    }
    
    @Override
    public void setValueAfterTimeCondition(Integer valueaftertimecondition) {
        this.valueaftertimecondition = valueaftertimecondition;
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
