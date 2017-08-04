package com.afterqcd.study.grpc.routeguide.server;

import com.afterqcd.study.grpc.routeguide.model.Feature;
import com.afterqcd.study.grpc.routeguide.model.FeatureDatabase;
import com.google.protobuf.util.JsonFormat;
import io.grpc.Server;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyServerBuilder;
import io.netty.handler.ssl.SslContext;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Collection;
import javax.net.ssl.SSLException;

/**
 * Created by afterqcd on 2017/1/13.
 */
public class RouteGuideServer {
    public static final URL FEATURES_FILE = RouteGuideServer.class.getClassLoader()
            .getResource("route_guide_db.json");
    private final int port;
    private final Server server;

    public RouteGuideServer(int port) throws IOException {
        this(port, parseFeatures(FEATURES_FILE));
    }

    private static Collection<Feature> parseFeatures(URL file) throws IOException {
        try (InputStream inputStream = file.openStream()) {
            try (Reader reader = new InputStreamReader(inputStream)) {
                FeatureDatabase.Builder builder = FeatureDatabase.newBuilder();
                JsonFormat.parser().merge(reader, builder);
                return builder.build().getFeatureList();
            }
        }
    }

    public RouteGuideServer(int port, Collection<Feature> features) throws SSLException {
        this(NettyServerBuilder.forPort(port), port, features);
    }

    public RouteGuideServer(NettyServerBuilder serverBuilder, int port, Collection<Feature> features)
            throws SSLException {
        this.port = port;
        this.server = serverBuilder
                .addService(new RouteGuideService(features))
//                .sslContext(getSslContext())
                .build();
    }

    private static SslContext getSslContext() throws SSLException {
        ClassLoader classLoader = RouteGuideServer.class.getClassLoader();
        return GrpcSslContexts.forServer(
                new File(classLoader.getResource("server.crt").getFile()),
                new File(classLoader.getResource("private_key_pkcs8.pem").getFile())
        ).build();
    }

    public void start() throws IOException {
        this.server.start();
        System.out.println("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down the server");
            RouteGuideServer.this.stop();
            System.out.println("Server shut down");
        }));
    }

    public void stop() {
        if (this.server != null) {
            this.server.shutdown();
        }
    }

    private void blockUtilShutdown() throws InterruptedException {
        if (this.server != null) {
            this.server.awaitTermination();
        }
    }

    public static void main(String[] args) throws Exception {
        RouteGuideServer server = new RouteGuideServer(8980);
        server.start();
        server.blockUtilShutdown();
    }
}
