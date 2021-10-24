FROM openjdk:14.0.2-oracle
VOLUME ["/application.properties"]
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar","--spring.config.location=/application.yml"]