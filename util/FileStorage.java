package util;

import java.io.*;

/**
 * Utility class to handle saving and loading objects to/from a file.
 */
public class FileStorage {

    /**
     * Serializes an object to the specified file path.
     */
    public static void saveToFile(Object obj, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(obj);
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }
    }

    /**
     * Deserializes an object from the specified file path.
     */
    public static Object loadFromFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading from file: " + e.getMessage());
            return null;
        }
    }
}
