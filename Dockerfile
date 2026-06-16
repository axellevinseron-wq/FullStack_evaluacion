FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /workspace

ARG MODULE
ARG JAR_NAME

COPY . .

RUN mvn -pl ${MODULE} -am clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

ARG MODULE
ARG JAR_NAME

COPY --from=build /workspace/${MODULE}/target/${JAR_NAME}.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
