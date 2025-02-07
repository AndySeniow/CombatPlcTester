// Package
package combat_plc_tester.model.IO;

// Imports
import static combat_plc_tester.model.IO.PlcDataType.*;

/**
 * Class: PlcInputFactory
 *
 * Purpose: Implements the InputFactory interface as part of the Abstract Factory 
 * Pattern to create concrete instances of PLC-specific inputs. 
 *
 * Design Pattern:
 * - Abstract Factory: Provides an interface for creating families of related 
 *   or dependent objects (inputs) without specifying their concrete classes. 
 * 
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class PlcInputFactory implements InputFactory{
    
    private PlcDataType plcdatatype;        // The PLC data type.
    private String name;                    // The name of the input.
    private short startaddress;             // The start address of the input in the data block.
    private byte bitaddress;                // The bit address of the input in the data block.
    private boolean bitinitialvalue;        // The initial value for Siemens Boolean inputs.
    private boolean bitvalue;               // The current value for Siemens Boolean inputs.
    private short byteinitialvalue;         // The initial value for Siemens Byte inputs.
    private short bytevalue;                // The current value for Siemens Byte inputs.
    private int wordinitialvalue;           // The initial value for Siemens Word inputs.
    private int wordvalue;                  // The current value for Siemens Word inputs.
    private long dwordinitialvalue;         // The initial value for Siemens DWord inputs.
    private long dwordvalue;                // The current value for Siemens DWord inputs.
    private short intinitialvalue;          // The initial value for Siemens Int inputs.
    private short intvalue;                 // The current value for Siemens Int inputs.
    private int dintinitialvalue;           // The initial value for Siemens DInt inputs.
    private int dintvalue;                  // The current value for Siemens DInt inputs.
    private float realinitialvalue;         // The initial value for Siemens Real inputs.
    private float realvalue;                // The current value for Siemens Real inputs.


    @Override
    public void setPlcDataType(PlcDataType plcdatatype) {
        this.plcdatatype = plcdatatype;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setStartAddress(short startaddress) {
        this.startaddress = startaddress;
    }

    @Override
    public void setBitAddress(byte bitaddress) {
        this.bitaddress = bitaddress;
    }

    @Override
    public void setBitInitialValue(boolean bitinitialvalue) {
        this.bitinitialvalue = bitinitialvalue;
    }

    @Override
    public void setBitValue(boolean bitvalue) {
        this.bitvalue = bitvalue;
    }

    @Override
    public void setByteInitialValue(short byteinitialvalue) {
        this.byteinitialvalue = byteinitialvalue;
    }

    @Override
    public void setByteValue(short bytevalue) {
        this.bytevalue = bytevalue;
    }
   
    @Override
    public void setWordInitialValue(int wordinitialvalue) {
        this.wordinitialvalue = wordinitialvalue;
    }

    @Override
    public void setWordValue(int wordvalue) {
        this.wordvalue = wordvalue;
    }
    
    @Override
    public void setDWordInitialValue(long dwordinitialvalue) {
        this.dwordinitialvalue = dwordinitialvalue;
    }

    @Override
    public void setDWordValue(long dwordvalue) {
        this.dwordvalue = dwordvalue;
    }
     
    @Override
    public void setIntInitialValue(short shortinitialvalue) {
        this.intinitialvalue = shortinitialvalue;
    }

    @Override
    public void setIntValue(short shortvalue) {
        this.intvalue = shortvalue;
    }
   
    @Override
    public void setDIntInitialValue(int intinitialvalue) {
        this.dintinitialvalue = intinitialvalue;
    }

    @Override
    public void setDIntValue(int intvalue) {
        this.dintvalue = intvalue;
    }
    
    @Override
    public void setRealInitialValue(float realinitialvalue) {
        this.realinitialvalue = realinitialvalue;
    }

    @Override
    public void setRealValue(float realvalue) {
        this.realvalue = realvalue;
    }
    
    @Override
    public Input getInput()
    {
        return createPlcInput();
    }
    
    /**
     * Creates an Input object based on the specified Siemens PLC data type.
     *
     * Generates a specific type of Input object (InputBit, InputByte, InputWord, InputDWord, InputInt, InputDInt, InputReal) 
     * depending on the value of the `plcdatatype` field. 
     *
     * @return An instance of the corresponding Input subtype based on the
     * `plcdatatype`, or null if the data type is not supported.
     *
     * Notes: - Only the BIT data type uses the `bitaddress` property for
     * initialization. - For all other data types, the `bitaddress` is set to 0
     * by default. - The initial and current values are assigned based on the
     * corresponding fields for each data type. - If the `plcdatatype` is not
     * one of the supported types, the method returns null.
     */
    private Input createPlcInput() {
        switch (plcdatatype) {
            case BIT:
                InputBit inputbit = new InputBit();
                inputbit.setName(this.name);
                inputbit.setStartAddress(this.startaddress);
                inputbit.setBitAddress(this.bitaddress);
                inputbit.setInitialValue(this.bitinitialvalue);
                inputbit.setValue(this.bitvalue);
                return inputbit;
            case BYTE: 
                InputByte inputbyte = new InputByte();
                inputbyte.setName(this.name);
                inputbyte.setStartAddress(this.startaddress);
                inputbyte.setBitAddress((byte)0);
                inputbyte.setInitialValue(this.byteinitialvalue);
                inputbyte.setValue(this.bytevalue);
                return inputbyte;
            case WORD:
                InputWord inputword = new InputWord();
                inputword.setName(this.name);
                inputword.setStartAddress(this.startaddress);
                inputword.setBitAddress((byte)0);
                inputword.setInitialValue(this.wordinitialvalue);
                inputword.setValue(this.wordvalue);
                return inputword;
            case DWORD:
                InputDWord inputdword = new InputDWord();
                inputdword.setName(this.name);
                inputdword.setStartAddress(this.startaddress);
                inputdword.setBitAddress((byte)0);
                inputdword.setInitialValue(this.dwordinitialvalue);
                inputdword.setValue(this.dwordvalue);
                return inputdword;
            case INT: 
                InputInt inputint = new InputInt();
                inputint.setName(this.name);
                inputint.setStartAddress(this.startaddress);
                inputint.setBitAddress((byte)0);
                inputint.setInitialValue(this.intinitialvalue);
                inputint.setValue(this.intvalue);
                return inputint;
            case DINT: 
                InputDInt inputdint = new InputDInt();
                inputdint.setName(this.name);
                inputdint.setStartAddress(this.startaddress);
                inputdint.setBitAddress((byte)0);
                inputdint.setInitialValue(this.dintinitialvalue);
                inputdint.setValue(this.dintvalue);
                return inputdint;
            case REAL: 
                InputReal inputdreal = new InputReal();
                inputdreal.setName(this.name);
                inputdreal.setStartAddress(this.startaddress);
                inputdreal.setBitAddress((byte)0);
                inputdreal.setInitialValue(this.realinitialvalue);
                inputdreal.setValue(this.realvalue);
                return inputdreal;
            default:
                return null;
        }
    }
}
