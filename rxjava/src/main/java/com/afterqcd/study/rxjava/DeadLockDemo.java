package com.afterqcd.study.rxjava;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by afterqcd on 16/9/19.
 */
public class DeadLockDemo {
    public static void main(String[] args) {
        for (int x = 0; x < 1000; x++) {
            Observable<Integer> merged = Observable.merge(
                    Observable.just(x).subscribeOn(Schedulers.io()),
                    Observable.just(x).subscribeOn(Schedulers.io())
            );

            merged.toList()
                    .map(i -> Observable.from(i).subscribeOn(Schedulers.io()).toList().toBlocking().single())
                    .subscribeOn(Schedulers.io())
                    .subscribe(System.out::println);
        }
    }
}
