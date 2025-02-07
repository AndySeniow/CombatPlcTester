// Package
package combat_plc_tester.model.IO;

// Imports
import java.io.Serializable;

/**
 * Class: Output
 *
 * Purpose: Serves as the abstract base class for all types of PLC outputs in the 
 * Abstract Factory Pattern. 
 *
 * Design Pattern:
 * - Abstract Factory: Acts as the abstract product in the Abstract Factory Pattern, 
 *   enabling the creation of various concrete input types by specific factories.
 *
 * Notes:
 * - Implements the Serializable interface to allow the input objects to be serialized 
 *   for storage or transmission.
 * - This class uses a generic type parameter `<T>` to support type-specific behaviors 
 *   and values for different output types.
 * - This class implements the Serializable interface to enable the
 * storage of fields and objects as a bytestream.
 *
 * @param <T> The type of value associated with the output.
 * 
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public abstract class Output<T> implements Serializable{

    private static final long serialVersionUID = 200L;
    
    private String name;                    // Output name.
    private String outputID;                // Output unique ID.
    private static int number = 0;          // ID counter.
    private short startaddress;             // Output start address.
    private byte bitaddress;                // Output bit address.

    public Output() {
        this.outputID = "O" + number;
        number += 1;
    }

    public static int getObjectCount(){
        return number;
    }
    
    public static void setObjectcount(int objectcount){
        number = objectcount;
    }
    
    /**
     * Copy Constructor: Output
     *
     * Creates a new Output object by copying the properties of an
     * existing Output instance. 
     *
     * @param copy The Output object to copy. The following properties are
     * duplicated: - outputID: The unique identifier of the output. - name: The
     * name of the output. - startaddress: The start address of the output in the
     * data block. - bitaddress: The bit address of the output in the data block.
     */
    public Output(Output copy) {
        this.outputID = copy.getOutputID();
        this.name = copy.getName();
        this.startaddress = copy.getStartAddress();
        this.bitaddress = copy.getBitAddress();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getOutputID() {
        return outputID;
    }

    public short getStartAddress() {
        return startaddress;
    }

    public void setStartAddress(short startaddress) {
        this.startaddress = startaddress;
    }

    public void setBitAddress(byte bitaddress) {
        this.bitaddress = bitaddress;
    }

    public byte getBitAddress() {
        return bitaddress;
    }

    public abstract T getValueBeforeTimeCondition();

    public abstract void setValueBeforeTimeCondition(T value);

    public abstract T getValueAfterTimeCondition();

    public abstract void setValueAfterTimeCondition(T value);

    public abstract T getInitialValue();

    public abstract void setInitialValue(T value);
}
