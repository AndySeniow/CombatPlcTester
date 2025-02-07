// Package
package combat_plc_tester.model.IO;

/**
 * Interface: OutputFactory
 *
 * Purpose: Defines the contract for creating output objects as part of the 
 * Abstract Factory Pattern. This interface ensures that all concrete factories 
 * implementing it provide consistent methods for instantiating outputs.
 *
 * Design Pattern:
 * - Abstract Factory: Serves as the abstract factory interface for creating 
 *   families of related output objects. 
 * 
* Notes:
 * - The following Siemens PLC data types will be supported:
 *   ------------------------------------
 *  | Siemens PLC Data Type | Java Data Type |
 *   ------------------------------------
 *  | Boolean               | byte (as Bit)  |
 *  | Byte                  | short          |
 *  | Word                  | int            |
 *  | DWord                 | long           |
 *  | Int                   | short          |
 *  | Real                  | float          |
 *   ------------------------------------
 * - Only the Siemens Boolean data type has a bit address.
 * - The initial value is the value of the output when the model does not 
 *   assign any value to the output through a state.
 * 
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public interface OutputFactory {
    
   public void setPlcDataType(PlcDataType plcdatatype);

   public void setName(String name); 

   public void setStartAddress(short startaddress); 

   public void setBitAddress(byte bitaddress); 

   public void setBitInitialValue(boolean bitinitialvalue); 

   public void setBitValueBeforeTimeCondition(boolean bitvaluebeforetimecondition);
   
   public void setBitValueAfterTimeCondition(boolean bitvalueaftertimecondition);  

   public void setByteInitialValue(short byteinitialvalue); 

   public void setByteValueBeforeTimeCondition(short bytevalue);
   
   public void setByteValueAfterTimeCondition(short bytevalue);
   
   public void setWordInitialValue(int wordinitialvalue); 

   public void setWordValueBeforeTimeCondition(int wordvalue);
   
   public void setWordValueAfterTimeCondition(int wordvalue);
   
   public void setDWordInitialValue(long wordinitialvalue); 

   public void setDWordValueBeforeTimeCondition(long wordvalue);
   
   public void setDWordValueAfterTimeCondition(long wordvalue);
   
   public void setIntInitialValue(short intinitialvalue); 

   public void setIntValueBeforeTimeCondition(short intvalue);
   
   public void setIntValueAfterTimeCondition(short intvalue);
   
   public void setDIntInitialValue(int dintinitialvalue); 

   public void setDIntValueBeforeTimeCondition(int dintvalue);
   
   public void setDIntValueAfterTimeCondition(int dintvalue);
   
   public void setRealInitialValue(float realtinitialvalue); 

   public void setRealValueBeforeTimeCondition(float realvalue);
   
   public void setRealValueAfterTimeCondition(float realvalue);
   
   public Output getOutput();
     
}