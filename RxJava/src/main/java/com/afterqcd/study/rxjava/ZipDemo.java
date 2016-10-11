package com.afterqcd.study.rxjava;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.concurrent.Executors;

/**
 * Created by afterqcd on 16/9/9.
 */
public class ZipDemo {
    private static Scheduler scheduler = Schedulers.from(
            Executors.newFixedThreadPool(
                    1,
                    new ThreadFactoryBuilder().setNameFormat("scheduler-1-%d").build()
            )
    );

    public static void main(String[] args) {
        Observable<String> name = Observable.just("zhang san").subscribeOn(scheduler);
        Observable<Integer> age = Observable.just(15).subscribeOn(scheduler);
        Observable<String> gender = Observable.just("male").subscribeOn(scheduler);

        System.out.println("before blocking");
        System.out.println(Observable.zip(name, age, gender, People::new).toBlocking().single());
        System.out.println("after blocking");
    }

    public static class People {
        public final String name;
        public final int age;
        public final String gender;

        public People(String name, int age, String gender) {
            this.name = name;
            this.age = age;
            this.gender = gender;
        }

        @Override
        public String toString() {
            return name + ", " + age + ", " + gender;
        }
    }
}
