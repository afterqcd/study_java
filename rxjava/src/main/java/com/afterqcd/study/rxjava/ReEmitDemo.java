package com.afterqcd.study.rxjava;

import rx.Observable;

/**
 * Created by afterqcd on 16/9/12.
 */
public class ReEmitDemo {
    public static void main(String[] args) {
        Observable<String> elems1 = Observable.just(1, 2, 3).map(i -> {
            System.out.println("elems1 emit " + i);
            return i.toString();
        });
        Observable<String> elems2 = Observable.just(4, 5, 6).map(i -> {
            System.out.println("elems2 emit " + i);
            return i.toString();
        });
        Observable<String> elems3 = Observable.just(7, 8, 9).map(i -> {
            System.out.println("elems3 emit " + i);
            return i.toString();
        });

        Observable.zip(elems1, elems2, (e1, e2) -> e1 + "_" + e2)
                .subscribe(System.out::println);

        Observable.zip(elems1, elems3, (e1, e3) -> e1 + "_" + e3)
                .subscribe(System.out::println);
    }
}
