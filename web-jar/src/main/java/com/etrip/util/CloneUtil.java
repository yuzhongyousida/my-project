package com.etrip.util;

import java.io.*;

public class CloneUtil {
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(T obj) {

        T clonedObj = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.close();

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            clonedObj = (T) ois.readObject();
            ois.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return clonedObj;
    }
}
