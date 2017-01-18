package com.afterqcd.study.grpc;

import static com.afterqcd.study.grpc.Pipe.*;

import io.grpc.stub.StreamObserver;
import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func1;

/**
 * Created by afterqcd on 2017/1/17.
 */
public class StubWrapper {
    public static <I, O> Observable<O> wrap(
            final I input,
            final Action2<I, StreamObserver<O>> method) {
        return Observable.create(sub -> method.call(input, streamObserver(sub)));
    }

    public static <I, O> Observable<O> wrapWithClientStream(
            final Observable<I> inputObservable,
            final Func1<StreamObserver<O>, StreamObserver<I>> method) {
        return Observable.create(sub -> pipe(inputObservable, method.call(streamObserver(sub))));
    }
}
