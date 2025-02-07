// Package
package combat_plc_tester.model.IO;

/**
 * Interface: InputFactory
 *
 * Purpose: Defines the contract for creating input objects as part of the 
 * Abstract Factory Pattern. This interface ensures that all concrete factories 
 * implementing it provide consistent methods for instantiating inputs.
 *
 * Design Pattern:
 * - Abstract Factory: Serves as the abstract factory interface for creating 
 *   families of related input objects. 
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
 * - The initial value is the value of the input when the model does not 
 *   assign any value to the input through a transition.
 * 
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public interface InputFactory {
    
   public void setPlcDataType(PlcDataType plcdatatype);

   public void setName(String name); 

   public void setStartAddress(short startaddress); 

   public void setBitAddress(byte bitaddress); 

   public void setBitInitialValue(boolean bitinitialvalue); 

   public void setBitValue(boolean bitvalue); 

   public void setByteInitialValue(short byteinitialvalue); 

   public void setByteValue(short bytevalue); 
   
   public void setWordValue(int wordinitialvalue); 
    
   public void setWordInitialValue(int wordinitialvalue); 
   
   public void setDWordValue(long dwordinitialvalue); 
    
   public void setDWordInitialValue(long dwordinitialvalue); 
   
   public void setIntInitialValue(short shortinitialvalue); 
   
   public void setIntValue(short shortvalue);
   
   public void setDIntInitialValue(int dintinitialvalue); 
   
   public void setDIntValue(int dintvalue);
   
   public void setRealInitialValue(float realinitialvalue); 
   
   public void setRealValue(float realvalue);
   
   public Input getInput();    
}
