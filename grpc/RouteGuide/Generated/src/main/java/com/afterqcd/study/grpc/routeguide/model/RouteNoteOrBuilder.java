// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/route_guide_model.proto

package com.afterqcd.study.grpc.routeguide.model;

public interface RouteNoteOrBuilder extends
    // @@protoc_insertion_point(interface_extends:routeguide.RouteNote)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>optional .routeguide.Point location = 1;</code>
   */
  boolean hasLocation();
  /**
   * <code>optional .routeguide.Point location = 1;</code>
   */
  com.afterqcd.study.grpc.routeguide.model.Point getLocation();
  /**
   * <code>optional .routeguide.Point location = 1;</code>
   */
  com.afterqcd.study.grpc.routeguide.model.PointOrBuilder getLocationOrBuilder();

  /**
   * <code>optional string message = 2;</code>
   */
  java.lang.String getMessage();
  /**
   * <code>optional string message = 2;</code>
   */
  com.google.protobuf.ByteString
      getMessageBytes();
}
