package com.afterqcd.study.protobuf;

import com.google.protobuf.GeneratedMessageV3;

import java.util.Arrays;

/**
 * Created by afterqcd on 16/9/26.
 */
public class Demo {
    public static void main(String[] args) {
        GeneratedMessageV3 addressBook = AddressBookOuterClass.AddressBook.newBuilder()
                .addPerson(PersonOuterClass.Person.newBuilder().setEmail("abc@qq.com"))
                .build();
        System.out.println(Arrays.toString(addressBook.toByteArray()));
    }
}
