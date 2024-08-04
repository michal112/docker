FROM openjdk:23-ea-21-slim
ENV VERSION 0.0.1-SNAPSHOT
RUN mkdir app
WORKDIR app
COPY target/category-$VERSION.jar app.jar
EXPOSE 8081
CMD ["java", "-jar", "app.jar"]