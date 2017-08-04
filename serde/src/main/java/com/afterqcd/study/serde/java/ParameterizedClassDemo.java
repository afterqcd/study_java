package com.afterqcd.study.serde.java;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by afterqcd on 2016/12/20.
 */
public class ParameterizedClassDemo {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Data<String> stringData = new Data<String>();
        stringData.setData("abc");

        Data<Integer> intData = new Data<Integer>();
        intData.setData(123);

        ByteArrayOutputStream bytesOs = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bytesOs);
        oos.writeObject(stringData);
        oos.writeObject(intData);
        oos.flush();

        byte[] bytes = bytesOs.toByteArray();

        ObjectInputStream bis = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Object obj1 = bis.readObject();
        Data<String> stringData1 = (Data<String>) obj1;
        Object obj2 = bis.readObject();
        Data<Integer> intData1 = (Data<Integer>) obj2;

        Objects.equals(stringData1.getData(), "abc");
        Objects.equals(intData1.getData(), 123);
    }
}

class Data<T> implements Serializable {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
