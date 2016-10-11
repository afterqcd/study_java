package com.afterqcd.study.protobuf;

import com.google.protobuf.GeneratedMessageV3;

/**
 * Created by afterqcd on 16/9/26.
 */
public class Demo {
    public static void main(String[] args) {
        GeneratedMessageV3 addressBook = AddressBookOuterClass.AddressBook.newBuilder().build();
        addressBook.toByteArray();
    }
}
