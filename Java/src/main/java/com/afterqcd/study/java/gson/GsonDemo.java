package com.afterqcd.study.java.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.reflect.TypeToken;

import java.util.Arrays;

/**
 * Created by afterqcd on 16/8/18.
 */
public class GsonDemo {
    private static Gson gson = new Gson();

    public static void main(String[] args) {
        readArray();
        readObject();
        readObjectByType();
        dealWithFinalField();
        parseStringToNumber();
        longSerialization();
    }

    private static void readArray() {
        Integer[] elems = gson.fromJson(
                "[1, 2, 3]",
                new TypeToken<Integer[]>() {}.getType()
        );
        System.out.println(Arrays.toString(elems));
    }

    private static void readObject() {
        People people = gson.fromJson("{\"name\": \"zhangsan\"}", People.class);
        System.out.println(people.getName());
    }

    private static void readObjectByType() {
        People people = gson.fromJson("{\"name\": \"zhangsan\"}", new TypeToken<People>() {}.getType());
        System.out.println(people.getName());
    }

    private static void dealWithFinalField() {
        People people = new People();
        people.setName("zhangsan");
        System.out.println(gson.toJson(people));

        People people1 = gson.fromJson("{\"type\":\"People\",\"name\":\"zhangsan\"}", People.class);
        System.out.println(people1.getName());
        System.out.println(people1.type);
    }

    private static void parseStringToNumber() {
        Num2 num2 = new Num2();
        num2.setNum("123456");

        Gson gson = new GsonBuilder().create();

        String str = gson.toJson(num2);
        System.out.println(str);

        Num num = gson.fromJson(str, Num.class);
        System.out.println(num.getNum());
    }

    private static void longSerialization() {
        Num num = new Num();
        num.setNum(123456);

        Gson gson = new GsonBuilder()
                .setLongSerializationPolicy(LongSerializationPolicy.STRING)
                .create();

        String str = gson.toJson(num);
        System.out.println(str);

        Num num2 = gson.fromJson(str, Num.class);
        System.out.println(num2.getNum());
    }
}
