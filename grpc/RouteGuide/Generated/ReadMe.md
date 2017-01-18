## 安装Protoc
```bash
# wget https://github.com/google/protobuf/releases/download/v3.1.0/protobuf-php-3.1.0.zip
```

## 安装protoc-gen-grpc-java
```bash
git clone https://github.com/grpc/grpc-java.git
git checkout v1.0.3
cd grpc-java/compiler
../gradlew java_pluginExecutable
cp build/exe/java_plugin/protoc-gen-grpc-java path/to/some_dir_in_PATH
```

## 切换到项目目录下
```bash
cd $RouteGuideGeneratedHome/src/main
```

## 生成Message
```bash
protoc --java_out=java proto/route_guide_model.proto
```

## 生成Service
```bash
protoc --grpc-java_out=java -I=proto proto/route_guide_service.proto
```