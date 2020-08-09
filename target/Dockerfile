# Deploy Server
FROM jetty:9-jre8-alpine

COPY ./reactServlet.war /var/lib/jetty/webapps/

USER jetty

EXPOSE 8080

CMD java -jar $JETTY_HOME/start.jar
