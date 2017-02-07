# Version: 1.0.0
FROM alpine:3.5
MAINTAINER Afterqcd "afterqcd@gmail.com"
RUN \
  echo http://nl.alpinelinux.org/alpine/edge/main >> /etc/apk/repositories \
  && apk update \
  && apk --no-cache add nghttp2

ADD start.sh refreshBackends.sh /bin/