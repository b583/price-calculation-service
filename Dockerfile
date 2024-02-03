FROM openjdk:17
MAINTAINER https://github.com/b583
COPY target/price-calculation-service-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar
COPY src/main/config/config.yaml config.yaml
# TODO ENV VARS
ENTRYPOINT ["java","-jar","/app.jar", "server", "config.yaml"]