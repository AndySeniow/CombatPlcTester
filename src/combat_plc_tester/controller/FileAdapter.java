// Package
package combat_plc_tester.controller;

/**
 * Interface: FileAdapter
 * 
 * Provides an abstraction for saving and loading data of type `T` to and from files. 
 *
 * Design Patterns:
 * - **Adapter Pattern:**
 *   - Bridges the gap between the application's data model and specific file formats 
 *     (e.g., JSON, XML, binary), allowing seamless integration of different storage mechanisms.
 *
 * @param <T> The type of data to be saved and loaded (e.g., objects, lists, etc.).
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public interface FileAdapter<T> {  
    
    /**
    * Saves the provided data to a specified file path. 
    *
    * Parameters:
    * @param data The data of type `T` to be saved.
    * @param filePath The file path where the data will be saved.
    *
    * @throws Exception If the save operation encounters an error.
    */
    public void save(T data, String filePath) throws Exception;
    
    /**
    * Loads and returns data of type `T` from the specified file path. 
    *
    * @param filePath The file path from which the data will be loaded.
    * @return The deserialized data of type `T` loaded from the file.
    * @throws Exception If the load operation encounters an error.
    */
    public T load(String filePath) throws Exception;
}

