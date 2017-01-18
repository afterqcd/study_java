package com.afterqcd.study.grpc.routeguide.client;

import com.afterqcd.study.grpc.routeguide.model.Point;
import com.afterqcd.study.grpc.routeguide.model.Rectangle;
import com.afterqcd.study.grpc.routeguide.model.RouteNote;
import rx.Observable;

import java.util.concurrent.CountDownLatch;

/**
 * Created by afterqcd on 2017/1/13.
 */
public class RouteGuideClient {
    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(5);

        RouteGuideStub stub = new RouteGuideStub("FX", 8980);

        System.out.println(stub.getFeature(point(408122808, -743999179)));
        System.out.println(stub.getFeature(point(1, 1)));

        subscribe(stub.getFeatureAsync(point(408122808, -743999179)), latch);
        subscribe(stub.getFeatureAsync(point(1, 1)), latch);

        subscribe(
                stub.listFeatures(rectangle(point(408122808, -743999179), point(1, 1))),
                latch
        );

        Observable<Point> points = Observable.just(
                point(1, 1), point(2, 2), point(3, 3),
                point(408122808, -743999179)
        );
        subscribe(stub.recordRoute(points), latch);

        Observable<RouteNote> routeNotes = Observable.just(
                routeNote(point(1, 1), "first note"),
                routeNote(point(1, 1), "second note"),
                routeNote(point(1, 1), "third note")
        );
        subscribe(stub.routeChat(routeNotes), latch);

        latch.await();
        stub.shutdown();
    }

    private static Rectangle rectangle(Point p1, Point p2) {
        return Rectangle.newBuilder().setHi(p1).setLo(p2).build();
    }

    private static Point point(int lat, int lon) {
        return Point.newBuilder().setLatitude(lat).setLongitude(lon).build();
    }

    private static RouteNote routeNote(Point location, String message) {
        return RouteNote.newBuilder().setLocation(location).setMessage(message).build();
    }

    private static <T> void subscribe(final Observable<T> observable, final CountDownLatch latch) {
        observable.subscribe(
                System.out::println,
                e -> {
                    e.printStackTrace();
                    latch.countDown();
                },
                latch::countDown
        );
    }
}
