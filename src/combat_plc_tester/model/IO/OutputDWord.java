// Package
package combat_plc_tester.model.IO;

// Imports
import java.io.Serializable;

/**
 * Class: OutputDWord
 *
 * Purpose: Represents a concrete implementation of an output specifically 
 * designed for handling Siemens DWORD outputs in a PLC system. 
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
public class OutputDWord extends Output<Long> implements Serializable{

    private static final long serialVersionUID = 204L;
    
    private long initialvalue;                    // OutputDWord initial value.
    private long valuebeforetimecondition;        // OutputDWord value before the time condition.
    private long valueaftertimecondition;         // OutputDWord value after the time condition.

    public OutputDWord() {
        super();
    }

    /**
     * Copy Constructor: OutputDWord
     *
     * Creates a new OutputDWord object by copying the properties of an
     * existing Output object. This constructor initializes the base properties
     * from the `Output` class and assigns the initial value specific to the
     * `OutputDWord` implementation.
     *
     * @param copy The Output object to copy. The following properties are
     * inherited and initialized from the parent class: - outputID: The unique
     * identifier of the output. - name: The name of the output. - startaddress:
     * The start address of the output in the data block. - bitaddress: The bit
     * address of the output in the data block. Additionally, the `initialvalue`
     * is cast to a long to match the `OutputDWord` type.
     */
    public OutputDWord(Output copy) {
        super(copy);
        this.initialvalue = (long)copy.getInitialValue();
    }
    
    @Override
    public Long getValueBeforeTimeCondition() {
        return valuebeforetimecondition;
    }
    
    @Override
    public void setValueBeforeTimeCondition(Long valuebeforetimecondition) {
        this.valuebeforetimecondition = valuebeforetimecondition;
    }
    
    @Override
    public Long getValueAfterTimeCondition() {
        return valueaftertimecondition;
    }
    
    @Override
    public void setValueAfterTimeCondition(Long valueaftertimecondition) {
        this.valueaftertimecondition = valueaftertimecondition;
    }
    
    @Override
    public Long getInitialValue() {
        return initialvalue;
    }
    
    @Override
    public void setInitialValue(Long initialvalue) {
        this.initialvalue = initialvalue;
    }
}
