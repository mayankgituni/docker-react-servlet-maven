FROM maven:3-jdk-11 as build

WORKDIR /app

RUN curl -sL https://deb.nodesource.com/setup_12.x | bash - \
    && apt-get install -y --no-install-recommends nodejs \
    && npm install -g yarn

COPY pom.xml ./

RUN mvn -B -T 1C dependency:go-offline

COPY src/main/client/package*.json ./src/main/client/

RUN cd src/main/client/ && yarn

COPY . ./

RUN mvn -B -T 1C package -DskipTests

FROM tomcat:9-jre11

RUN rm -rf /usr/local/tomcat/webapps/ROOT

COPY --from=build /app/target/reactServlet.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
