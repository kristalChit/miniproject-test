FROM maven:3-eclipse-temurin-21 AS builder

WORKDIR /src

COPY src src
COPY .mvn .mvn
COPY mvnw.cmd .
COPY mvnw .
COPY pom.xml .

RUN mvn package -Dmaven.test.skip=true

FROM openjdk:21-jdk-slim

WORKDIR /app

#COPY --from=builder /src/target/project-ss-0.0.1-SNAPSHOT.jar app.jar
#COPY --from=builder /src/email email

ENV PORT=8080 SPRING_REDIS_HOST=localhost SPRING_REDIS_PORT=6379
ENV SPRING_REDIS_USERNAME=NOT_SET SPRING_REDIS_PASSWORD=NOT_SET
ENV SPRING_REDIS_DATABASE=0

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar
