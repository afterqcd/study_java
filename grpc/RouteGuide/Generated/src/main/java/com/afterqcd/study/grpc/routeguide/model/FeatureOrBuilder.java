// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/route_guide_model.proto

package com.afterqcd.study.grpc.routeguide.model;

public interface FeatureOrBuilder extends
    // @@protoc_insertion_point(interface_extends:routeguide.Feature)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>optional string name = 1;</code>
   */
  java.lang.String getName();
  /**
   * <code>optional string name = 1;</code>
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <code>optional .routeguide.Point location = 2;</code>
   */
  boolean hasLocation();
  /**
   * <code>optional .routeguide.Point location = 2;</code>
   */
  com.afterqcd.study.grpc.routeguide.model.Point getLocation();
  /**
   * <code>optional .routeguide.Point location = 2;</code>
   */
  com.afterqcd.study.grpc.routeguide.model.PointOrBuilder getLocationOrBuilder();
}
