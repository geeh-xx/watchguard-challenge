FROM  --platform=linux/amd64 maven:3.9.7-eclipse-temurin-21-alpine

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean install

COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]