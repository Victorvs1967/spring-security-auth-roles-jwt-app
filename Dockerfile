# Build stage
FROM maven:3.8.5-openjdk-17 AS build

ARG JAR_FILE=/home/app/target/*.jar

COPY src /home/app/src
COPY pom.xml /home/app

RUN mvn -f /home/app/pom.xml clean package -DskipTests
RUN mv ${JAR_FILE} app.jar

# Run stage
FROM openjdk:17

COPY --from=build app.jar /usr/local/lib/app.jar

EXPOSE 8080
USER 10014

ENTRYPOINT java -jar /usr/local/lib/app.jar