package com.afterqcd.study.rxjava;

/**
 * Created by afterqcd on 16/7/29.
 */
public class Logger {
    public static void log(String message) {
        System.out.println(message + " on " + Thread.currentThread().getName());
    }
}
