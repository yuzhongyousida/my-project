package com.myself.util;

import java.io.*;

/**
 * 深度clone工具类，被clone的类必须是序列化了，否则会报错
 * 利用对象的序列化、反序列化达到深度clone
 */
public class CloneUtil {

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
