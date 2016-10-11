package com.afterqcd.study.rxjava;

import rx.Observable;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * Created by afterqcd on 16/9/9.
 */
public class ToBlockingDemo {
    public static void main(String[] args) {
        Iterator<Long> iterator = Observable.interval(100, TimeUnit.MILLISECONDS)
                .toBlocking().getIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
