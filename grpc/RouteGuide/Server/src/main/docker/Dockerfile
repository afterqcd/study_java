FROM java:8-jdk-alpine
MAINTAINER afterqcd@qq.com
ENV APP_VERSION 1.0-SNAPSHOT
ADD grpc-route-guide-server-${APP_VERSION}-bin.tar.gz /usr/app/
CMD ["/bin/sh", "java", "-Djava.ext.dirs=/usr/app/grpc-route-guide-server-${APP_VERSION}/lib", "com.afterqcd.study.grpc.routeguide.server.RouteGuideServer"]