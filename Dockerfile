FROM openjdk:17
LABEL org.opencontainers.image.authors="https://github.com/b583"
COPY target/price-calculation-service-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar
COPY config.yaml config.yaml
ENTRYPOINT ["java","-jar","/app.jar", "server", "config.yaml"]