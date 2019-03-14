package com.xm.mvptest.bean;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by boka_lyp on 2015/12/10.
 */
public class BaseBean implements Serializable, Cloneable {

    public BaseBean() {
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            return deepCopy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.clone();
    }

    public Object deepCopy() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();
    }
}
