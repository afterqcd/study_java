package com.afterqcd.study.rxjava;

import rx.Observable;

import java.util.Random;

/**
 * Created by afterqcd on 2016/12/1.
 */
public class SyncPullDemo {
    public static void main(String[] args) {
        Observable<Consumer.PullResult> pullResults = Observable.create(subscriber -> {
            Consumer consumer = new Consumer(new Random().nextInt());
            while (true) {
                if (!subscriber.isUnsubscribed()) {
                    Consumer.PullResult pullResult = consumer.pull();
                    System.out.println("Polled and emitting " + pullResult);
                    subscriber.onNext(pullResult);
                } else {
                    break;
                }
            }
            subscriber.onCompleted();
        });

        pullResults.filter(pr -> pr.result > 0 ).take(2)
                .subscribe(pr -> System.out.println("Consuming " + pr));

        pullResults.filter(pr -> pr.result > 0 )
                .subscribe(pr -> System.out.println("Consuming " + pr));
    }
}

class Consumer {
    private final int id;

    Consumer(int id) {
        this.id = id;
    }

    PullResult pull() {
        return new PullResult(id, new Random().nextInt());
    }

    static class PullResult {
        final int consumerId;
        final int result;

        PullResult(int consumerId, int result) {
            this.consumerId = consumerId;
            this.result = result;
        }

        @Override
        public String toString() {
            return "Result " + result + " from consumer " + consumerId;
        }
    }
}


