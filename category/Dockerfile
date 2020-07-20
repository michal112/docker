FROM openjdk:8-jre-alpine
ENV VERSION=0.0.1-SNAPSHOT
RUN ./mvnw clean \
    && ./mvnw package \
    && mkdir app
WORKDIR app
COPY target/category-$(VERSION).jar app.jar
CMD["java", "jar". "app.jar"]