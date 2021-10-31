FROM openjdk:14.0.2-oracle
VOLUME ["/application.properties"]
ENV JAVA_OPTS "-XX:+UseG1GC"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["sh", "-c","java $JAVA_OPTS -jar /app.jar --spring.config.location=/application.yml"]