// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: person.proto

package com.afterqcd.study.protobuf;

public final class PersonOuterClass {
  private PersonOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface PersonOrBuilder extends
      // @@protoc_insertion_point(interface_extends:com.afterqcd.study.protobuf.Person)
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
     * <code>optional int32 id = 2;</code>
     */
    int getId();

    /**
     * <code>optional string email = 3;</code>
     */
    java.lang.String getEmail();
    /**
     * <code>optional string email = 3;</code>
     */
    com.google.protobuf.ByteString
        getEmailBytes();

    /**
     * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
     */
    java.util.List<com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber> 
        getPhoneList();
    /**
     * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
     */
    com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber getPhone(int index);
    /**
     * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
     */
    int getPhoneCount();
    /**
     * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
     */
    java.util.List<? extends com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumberOrBuilder> 
        getPhoneOrBuilderList();
    /**
     * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
     */
    com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumberOrBuilder getPhoneOrBuilder(
        int index);
  }
  /**
   * Protobuf type {@code com.afterqcd.study.protobuf.Person}
   */
  public  static final class Person extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:com.afterqcd.study.protobuf.Person)
      PersonOrBuilder {
    // Use Person.newBuilder() to construct.
    private Person(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Person() {
      name_ = "";
      id_ = 0;
      email_ = "";
      phone_ = java.util.Collections.emptyList();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private Person(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      int mutable_bitField0_ = 0;
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!input.skipField(tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              java.lang.String s = input.readStringRequireUtf8();

              name_ = s;
              break;
            }
            case 16: {

              id_ = input.readInt32();
              break;
            }
            case 26: {
              java.lang.String s = input.readStringRequireUtf8();

              email_ = s;
              break;
            }
            case 34: {
              if (!((mutable_bitField0_ & 0x00000008) == 0x00000008)) {
                phone_ = new java.util.ArrayList<com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber>();
                mutable_bitField0_ |= 0x00000008;
              }
              phone_.add(
                  input.readMessage(com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber.parser(), extensionRegistry));
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        if (((mutable_bitField0_ & 0x00000008) == 0x00000008)) {
          phone_ = java.util.Collections.unmodifiableList(phone_);
        }
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.afterqcd.study.protobuf.PersonOuterClass.internal_static_com_afterqcd_study_protobuf_Person_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.afterqcd.study.protobuf.PersonOuterClass.internal_static_com_afterqcd_study_protobuf_Person_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.afterqcd.study.protobuf.PersonOuterClass.Person.class, com.afterqcd.study.protobuf.PersonOuterClass.Person.Builder.class);
    }

    private int bitField0_;
    public static final int NAME_FIELD_NUMBER = 1;
    private volatile java.lang.Object name_;
    /**
     * <code>optional string name = 1;</code>
     */
    public java.lang.String getName() {
      java.lang.Object ref = name_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        name_ = s;
        return s;
      }
    }
    /**
     * <code>optional string name = 1;</code>
     */
    public com.google.protobuf.ByteString
        getNameBytes() {
      java.lang.Object ref = name_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        name_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ID_FIELD_NUMBER = 2;
    private int id_;
    /**
     * <code>optional int32 id = 2;</code>
     */
    public int getId() {
      return id_;
    }

    public static final int EMAIL_FIELD_NUMBER = 3;
    private volatile java.lang.Object email_;
    /**
     * <code>optional string email = 3;</code>
     */
    public java.lang.String getEmail() {
      java.lang.Object ref = email_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        email_ = s;
        return s;
      }
    }
    /**
     * <code>optional string email = 3;</code>
     */
    public com.google.protobuf.ByteString
        getEmailBytes() {
      java.lang.Object ref = email_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        email_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int PHONE_FIELD_NUMBER = 4;
    private java.util.List<com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber> phone_;
    /**
     * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
     */
    public java.util.List<com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber> getPhoneList() {
      return phone_;
    }
    /**
     * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
     */
    public java.util.List<? extends com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumberOrBuilder> 
        getPhoneOrBuilderList() {
      return phone_;
    }
    /**
     * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
     */
    public int getPhoneCount() {
      return phone_.size();
    }
    /**
     * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
     */
    public com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber getPhone(int index) {
      return phone_.get(index);
    }
    /**
     * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
     */
    public com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumberOrBuilder getPhoneOrBuilder(
        int index) {
      return phone_.get(index);
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (!getNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, name_);
      }
      if (id_ != 0) {
        output.writeInt32(2, id_);
      }
      if (!getEmailBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, email_);
      }
      for (int i = 0; i < phone_.size(); i++) {
        output.writeMessage(4, phone_.get(i));
      }
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getNameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, name_);
      }
      if (id_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(2, id_);
      }
      if (!getEmailBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, email_);
      }
      for (int i = 0; i < phone_.size(); i++) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(4, phone_.get(i));
      }
      memoizedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.afterqcd.study.protobuf.PersonOuterClass.Person)) {
        return super.equals(obj);
      }
      com.afterqcd.study.protobuf.PersonOuterClass.Person other = (com.afterqcd.study.protobuf.PersonOuterClass.Person) obj;

      boolean result = true;
      result = result && getName()
          .equals(other.getName());
      result = result && (getId()
          == other.getId());
      result = result && getEmail()
          .equals(other.getEmail());
      result = result && getPhoneList()
          .equals(other.getPhoneList());
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptorForType().hashCode();
      hash = (37 * hash) + NAME_FIELD_NUMBER;
      hash = (53 * hash) + getName().hashCode();
      hash = (37 * hash) + ID_FIELD_NUMBER;
      hash = (53 * hash) + getId();
      hash = (37 * hash) + EMAIL_FIELD_NUMBER;
      hash = (53 * hash) + getEmail().hashCode();
      if (getPhoneCount() > 0) {
        hash = (37 * hash) + PHONE_FIELD_NUMBER;
        hash = (53 * hash) + getPhoneList().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.afterqcd.study.protobuf.PersonOuterClass.Person parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.afterqcd.study.protobuf.PersonOuterClass.Person parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.afterqcd.study.protobuf.PersonOuterClass.Person parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.afterqcd.study.protobuf.PersonOuterClass.Person parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.afterqcd.study.protobuf.PersonOuterClass.Person parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.afterqcd.study.protobuf.PersonOuterClass.Person parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.afterqcd.study.protobuf.PersonOuterClass.Person parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.afterqcd.study.protobuf.PersonOuterClass.Person parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.afterqcd.study.protobuf.PersonOuterClass.Person parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.afterqcd.study.protobuf.PersonOuterClass.Person parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(com.afterqcd.study.protobuf.PersonOuterClass.Person prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code com.afterqcd.study.protobuf.Person}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:com.afterqcd.study.protobuf.Person)
        com.afterqcd.study.protobuf.PersonOuterClass.PersonOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.afterqcd.study.protobuf.PersonOuterClass.internal_static_com_afterqcd_study_protobuf_Person_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.afterqcd.study.protobuf.PersonOuterClass.internal_static_com_afterqcd_study_protobuf_Person_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.afterqcd.study.protobuf.PersonOuterClass.Person.class, com.afterqcd.study.protobuf.PersonOuterClass.Person.Builder.class);
      }

      // Construct using com.afterqcd.study.protobuf.PersonOuterClass.Person.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
          getPhoneFieldBuilder();
        }
      }
      public Builder clear() {
        super.clear();
        name_ = "";

        id_ = 0;

        email_ = "";

        if (phoneBuilder_ == null) {
          phone_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000008);
        } else {
          phoneBuilder_.clear();
        }
        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.afterqcd.study.protobuf.PersonOuterClass.internal_static_com_afterqcd_study_protobuf_Person_descriptor;
      }

      public com.afterqcd.study.protobuf.PersonOuterClass.Person getDefaultInstanceForType() {
        return com.afterqcd.study.protobuf.PersonOuterClass.Person.getDefaultInstance();
      }

      public com.afterqcd.study.protobuf.PersonOuterClass.Person build() {
        com.afterqcd.study.protobuf.PersonOuterClass.Person result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.afterqcd.study.protobuf.PersonOuterClass.Person buildPartial() {
        com.afterqcd.study.protobuf.PersonOuterClass.Person result = new com.afterqcd.study.protobuf.PersonOuterClass.Person(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        result.name_ = name_;
        result.id_ = id_;
        result.email_ = email_;
        if (phoneBuilder_ == null) {
          if (((bitField0_ & 0x00000008) == 0x00000008)) {
            phone_ = java.util.Collections.unmodifiableList(phone_);
            bitField0_ = (bitField0_ & ~0x00000008);
          }
          result.phone_ = phone_;
        } else {
          result.phone_ = phoneBuilder_.build();
        }
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder clone() {
        return (Builder) super.clone();
      }
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.setField(field, value);
      }
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.afterqcd.study.protobuf.PersonOuterClass.Person) {
          return mergeFrom((com.afterqcd.study.protobuf.PersonOuterClass.Person)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.afterqcd.study.protobuf.PersonOuterClass.Person other) {
        if (other == com.afterqcd.study.protobuf.PersonOuterClass.Person.getDefaultInstance()) return this;
        if (!other.getName().isEmpty()) {
          name_ = other.name_;
          onChanged();
        }
        if (other.getId() != 0) {
          setId(other.getId());
        }
        if (!other.getEmail().isEmpty()) {
          email_ = other.email_;
          onChanged();
        }
        if (phoneBuilder_ == null) {
          if (!other.phone_.isEmpty()) {
            if (phone_.isEmpty()) {
              phone_ = other.phone_;
              bitField0_ = (bitField0_ & ~0x00000008);
            } else {
              ensurePhoneIsMutable();
              phone_.addAll(other.phone_);
            }
            onChanged();
          }
        } else {
          if (!other.phone_.isEmpty()) {
            if (phoneBuilder_.isEmpty()) {
              phoneBuilder_.dispose();
              phoneBuilder_ = null;
              phone_ = other.phone_;
              bitField0_ = (bitField0_ & ~0x00000008);
              phoneBuilder_ = 
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                   getPhoneFieldBuilder() : null;
            } else {
              phoneBuilder_.addAllMessages(other.phone_);
            }
          }
        }
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.afterqcd.study.protobuf.PersonOuterClass.Person parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.afterqcd.study.protobuf.PersonOuterClass.Person) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private java.lang.Object name_ = "";
      /**
       * <code>optional string name = 1;</code>
       */
      public java.lang.String getName() {
        java.lang.Object ref = name_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          name_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>optional string name = 1;</code>
       */
      public com.google.protobuf.ByteString
          getNameBytes() {
        java.lang.Object ref = name_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          name_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>optional string name = 1;</code>
       */
      public Builder setName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        name_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional string name = 1;</code>
       */
      public Builder clearName() {
        
        name_ = getDefaultInstance().getName();
        onChanged();
        return this;
      }
      /**
       * <code>optional string name = 1;</code>
       */
      public Builder setNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        name_ = value;
        onChanged();
        return this;
      }

      private int id_ ;
      /**
       * <code>optional int32 id = 2;</code>
       */
      public int getId() {
        return id_;
      }
      /**
       * <code>optional int32 id = 2;</code>
       */
      public Builder setId(int value) {
        
        id_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 id = 2;</code>
       */
      public Builder clearId() {
        
        id_ = 0;
        onChanged();
        return this;
      }

      private java.lang.Object email_ = "";
      /**
       * <code>optional string email = 3;</code>
       */
      public java.lang.String getEmail() {
        java.lang.Object ref = email_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          email_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>optional string email = 3;</code>
       */
      public com.google.protobuf.ByteString
          getEmailBytes() {
        java.lang.Object ref = email_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          email_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>optional string email = 3;</code>
       */
      public Builder setEmail(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        email_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional string email = 3;</code>
       */
      public Builder clearEmail() {
        
        email_ = getDefaultInstance().getEmail();
        onChanged();
        return this;
      }
      /**
       * <code>optional string email = 3;</code>
       */
      public Builder setEmailBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        email_ = value;
        onChanged();
        return this;
      }

      private java.util.List<com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber> phone_ =
        java.util.Collections.emptyList();
      private void ensurePhoneIsMutable() {
        if (!((bitField0_ & 0x00000008) == 0x00000008)) {
          phone_ = new java.util.ArrayList<com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber>(phone_);
          bitField0_ |= 0x00000008;
         }
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
          com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber, com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber.Builder, com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumberOrBuilder> phoneBuilder_;

      /**
       * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
       */
      public java.util.List<com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber> getPhoneList() {
        if (phoneBuilder_ == null) {
          return java.util.Collections.unmodifiableList(phone_);
        } else {
          return phoneBuilder_.getMessageList();
        }
      }
      /**
       * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
       */
      public int getPhoneCount() {
        if (phoneBuilder_ == null) {
          return phone_.size();
        } else {
          return phoneBuilder_.getCount();
        }
      }
      /**
       * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
       */
      public com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber getPhone(int index) {
        if (phoneBuilder_ == null) {
          return phone_.get(index);
        } else {
          return phoneBuilder_.getMessage(index);
        }
      }
      /**
       * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
       */
      public Builder setPhone(
          int index, com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber value) {
        if (phoneBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensurePhoneIsMutable();
          phone_.set(index, value);
          onChanged();
        } else {
          phoneBuilder_.setMessage(index, value);
        }
        return this;
      }
      /**
       * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
       */
      public Builder setPhone(
          int index, com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber.Builder builderForValue) {
        if (phoneBuilder_ == null) {
          ensurePhoneIsMutable();
          phone_.set(index, builderForValue.build());
          onChanged();
        } else {
          phoneBuilder_.setMessage(index, builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
       */
      public Builder addPhone(com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber value) {
        if (phoneBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensurePhoneIsMutable();
          phone_.add(value);
          onChanged();
        } else {
          phoneBuilder_.addMessage(value);
        }
        return this;
      }
      /**
       * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
       */
      public Builder addPhone(
          int index, com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber value) {
        if (phoneBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensurePhoneIsMutable();
          phone_.add(index, value);
          onChanged();
        } else {
          phoneBuilder_.addMessage(index, value);
        }
        return this;
      }
      /**
       * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
       */
      public Builder addPhone(
          com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber.Builder builderForValue) {
        if (phoneBuilder_ == null) {
          ensurePhoneIsMutable();
          phone_.add(builderForValue.build());
          onChanged();
        } else {
          phoneBuilder_.addMessage(builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
       */
      public Builder addPhone(
          int index, com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber.Builder builderForValue) {
        if (phoneBuilder_ == null) {
          ensurePhoneIsMutable();
          phone_.add(index, builderForValue.build());
          onChanged();
        } else {
          phoneBuilder_.addMessage(index, builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
       */
      public Builder addAllPhone(
          java.lang.Iterable<? extends com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber> values) {
        if (phoneBuilder_ == null) {
          ensurePhoneIsMutable();
          com.google.protobuf.AbstractMessageLite.Builder.addAll(
              values, phone_);
          onChanged();
        } else {
          phoneBuilder_.addAllMessages(values);
        }
        return this;
      }
      /**
       * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
       */
      public Builder clearPhone() {
        if (phoneBuilder_ == null) {
          phone_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000008);
          onChanged();
        } else {
          phoneBuilder_.clear();
        }
        return this;
      }
      /**
       * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
       */
      public Builder removePhone(int index) {
        if (phoneBuilder_ == null) {
          ensurePhoneIsMutable();
          phone_.remove(index);
          onChanged();
        } else {
          phoneBuilder_.remove(index);
        }
        return this;
      }
      /**
       * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
       */
      public com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber.Builder getPhoneBuilder(
          int index) {
        return getPhoneFieldBuilder().getBuilder(index);
      }
      /**
       * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
       */
      public com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumberOrBuilder getPhoneOrBuilder(
          int index) {
        if (phoneBuilder_ == null) {
          return phone_.get(index);  } else {
          return phoneBuilder_.getMessageOrBuilder(index);
        }
      }
      /**
       * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
       */
      public java.util.List<? extends com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumberOrBuilder> 
           getPhoneOrBuilderList() {
        if (phoneBuilder_ != null) {
          return phoneBuilder_.getMessageOrBuilderList();
        } else {
          return java.util.Collections.unmodifiableList(phone_);
        }
      }
      /**
       * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
       */
      public com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber.Builder addPhoneBuilder() {
        return getPhoneFieldBuilder().addBuilder(
            com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber.getDefaultInstance());
      }
      /**
       * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
       */
      public com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber.Builder addPhoneBuilder(
          int index) {
        return getPhoneFieldBuilder().addBuilder(
            index, com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber.getDefaultInstance());
      }
      /**
       * <code>repeated .com.afterqcd.study.protobuf.PhoneNumber phone = 4;</code>
       */
      public java.util.List<com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber.Builder> 
           getPhoneBuilderList() {
        return getPhoneFieldBuilder().getBuilderList();
      }
      private com.google.protobuf.RepeatedFieldBuilderV3<
          com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber, com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber.Builder, com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumberOrBuilder> 
          getPhoneFieldBuilder() {
        if (phoneBuilder_ == null) {
          phoneBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
              com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber, com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumber.Builder, com.afterqcd.study.protobuf.PhoneNumberOuterClass.PhoneNumberOrBuilder>(
                  phone_,
                  ((bitField0_ & 0x00000008) == 0x00000008),
                  getParentForChildren(),
                  isClean());
          phone_ = null;
        }
        return phoneBuilder_;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }


      // @@protoc_insertion_point(builder_scope:com.afterqcd.study.protobuf.Person)
    }

    // @@protoc_insertion_point(class_scope:com.afterqcd.study.protobuf.Person)
    private static final com.afterqcd.study.protobuf.PersonOuterClass.Person DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.afterqcd.study.protobuf.PersonOuterClass.Person();
    }

    public static com.afterqcd.study.protobuf.PersonOuterClass.Person getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Person>
        PARSER = new com.google.protobuf.AbstractParser<Person>() {
      public Person parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new Person(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Person> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Person> getParserForType() {
      return PARSER;
    }

    public com.afterqcd.study.protobuf.PersonOuterClass.Person getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_afterqcd_study_protobuf_Person_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_afterqcd_study_protobuf_Person_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014person.proto\022\033com.afterqcd.study.proto" +
      "buf\032\022phone_number.proto\"j\n\006Person\022\014\n\004nam" +
      "e\030\001 \001(\t\022\n\n\002id\030\002 \001(\005\022\r\n\005email\030\003 \001(\t\0227\n\005ph" +
      "one\030\004 \003(\0132(.com.afterqcd.study.protobuf." +
      "PhoneNumberb\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.afterqcd.study.protobuf.PhoneNumberOuterClass.getDescriptor(),
        }, assigner);
    internal_static_com_afterqcd_study_protobuf_Person_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_afterqcd_study_protobuf_Person_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_afterqcd_study_protobuf_Person_descriptor,
        new java.lang.String[] { "Name", "Id", "Email", "Phone", });
    com.afterqcd.study.protobuf.PhoneNumberOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}