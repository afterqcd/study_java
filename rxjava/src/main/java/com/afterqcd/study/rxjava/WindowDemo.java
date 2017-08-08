package com.afterqcd.study.rxjava;

import com.google.common.base.Strings;
import rx.Observable;

/**
 * Created by afterqcd on 2017/3/16.
 */
public class WindowDemo {
    public static void main(String[] args) {
        Observable.range(1, 9)
                .window(2, 1)
                .flatMap(window -> window.scan("", WindowDemo::join).takeLast(1))
                .subscribe(System.out::println);
    }

    private static <T> String join(String str, T item) {
        if (Strings.isNullOrEmpty(str)) {
            return item.toString();
        } else {
            return str + ", " + item;
        }
    }
}
