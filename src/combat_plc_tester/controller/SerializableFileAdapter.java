// Package
package combat_plc_tester.controller;

// Imports
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Class: SerializableFileAdapter
 * 
 * Purpose:
 * Implements the `FileAdapter` interface to provide functionality for saving and loading objects 
 * of type `T` that implement the `Serializable` interface.
 *
 * Design Patterns:
 * - **Adapter Pattern:**
 * - Adapts Java's built-in serialization mechanism to conform to the `FileAdapter` interface.
 *
 * @param <T> The type of data to be saved and loaded. Must implement `Serializable`.
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class SerializableFileAdapter<T extends Serializable> implements FileAdapter<T> {
    
    private static final String DEFAULT_EXTENSION = ".cmb";         // The file extension.
    
    @Override
    public void save(T data, String filePath) throws IOException {
        if (!filePath.endsWith(DEFAULT_EXTENSION)) {
            filePath += DEFAULT_EXTENSION;
        }
        try (ObjectOutputStream objectoutputstream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            objectoutputstream.reset();
            objectoutputstream.writeObject(data);
        }
    }

    @Override
    public T load(String filePath) throws IOException, ClassNotFoundException {      
        if (!filePath.endsWith(DEFAULT_EXTENSION)) {
            throw new IOException("Invalid file format. The file must have the extension " + DEFAULT_EXTENSION + ".");
        }
        try (ObjectInputStream objectinputstream = new ObjectInputStream(new FileInputStream(filePath))) {
            return (T) objectinputstream.readObject();
        }
    }
}

