生成Avro代码
------------

    $ mvn compile
    
用mvn编译即可在com.afterqcd.study.serde.avro下生成LogEntry。

生成Protobuf代码
----------------

    $ protoc --java_out=src/main/java src/main/proto/log_entry.proto
    
使用protoc编译生成LogEntryOuterClass