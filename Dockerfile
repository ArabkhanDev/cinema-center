FROM alpine:latest
RUN apk add --no-cache openjdk17
RUN apk add --no-cache tzdata
COPY build/libs/cinema-1.0.3e9a379.jar /app/
WORKDIR /app/
ENTRYPOINT ["java"]
CMD ["-jar", "/app/cinema-1.0.3e9a379.jar"]
