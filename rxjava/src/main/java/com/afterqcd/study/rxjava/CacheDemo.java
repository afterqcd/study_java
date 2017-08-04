package com.afterqcd.study.rxjava;

import rx.Observable;

/**
 * Created by afterqcd on 2016/10/14.
 */
public class CacheDemo {
    public static void main(String[] args) {
        Observable.just(1, 2).cacheWithInitialCapacity(1)
                .subscribe(System.out::println);
    }
}
