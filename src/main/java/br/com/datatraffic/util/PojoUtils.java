package br.com.datatraffic.util;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class PojoUtils {

    public static byte[] convertObjectToByteArray(Object object) {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            objectOutputStream.close();
            byteArrayOutputStream.close();
            bytes = byteArrayOutputStream.toByteArray();
        } catch (Exception ignored) {
        }
        return bytes;
    }
}
