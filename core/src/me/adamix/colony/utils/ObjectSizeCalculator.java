package me.adamix.colony.utils;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;

public class ObjectSizeCalculator {
	public static int getObjectSize(Serializable obj) {
		try {
			// Create a ByteArrayOutputStream
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			// Create an ObjectOutputStream with the ByteArrayOutputStream
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			// Write the object to the ObjectOutputStream
			objectOutputStream.writeObject(obj);
			objectOutputStream.flush();
			// Close the streams
			objectOutputStream.close();
			// Return the size of the ByteArrayOutputStream
			return byteArrayOutputStream.size();
		} catch (Exception e) {
			e.printStackTrace();
			return -1; // Return -1 if an error occurs
		}
	}

	public static <K extends Serializable, V extends Serializable> int getObjectSize(Map<K, V> map) {
		try {
			// Create a ByteArrayOutputStream
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			// Create an ObjectOutputStream with the ByteArrayOutputStream
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			// Write the map to the ObjectOutputStream
			objectOutputStream.writeObject(map);
			objectOutputStream.flush();
			// Close the streams
			objectOutputStream.close();
			// Return the size of the ByteArrayOutputStream
			return byteArrayOutputStream.size();
		} catch (Exception e) {
			e.printStackTrace();
			return -1; // Return -1 if an error occurs
		}
	}
}