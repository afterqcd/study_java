package com.afterqcd.study.rxjava;

import org.junit.Test;
import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

import java.util.Arrays;

/**
 * Created by afterqcd on 16/7/29.
 */
public class ThreadModelTest {
    /**
     * 默认情况下,create/operator/observer都在同一个线程中执行
     */
    @Test
    public void testDefault() {
        Logger.log("Start");

        Observable
                .create(o -> {
                    Logger.log("Created");
                    o.onNext(1);
                    o.onNext(2);
                    o.onCompleted();
                })
                .subscribe(Printer.create());

        Logger.log("Finished");
    }

    /**
     * 若事件源onNext/onComplete/onError不在当前线程执行,
     * 则operator/observer都在事件源onNext/onComplete/onError同一线程执行
     */
    @Test
    public void testReceiveNotificationsOnSubjectOnNextThread() {
        final BehaviorSubject<Integer> subject = BehaviorSubject.create();
        subject.subscribe(Printer.create());

        new Thread(() -> {
            Logger.log("onNext(1)");
            subject.onNext(1);
        }).start();

        new Thread(() -> {
            Logger.log("onNext(2)");
            subject.onNext(2);
        }).start();

        Logger.log("onNext(0)");
        subject.onNext(0);
    }

    /**
     * subscribeOn可以指定事件源onNext/onComplete/onError在哪个线程执行,
     * 相当于同时指定了后续operator/observer的执行线程
     */
    @Test
    public void testSubscribeOnNewThread() {
        Logger.log("Start");

        Observable
                .create(o -> {
                    Logger.log("Created");
                    o.onNext(1);
                    o.onNext(2);
                    o.onCompleted();
                })
                .doOnNext(i -> Logger.log("doOnNext(" + i + ") before subscribe on new thread"))
                .subscribeOn(Schedulers.newThread())
                .doOnNext(i -> Logger.log("doOnNext(" + i + ") before subscribe on new thread"))
                .subscribe(Printer.create());

        Logger.log("Finished");
    }

    /**
     * 只有第一个subscribeOn生效
     */
    @Test
    public void testOnlyTheFirstSubscribeOnTakeEffect() {
        Logger.log("Start");

        Observable
                .create(o -> {
                    Logger.log("Created");
                    o.onNext(1);
                    o.onNext(2);
                    o.onCompleted();
                })
                .subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(Printer.create());

        Logger.log("Finished");
    }

    /**
     * observeOn指定后续operator/observer的执行线程
     */
    @Test
    public void testObserveOnNewThread() {
        Logger.log("Start");

        Observable
                .create(o -> {
                    Logger.log("Created");
                    o.onNext(1);
                    o.onNext(2);
                    o.onCompleted();
                })
                .doOnNext(i -> Logger.log("doOnNext(" + i + ") before observe on new thread"))
                .observeOn(Schedulers.newThread())
                .doOnNext(i -> Logger.log("doOnNext(" + i + ") after observe on new thread"))
                .subscribe(Printer.create());

        Logger.log("Finished");
    }

    @Test
    public void testUnsubscribeOn() throws InterruptedException {
        Observable
                .using(
                        () -> {
                            Logger.log("Create source");
                            return Arrays.asList(1, 2, 3);
                        },
                        (elems) -> {
                            Logger.log("Create observable");
                            return Observable.from(elems);
                        },
                        (elems) -> Logger.log("Dispose source")
                )
                .unsubscribeOn(Schedulers.newThread())
                .subscribe(Printer.create());
    }
}
