// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/route_guide_model.proto

package com.afterqcd.study.grpc.routeguide.model;

public interface RectangleOrBuilder extends
    // @@protoc_insertion_point(interface_extends:routeguide.Rectangle)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>optional .routeguide.Point lo = 1;</code>
   */
  boolean hasLo();
  /**
   * <code>optional .routeguide.Point lo = 1;</code>
   */
  com.afterqcd.study.grpc.routeguide.model.Point getLo();
  /**
   * <code>optional .routeguide.Point lo = 1;</code>
   */
  com.afterqcd.study.grpc.routeguide.model.PointOrBuilder getLoOrBuilder();

  /**
   * <code>optional .routeguide.Point hi = 2;</code>
   */
  boolean hasHi();
  /**
   * <code>optional .routeguide.Point hi = 2;</code>
   */
  com.afterqcd.study.grpc.routeguide.model.Point getHi();
  /**
   * <code>optional .routeguide.Point hi = 2;</code>
   */
  com.afterqcd.study.grpc.routeguide.model.PointOrBuilder getHiOrBuilder();
}
