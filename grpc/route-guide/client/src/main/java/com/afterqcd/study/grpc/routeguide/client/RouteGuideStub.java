package com.afterqcd.study.grpc.routeguide.client;

import com.afterqcd.study.grpc.routeguide.model.Feature;
import com.afterqcd.study.grpc.routeguide.model.Point;
import com.afterqcd.study.grpc.routeguide.model.Rectangle;
import com.afterqcd.study.grpc.routeguide.model.RouteNote;
import com.afterqcd.study.grpc.routeguide.model.RouteSummary;
import com.afterqcd.study.grpc.routeguide.service.RouteGuideGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.util.RoundRobinLoadBalancerFactory;
import io.netty.handler.ssl.SslContext;
import rx.Observable;

import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLException;

/**
 * Created by afterqcd on 2017/1/17.
 */
public class RouteGuideStub {
    private final ManagedChannel channel;
    public final RouteGuideGrpc.RouteGuideBlockingStub blockingStub;
    public final RouteGuideGrpc.RouteGuideStub stub;

    public RouteGuideStub(String target) throws SSLException {
        this(
                NettyChannelBuilder.forTarget(target)
                        .loadBalancerFactory(RoundRobinLoadBalancerFactory.getInstance())
                        .usePlaintext(true)
//                        .sslContext(getSslContext())
        );
    }

    private static SslContext getSslContext() throws SSLException {
        return GrpcSslContexts.forClient().trustManager(
                RouteGuideClient.class.getClassLoader().getResourceAsStream("server.crt")
        ).build();
    }

    public RouteGuideStub(ManagedChannelBuilder channelBuilder) {
        this.channel = channelBuilder.build();

        this.blockingStub = RouteGuideGrpc.newBlockingStub(this.channel);
        this.stub = RouteGuideGrpc.newStub(this.channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(10, TimeUnit.SECONDS);
    }

    /**
     * 同步获取Point对应的Feature。
     *
     * @param point
     * @return
     */
    public Feature getFeature(Point point) {
        return blockingStub.getFeature(point);
    }

    /**
     * 异步获取Point对应的Feature。
     *
     * @param point
     * @return
     */
    public Observable<Feature> getFeatureAsync(Point point) {
        return stub.getFeature(point);
    }

    /**
     * 获取指定区域内的所有Feature。
     *
     * @param rectangle
     * @return
     */
    public Observable<Feature> listFeatures(Rectangle rectangle) {
        return stub.listFeatures(rectangle);
    }

    public Observable<RouteSummary> recordRoute(Observable<Point> points) {
        return stub.recordRoute(points);
    }

    public Observable<RouteNote> routeChat(Observable<RouteNote> routeNotes) {
        return stub.routeChat(routeNotes);
    }
}
