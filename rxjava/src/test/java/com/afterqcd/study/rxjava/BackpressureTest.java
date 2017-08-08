package com.afterqcd.study.rxjava;

import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by afterqcd on 16/7/29.
 */
public class BackpressureTest {
    /**
     * 同步情况下,生产者会等待慢消费者,不存在问题
     *
     * @throws Exception
     */
    @Test
    public void testSync() throws Exception {
        Observable
                .just(1, 2, 3)
                .subscribe(i -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(i);
                });
    }

    @Test
    public void testAsync() throws Exception {
//        final CountDownLatch latch = new CountDownLatch(1);
//
//        Observable
//                .interval(1, TimeUnit.MILLISECONDS)
//                .take(500)
//                .observeOn(Schedulers.newThread())
//                .subscribe(
//                        i -> {
//                            try {
//                                Thread.sleep(100);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//
//                            System.out.println(i);
//                        },
//                        e -> {},
//                        latch::countDown
//                );
//
//        latch.await();
    }

    @Test
    public void testSolveBackpressureBySample() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);

        Observable
                .interval(1, TimeUnit.MILLISECONDS)
                .take(500)
                .observeOn(Schedulers.newThread())
                .sample(100, TimeUnit.MILLISECONDS)
                .subscribe(
                        i -> {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            System.out.println(i);
                        },
                        e -> {
                        },
                        latch::countDown
                );

        latch.await();
    }

    @Test
    public void testSolveBackpressureByBuffer() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);

        Observable
                .interval(1, TimeUnit.MILLISECONDS)
                .take(500)
                .observeOn(Schedulers.newThread())
                .buffer(100, TimeUnit.MILLISECONDS)
                .subscribe(
                        i -> {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            System.out.println(i);
                        },
                        e -> {
                        },
                        latch::countDown
                );

        latch.await();
    }

    @Test
    public void testSolveBackpressureByReactivePull() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);

        Observable
                .interval(1, TimeUnit.MILLISECONDS)
                .take(100)
                .observeOn(Schedulers.newThread())
                .subscribe(
                        new Subscriber<Long>() {
                            @Override
                            public void onStart() {
                                request(1);
                            }

                            @Override
                            public void onCompleted() {
                                latch.countDown();
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onNext(Long aLong) {
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                System.out.println(aLong);
                                request(1);
                            }
                        }
                );

        latch.await();

    }
}
