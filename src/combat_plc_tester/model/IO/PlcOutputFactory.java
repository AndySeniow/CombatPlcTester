// Package
package combat_plc_tester.model.IO;

// Imports
import static combat_plc_tester.model.IO.PlcDataType.BIT;
import static combat_plc_tester.model.IO.PlcDataType.BYTE;

/**
 * Class: PlcOutputFactory
 *
 * Purpose: Implements the OutputFactory interface as part of the Abstract Factory 
 * Pattern to create concrete instances of PLC-specific outputs. 
 *
 * Design Pattern:
 * - Abstract Factory: Provides an interface for creating families of related 
 *   or dependent objects (outputs) without specifying their concrete classes. 
 * 
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class PlcOutputFactory implements OutputFactory{
    
    private PlcDataType plcdatatype;                // The PLC data type.
    private String name;                            // The name of the output.
    private short startaddress;                     // The start address of the output in the data block. 
    private byte bitaddress;                        // The bit address of the output in the data block.
    private boolean bitinitialvalue;                // The initial value for Siemens Boolean outputs.
    private boolean bitvaluebeforetimecondition;    // The value for Siemens Boolean outputs before the time condition.
    private boolean bitvalueaftertimecondition;     // The value for Siemens Boolean outputs after the time condtition.
    private short byteinitialvalue;                 // The initial value for Siemens BYTE outputs.
    private short bytevaluebeforetimecondition;     // The value for Siemens BYTE outputs before the time condition.
    private short bytevalueaftertimecondition;      // The value for Siemens BYTE outputs after the time condtition.
    private int wordinitialvalue;                   // The initial value for Siemens WORD outputs.
    private int wordvaluebeforetimecondition;       // The value for Siemens WORD outputs before the time condition.
    private int wordvalueaftertimecondition;        // The value for Siemens WORD outputs after the time condtition. 
    private long dwordinitialvalue;                 // The initial value for Siemens DWORD outputs.
    private long dwordvaluebeforetimecondition;     // The value for Siemens DWORD outputs before the time condition.
    private long dwordvalueaftertimecondition;      // The value for Siemens DWORD outputs after the time condtition. 
    private short intinitialvalue;                  // The initial value for Siemens INT outputs.
    private short intvaluebeforetimecondition;      // The value for Siemens INT outputs before the time condition.
    private short intvalueaftertimecondition;       // The value for Siemens INT outputs after the time condtition.
    private int dintinitialvalue;                   // The initial value for Siemens DINT outputs.
    private int dintvaluebeforetimecondition;       // The value for Siemens DINT outputs before the time condition.
    private int dintvalueaftertimecondition;        // The value for Siemens DINT outputs after the time condtition.
    private float realinitialvalue;                 // The initial value for Siemens REAL outputs.
    private float realvaluebeforetimecondition;     // The value for Siemens REAL outputs before the time condition. 
    private float realvalueaftertimecondition;      // The value for Siemens REAL outputs after the time condtition.
   
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
    public void setBitValueBeforeTimeCondition(boolean bitvaluebeforetimecondition) {
        this.bitvaluebeforetimecondition = bitvaluebeforetimecondition;
    }

    @Override
    public void setBitValueAfterTimeCondition(boolean bitvalueaftertimecondition) {
        this.bitvalueaftertimecondition = bitvalueaftertimecondition;
    }
    
    @Override
    public void setByteInitialValue(short byteinitialvalue) {
        this.byteinitialvalue = byteinitialvalue;
    }

    @Override
    public void setByteValueBeforeTimeCondition(short bytevaluebeforetimecondition) {
        this.bytevaluebeforetimecondition = bytevaluebeforetimecondition;
    }
    
    @Override
    public void setByteValueAfterTimeCondition(short bytevalueaftertimecondition) {
        this.bytevalueaftertimecondition = bytevalueaftertimecondition;
    }
    
    @Override
    public void setWordInitialValue(int wordinitialvalue) {
        this.wordinitialvalue = wordinitialvalue;
    }

    @Override
    public void setWordValueBeforeTimeCondition(int wordvaluebeforetimecondition) {
        this.wordvaluebeforetimecondition = wordvaluebeforetimecondition;
    }
    
    @Override
    public void setWordValueAfterTimeCondition(int wordvalueaftertimecondition) {
        this.wordvalueaftertimecondition = wordvalueaftertimecondition;
    }
    
    @Override
    public void setDWordInitialValue(long dwordinitialvalue) {
        this.dwordinitialvalue = dwordinitialvalue;
    }

    @Override
    public void setDWordValueBeforeTimeCondition(long dwordvaluebeforetimecondition) {
        this.dwordvaluebeforetimecondition = dwordvaluebeforetimecondition;
    }
    
    @Override
    public void setDWordValueAfterTimeCondition(long dwordvalueaftertimecondition) {
        this.dwordvalueaftertimecondition = dwordvalueaftertimecondition;
    }
    
    @Override
    public void setIntInitialValue(short intinitialvalue) {
        this.intinitialvalue = intinitialvalue;
    }

    @Override
    public void setIntValueBeforeTimeCondition(short intvaluebeforetimecondition) {
        this.intvaluebeforetimecondition = intvaluebeforetimecondition;
    }
    
    @Override
    public void setIntValueAfterTimeCondition(short intvalueaftertimecondition) {
        this.intvalueaftertimecondition = intvalueaftertimecondition;
    }
   
    @Override
    public void setDIntInitialValue(int dintinitialvalue) {
        this.dintinitialvalue = dintinitialvalue;
    }

    @Override
    public void setDIntValueBeforeTimeCondition(int dintvaluebeforetimecondition) {
        this.dintvaluebeforetimecondition = dintvaluebeforetimecondition;
    }
    
    @Override
    public void setDIntValueAfterTimeCondition(int dintvalueaftertimecondition) {
        this.dintvalueaftertimecondition = dintvalueaftertimecondition;
    }
    
    @Override
    public void setRealInitialValue(float realinitialvalue) {
        this.realinitialvalue = realinitialvalue;
    }

    @Override
    public void setRealValueBeforeTimeCondition(float realvaluebeforetimecondition) {
        this.realvaluebeforetimecondition = realvaluebeforetimecondition;
    }
    
    @Override
    public void setRealValueAfterTimeCondition(float realvalueaftertimecondition) {
        this.realvalueaftertimecondition = realvalueaftertimecondition;
    }
    
    @Override
    public Output getOutput()
    {
        return createPlcOutput();
    }
    
    /**
     * Creates an Output object based on the specified Siemens PLC data type.
     *
     * Generates a specific type of Output object (OutputBit, OutputByte, OutputWord, OutputDWord, OutputInt, OutputDInt, OutputReal) 
     * depending on the value of the `plcdatatype` field. 
     *
     * @return An instance of the corresponding Output subtype based on the
     * `plcdatatype`, or null if the data type is not supported.
     *
     * Notes: - Only the BIT data type uses the `bitaddress` property for
     * initialization. - For all other data types, the `bitaddress` is set to 0
     * by default. - The initial and current values are assigned based on the
     * corresponding fields for each data type. - If the `plcdatatype` is not
     * one of the supported types, the method returns null.
     */
    private Output createPlcOutput()
    {
        switch (plcdatatype) {
            case BIT:
                OutputBit outputbit = new OutputBit();
                outputbit.setName(this.name);
                outputbit.setStartAddress(this.startaddress);
                outputbit.setBitAddress(this.bitaddress);
                outputbit.setInitialValue(this.bitinitialvalue);
                outputbit.setValueBeforeTimeCondition(this.bitvaluebeforetimecondition);
                outputbit.setValueAfterTimeCondition(this.bitvalueaftertimecondition);
                return outputbit;
            case BYTE: 
                OutputByte outputbyte = new OutputByte();
                outputbyte.setName(this.name);
                outputbyte.setStartAddress(this.startaddress);
                outputbyte.setBitAddress((byte)0);
                outputbyte.setInitialValue(this.byteinitialvalue);
                outputbyte.setValueBeforeTimeCondition(this.bytevaluebeforetimecondition);
                outputbyte.setValueAfterTimeCondition(this.bytevalueaftertimecondition);
                return outputbyte;
            case WORD: 
                OutputWord outputword= new OutputWord();
                outputword.setName(this.name);
                outputword.setStartAddress(this.startaddress);
                outputword.setBitAddress((byte)0);
                outputword.setInitialValue(this.wordinitialvalue);
                outputword.setValueBeforeTimeCondition(this.wordvaluebeforetimecondition);
                outputword.setValueAfterTimeCondition(this.wordvalueaftertimecondition);
                return outputword;
            case DWORD: 
                OutputDWord outputdword= new OutputDWord();
                outputdword.setName(this.name);
                outputdword.setStartAddress(this.startaddress);
                outputdword.setBitAddress((byte)0);
                outputdword.setInitialValue(this.dwordinitialvalue);
                outputdword.setValueBeforeTimeCondition(this.dwordvaluebeforetimecondition);
                outputdword.setValueAfterTimeCondition(this.dwordvalueaftertimecondition);
                return outputdword;
            case INT: 
                OutputInt outputint = new OutputInt();
                outputint.setName(this.name);
                outputint.setStartAddress(this.startaddress);
                outputint.setBitAddress((byte)0);
                outputint.setInitialValue(this.intinitialvalue);
                outputint.setValueBeforeTimeCondition(this.intvaluebeforetimecondition);
                outputint.setValueAfterTimeCondition(this.intvalueaftertimecondition);
                return outputint;
            case DINT: 
                OutputDInt outputdint = new OutputDInt();
                outputdint.setName(this.name);
                outputdint.setStartAddress(this.startaddress);
                outputdint.setBitAddress((byte)0);
                outputdint.setInitialValue(this.dintinitialvalue);
                outputdint.setValueBeforeTimeCondition(this.dintvaluebeforetimecondition);
                outputdint.setValueAfterTimeCondition(this.dintvalueaftertimecondition);
                return outputdint;
            case REAL: 
                OutputReal outputreal = new OutputReal();
                outputreal.setName(this.name);
                outputreal.setStartAddress(this.startaddress);
                outputreal.setBitAddress((byte)0);
                outputreal.setInitialValue(this.realinitialvalue);
                outputreal.setValueBeforeTimeCondition(this.realvaluebeforetimecondition);
                outputreal.setValueAfterTimeCondition(this.realvalueaftertimecondition);
                return outputreal;    
            default:
                return null;
        }
    }
}
