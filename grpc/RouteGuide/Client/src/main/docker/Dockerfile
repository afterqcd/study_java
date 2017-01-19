FROM java:8-jdk-alpine
MAINTAINER afterqcd@qq.com
ENV APP grpc-route-guide-client
ENV APP_VERSION 1.0-SNAPSHOT
ADD ${APP}-${APP_VERSION}-bin.tar.gz /usr/app/
CMD java -Djava.ext.dirs=/usr/app/${APP}-${APP_VERSION}/lib com.afterqcd.study.grpc.routeguide.client.RouteGuideClient