FROM --platform=linux/amd64 openjdk:17-alpine

WORKDIR /opt/server
COPY ./target/service-orders-0.0.1-SNAPSHOT.jar server.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "server.jar"]