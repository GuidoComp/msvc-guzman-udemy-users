FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
ADD ./target/users-0.0.1-SNAPSHOT.jar users-server.jar

ENTRYPOINT ["java", "-jar", "users-server.jar"]