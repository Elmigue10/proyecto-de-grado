FROM openjdk:19-jdk-slim
WORKDIR /app

COPY ./target/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
EXPOSE 8080