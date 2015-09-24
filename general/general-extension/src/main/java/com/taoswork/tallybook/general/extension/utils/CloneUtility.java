package com.taoswork.tallybook.general.extension.utils;

import java.io.*;
import java.lang.reflect.Method;

/**
 * Created by Gao Yuan on 2015/6/24.
 */
public class CloneUtility {
    public static <T> T makeClone(T source) {
        try {
            if(source == null){
                return null;
            } else if (source instanceof Cloneable) {
                Method cloneMethod = source.getClass().getDeclaredMethod("clone");
                return (T) cloneMethod.invoke(source);
            } else if(source instanceof Serializable){
                return (T)makeCloneForSerializable((Serializable)source);
            }
        } catch (Exception exp) {
            throw new RuntimeException(exp);
        }

        throw new RuntimeException("Clone not supported: " + source.getClass() + ". (extends Cloneable or Serializable) required.");
    }
    public static <T extends Serializable> T makeCloneForSerializable(T source) {
        try {
            if(source instanceof Serializable){
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(source);

                ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Object deepCopy = ois.readObject();

                return (T) deepCopy;

            }
        } catch (Exception exp) {
            throw new RuntimeException(exp);
        }

        throw new RuntimeException("Clone not supported: " + source.getClass() + ". (extends Cloneable or Serializable) required.");
    }

}
