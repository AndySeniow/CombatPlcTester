// Package
package combat_plc_tester.model.IO;

// Imports
import java.io.Serializable;

/**
 * Class: Input
 *
 * Purpose: Serves as the abstract base class for all types of PLC inputs in the 
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
 *   and values for different input types.
 * - This class implements the Serializable interface to enable the
 * storage of fields and objects as a bytestream.
 *
 * @param <T> The type of value associated with the input.
 * 
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public abstract class Input<T> implements Serializable{

    private static final long serialVersionUID = 200L;
    
    private String name;                    // Input name.
    private String inputID;                 // Input unique ID.
    private static int number = 0;          // ID counter.
    private short startaddress;             // Input start address.
    private byte bitaddress;                // Input bit address.

    public Input() {
        this.inputID = "I" + number;
        number += 1;
    }

    public static int getObjectCount(){
        return number;
    }
    
    public static void setObjectcount(int objectcount){
        number = objectcount;
    }
    
    /**
     * Copy Constructor: Input
     *
     * Creates a new Input object by copying the properties of an
     * existing Input instance. 
     *
     * @param copy The Input object to copy. The following properties are
     * duplicated: - inputID: The unique identifier of the input. - name: The
     * name of the input. - startaddress: The start address of the input in the
     * data block. - bitaddress: The bit address of the input in the data block.
     */
    public Input(Input copy) {
        this.inputID = copy.getInputID();
        this.name = copy.getName();
        this.startaddress = copy.getStartAddress();
        this.bitaddress = copy.getBitAddress();
    }

    public String getInputID() {
        return this.inputID;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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

    public abstract T getValue();

    public abstract void setValue(T value);

    public abstract T getInitialValue();

    public abstract void setInitialValue(T value);
}
