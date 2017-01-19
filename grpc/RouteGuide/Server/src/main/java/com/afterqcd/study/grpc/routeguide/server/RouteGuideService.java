package com.afterqcd.study.grpc.routeguide.server;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

import com.afterqcd.study.grpc.ServiceDelegator;
import com.afterqcd.study.grpc.routeguide.model.Feature;
import com.afterqcd.study.grpc.routeguide.model.Point;
import com.afterqcd.study.grpc.routeguide.model.Rectangle;
import com.afterqcd.study.grpc.routeguide.model.RouteNote;
import com.afterqcd.study.grpc.routeguide.model.RouteSummary;
import com.afterqcd.study.grpc.routeguide.service.RouteGuideGrpc;
import com.google.common.collect.Lists;
import io.grpc.stub.StreamObserver;
import rx.Observable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by afterqcd on 2017/1/13.
 */
public class RouteGuideService extends RouteGuideGrpc.RouteGuideImplBase {
    private final List<Feature> features;
    private final ConcurrentMap<Point, List<RouteNote>> routeNotes;

    public RouteGuideService(Collection<Feature> features) {
        this.features = Lists.newArrayList(features);
        this.routeNotes = new ConcurrentHashMap<>();
    }

    @Override
    public void getFeature(Point request, StreamObserver<Feature> response) {
        System.out.println("getFeature received " + request);
        ServiceDelegator.delegate(request, response, this::getFeature);
    }

    private Observable<Feature> getFeature(Point point) {
        Feature feature = checkFeature(point);
        if (feature == null) {
            feature = Feature.newBuilder().setName("").setLocation(point).build();
        }
        return Observable.just(feature);
    }

    private Feature checkFeature(Point point) {
        for (Feature feature : features) {
            if (feature.getLocation().equals(point)) {
                return feature;
            }
        }
        return null;
    }

    @Override
    public void listFeatures(Rectangle request, StreamObserver<Feature> response) {
        ServiceDelegator.delegate(request, response, this::listFeatures);
    }

    private Observable<Feature> listFeatures(Rectangle rectangle) {
        final int left = min(rectangle.getLo().getLongitude(), rectangle.getHi().getLongitude());
        final int right = max(rectangle.getLo().getLongitude(), rectangle.getHi().getLongitude());
        final int top = max(rectangle.getLo().getLatitude(), rectangle.getHi().getLatitude());
        final int bottom = min(rectangle.getLo().getLatitude(), rectangle.getHi().getLatitude());

        return Observable.from(features)
                .filter(f -> {
                    int lat = f.getLocation().getLatitude();
                    int lon = f.getLocation().getLongitude();
                    return lon >= left && lon <= right && lat >= bottom && lat <= top;
                });
    }

    @Override
    public StreamObserver<Point> recordRoute(StreamObserver<RouteSummary> response) {
        return ServiceDelegator.delegateWithClientStream(response, this::recordRoute);
    }

    private Observable<RouteSummary> recordRoute(Observable<Point> points) {
        return points.reduce(new Summary(), (s, p) -> {
            System.out.println("recordRoute received " + p);
            s.pointCount++;
            if (checkFeature(p) != null) {
                s.featureCount++;
            }
            return s;
        }).map(s -> RouteSummary.newBuilder()
                .setPointCount(s.pointCount)
                .setFeatureCount(s.featureCount)
                .setElapsedTime((int) TimeUnit.NANOSECONDS.toSeconds(
                        System.nanoTime() - s.startTime
                )).build()
        );
    }

    @Override
    public StreamObserver<RouteNote> routeChat(StreamObserver<RouteNote> response) {
        return ServiceDelegator.delegateWithClientStream(response, this::routeChat);
    }

    private Observable<RouteNote> routeChat(Observable<RouteNote> request) {
        return request.flatMap(note -> {
            List<RouteNote> preNotes = getOrCreateNotes(note.getLocation());
            Observable<RouteNote> response = Observable.from(
                    preNotes.toArray(new RouteNote[preNotes.size()])
            );
            preNotes.add(note);
            return response;
        });
    }

    private List<RouteNote> getOrCreateNotes(Point location) {
        List<RouteNote> notes = Collections.synchronizedList(new ArrayList<>());
        List<RouteNote> previousNotes = this.routeNotes.putIfAbsent(location, notes);
        return previousNotes == null ? notes : previousNotes;
    }
}

