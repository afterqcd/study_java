package com.afterqcd.study.grpc.routeguide.service;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;

import com.afterqcd.study.grpc.StubUtils;
import com.afterqcd.study.grpc.Method;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import rx.Observable;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.0.3)",
    comments = "Source: route_guide_service.proto")
public class RouteGuideGrpc {

  private RouteGuideGrpc() {}

  public static final String SERVICE_NAME = "routeguide.RouteGuide";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.afterqcd.study.grpc.routeguide.model.Point,
      com.afterqcd.study.grpc.routeguide.model.Feature> METHOD_GET_FEATURE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "routeguide.RouteGuide", "GetFeature"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.afterqcd.study.grpc.routeguide.model.Point.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.afterqcd.study.grpc.routeguide.model.Feature.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.afterqcd.study.grpc.routeguide.model.Rectangle,
      com.afterqcd.study.grpc.routeguide.model.Feature> METHOD_LIST_FEATURES =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING,
          generateFullMethodName(
              "routeguide.RouteGuide", "ListFeatures"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.afterqcd.study.grpc.routeguide.model.Rectangle.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.afterqcd.study.grpc.routeguide.model.Feature.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.afterqcd.study.grpc.routeguide.model.Point,
      com.afterqcd.study.grpc.routeguide.model.RouteSummary> METHOD_RECORD_ROUTE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING,
          generateFullMethodName(
              "routeguide.RouteGuide", "RecordRoute"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.afterqcd.study.grpc.routeguide.model.Point.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.afterqcd.study.grpc.routeguide.model.RouteSummary.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.afterqcd.study.grpc.routeguide.model.RouteNote,
      com.afterqcd.study.grpc.routeguide.model.RouteNote> METHOD_ROUTE_CHAT =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING,
          generateFullMethodName(
              "routeguide.RouteGuide", "RouteChat"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.afterqcd.study.grpc.routeguide.model.RouteNote.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.afterqcd.study.grpc.routeguide.model.RouteNote.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static RouteGuideStub newStub(io.grpc.Channel channel) {
    StubUtils.onCreating(getServiceDescriptor());
      return new RouteGuideStub(channel);
    }

    /**
     * Creates a new blocking-style stub that supports unary and streaming output calls on the service
     */
    public static RouteGuideBlockingStub newBlockingStub(
        io.grpc.Channel channel) {
      StubUtils.onCreating(getServiceDescriptor());
        return new RouteGuideBlockingStub(channel);
      }

      /**
       * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
       */
      public static RouteGuideFutureStub newFutureStub(
          io.grpc.Channel channel) {
        StubUtils.onCreating(getServiceDescriptor());
          return new RouteGuideFutureStub(channel);
        }

        /**
         */
        public static abstract class RouteGuideImplBase implements io.grpc.BindableService {

          /**
           * <pre>
           * POST /routeguide.RouteGuide/GetFeature
           * </pre>
           */
          public Observable<com.afterqcd.study.grpc.routeguide.model.Feature> getFeature(
              com.afterqcd.study.grpc.routeguide.model.Point request) {
            return Observable.error(new NotImplementedException());
          }

          /**
           * <pre>
           * POST /routeguide.RouteGuide/ListFeatures
           * </pre>
           */
          public Observable<com.afterqcd.study.grpc.routeguide.model.Feature> listFeatures(
              com.afterqcd.study.grpc.routeguide.model.Rectangle request) {
            return Observable.error(new NotImplementedException());
          }

          /**
           * <pre>
           * POST /routeguide.RouteGuide/RecordRoute
           * </pre>
           */
          public Observable<com.afterqcd.study.grpc.routeguide.model.RouteSummary> recordRoute(
              Observable<com.afterqcd.study.grpc.routeguide.model.Point> requestObservable) {
            return Observable.error(new NotImplementedException());
          }

          /**
           * <pre>
           * POST /routeguide.RouteGuide/RouteChat
           * </pre>
           */
          public Observable<com.afterqcd.study.grpc.routeguide.model.RouteNote> routeChat(
              Observable<com.afterqcd.study.grpc.routeguide.model.RouteNote> requestObservable) {
            return Observable.error(new NotImplementedException());
          }

          @java.lang.Override public io.grpc.ServerServiceDefinition bindService() {
            return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
                .addMethod(
                  METHOD_GET_FEATURE,
                  asyncUnaryCall(
                    new MethodHandlers<
                      com.afterqcd.study.grpc.routeguide.model.Point,
                      com.afterqcd.study.grpc.routeguide.model.Feature>(
                        this, METHODID_GET_FEATURE)))
                .addMethod(
                  METHOD_LIST_FEATURES,
                  asyncServerStreamingCall(
                    new MethodHandlers<
                      com.afterqcd.study.grpc.routeguide.model.Rectangle,
                      com.afterqcd.study.grpc.routeguide.model.Feature>(
                        this, METHODID_LIST_FEATURES)))
                .addMethod(
                  METHOD_RECORD_ROUTE,
                  asyncClientStreamingCall(
                    new MethodHandlers<
                      com.afterqcd.study.grpc.routeguide.model.Point,
                      com.afterqcd.study.grpc.routeguide.model.RouteSummary>(
                        this, METHODID_RECORD_ROUTE)))
                .addMethod(
                  METHOD_ROUTE_CHAT,
                  asyncBidiStreamingCall(
                    new MethodHandlers<
                      com.afterqcd.study.grpc.routeguide.model.RouteNote,
                      com.afterqcd.study.grpc.routeguide.model.RouteNote>(
                        this, METHODID_ROUTE_CHAT)))
                .build();
          }
        }

        /**
         */
        public static final class RouteGuideStub extends io.grpc.stub.AbstractStub<RouteGuideStub> {
          private RouteGuideStub(io.grpc.Channel channel) {
            super(channel);
          }

          private RouteGuideStub(io.grpc.Channel channel,
              io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
          }

          @java.lang.Override
          protected RouteGuideStub build(io.grpc.Channel channel,
              io.grpc.CallOptions callOptions) {
            return new RouteGuideStub(channel, callOptions);
          }

          /**
           * <pre>
           * POST /routeguide.RouteGuide/GetFeature
           * </pre>
           */
          public Observable<com.afterqcd.study.grpc.routeguide.model.Feature> getFeature(
              com.afterqcd.study.grpc.routeguide.model.Point request) {
            return Method.wrap(request, (req, resObserver) -> {
                asyncUnaryCall(
                    getChannel().newCall(METHOD_GET_FEATURE, getCallOptions()), req, resObserver);
            });}

          /**
           * <pre>
           * POST /routeguide.RouteGuide/ListFeatures
           * </pre>
           */
          public Observable<com.afterqcd.study.grpc.routeguide.model.Feature> listFeatures(
              com.afterqcd.study.grpc.routeguide.model.Rectangle request) {
            return Method.wrap(request, (req, resObserver) -> {
                asyncServerStreamingCall(
                    getChannel().newCall(METHOD_LIST_FEATURES, getCallOptions()), req, resObserver);
            });}

          /**
           * <pre>
           * POST /routeguide.RouteGuide/RecordRoute
           * </pre>
           */
          public Observable<com.afterqcd.study.grpc.routeguide.model.RouteSummary> recordRoute(
              Observable<com.afterqcd.study.grpc.routeguide.model.Point> requestObservable) {
            return Method.wrapWithClientStream(requestObservable, resObserver -> {
                return asyncClientStreamingCall(
                    getChannel().newCall(METHOD_RECORD_ROUTE, getCallOptions()), resObserver);
            });}

          /**
           * <pre>
           * POST /routeguide.RouteGuide/RouteChat
           * </pre>
           */
          public Observable<com.afterqcd.study.grpc.routeguide.model.RouteNote> routeChat(
              Observable<com.afterqcd.study.grpc.routeguide.model.RouteNote> requestObservable) {
            return Method.wrapWithClientStream(requestObservable, resObserver -> {
                return asyncBidiStreamingCall(
                    getChannel().newCall(METHOD_ROUTE_CHAT, getCallOptions()), resObserver);
            });}
        }

        /**
         */
        public static final class RouteGuideBlockingStub extends io.grpc.stub.AbstractStub<RouteGuideBlockingStub> {
          private RouteGuideBlockingStub(io.grpc.Channel channel) {
            super(channel);
          }

          private RouteGuideBlockingStub(io.grpc.Channel channel,
              io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
          }

          @java.lang.Override
          protected RouteGuideBlockingStub build(io.grpc.Channel channel,
              io.grpc.CallOptions callOptions) {
            return new RouteGuideBlockingStub(channel, callOptions);
          }

          /**
           * <pre>
           * POST /routeguide.RouteGuide/GetFeature
           * </pre>
           */
          public com.afterqcd.study.grpc.routeguide.model.Feature getFeature(com.afterqcd.study.grpc.routeguide.model.Point request) {
            return blockingUnaryCall(
                getChannel(), METHOD_GET_FEATURE, getCallOptions(), request);
          }

          /**
           * <pre>
           * POST /routeguide.RouteGuide/ListFeatures
           * </pre>
           */
          public java.util.Iterator<com.afterqcd.study.grpc.routeguide.model.Feature> listFeatures(
              com.afterqcd.study.grpc.routeguide.model.Rectangle request) {
            return blockingServerStreamingCall(
                getChannel(), METHOD_LIST_FEATURES, getCallOptions(), request);
          }
        }

        /**
         */
        public static final class RouteGuideFutureStub extends io.grpc.stub.AbstractStub<RouteGuideFutureStub> {
          private RouteGuideFutureStub(io.grpc.Channel channel) {
            super(channel);
          }

          private RouteGuideFutureStub(io.grpc.Channel channel,
              io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
          }

          @java.lang.Override
          protected RouteGuideFutureStub build(io.grpc.Channel channel,
              io.grpc.CallOptions callOptions) {
            return new RouteGuideFutureStub(channel, callOptions);
          }

          /**
           * <pre>
           * POST /routeguide.RouteGuide/GetFeature
           * </pre>
           */
          public com.google.common.util.concurrent.ListenableFuture<com.afterqcd.study.grpc.routeguide.model.Feature> getFeature(
              com.afterqcd.study.grpc.routeguide.model.Point request) {
            return futureUnaryCall(
                getChannel().newCall(METHOD_GET_FEATURE, getCallOptions()), request);
          }
        }

        private static final int METHODID_GET_FEATURE = 0;
        private static final int METHODID_LIST_FEATURES = 1;
        private static final int METHODID_RECORD_ROUTE = 2;
        private static final int METHODID_ROUTE_CHAT = 3;

        private static class MethodHandlers<Req, Resp> implements
            io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
          private final RouteGuideImplBase serviceImpl;
          private final int methodId;

          public MethodHandlers(RouteGuideImplBase serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
          }

          @java.lang.Override
          @java.lang.SuppressWarnings("unchecked")
          public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
              case METHODID_GET_FEATURE:
                Method.delegate((com.afterqcd.study.grpc.routeguide.model.Point) request,
                    (io.grpc.stub.StreamObserver<com.afterqcd.study.grpc.routeguide.model.Feature>) responseObserver,
                    serviceImpl::getFeature);
                break;
              case METHODID_LIST_FEATURES:
                Method.delegate((com.afterqcd.study.grpc.routeguide.model.Rectangle) request,
                    (io.grpc.stub.StreamObserver<com.afterqcd.study.grpc.routeguide.model.Feature>) responseObserver,
                    serviceImpl::listFeatures);
                break;
              default:
                throw new AssertionError();
            }
          }

          @java.lang.Override
          @java.lang.SuppressWarnings("unchecked")
          public io.grpc.stub.StreamObserver<Req> invoke(
              io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
              case METHODID_RECORD_ROUTE:
                return (io.grpc.stub.StreamObserver<Req>) Method.delegateWithClientStream(
                    (io.grpc.stub.StreamObserver<com.afterqcd.study.grpc.routeguide.model.RouteSummary>) responseObserver,
                    serviceImpl::recordRoute);
              case METHODID_ROUTE_CHAT:
                return (io.grpc.stub.StreamObserver<Req>) Method.delegateWithClientStream(
                    (io.grpc.stub.StreamObserver<com.afterqcd.study.grpc.routeguide.model.RouteNote>) responseObserver,
                    serviceImpl::routeChat);
              default:
                throw new AssertionError();
            }
          }
        }

        public static io.grpc.ServiceDescriptor getServiceDescriptor() {
          return new io.grpc.ServiceDescriptor(SERVICE_NAME,
              METHOD_GET_FEATURE,
              METHOD_LIST_FEATURES,
              METHOD_RECORD_ROUTE,
              METHOD_ROUTE_CHAT);
        }

      }
