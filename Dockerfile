# Maven Builder
FROM maven:3-jdk-8-alpine as build

WORKDIR /app

COPY pom.xml ./

COPY src ./src

EXPOSE 8080

CMD mvn tomcat7:run
