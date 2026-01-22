FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app


COPY . .

ARG SERVICE

RUN mvn -pl ${SERVICE} -am -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app

ARG SERVICE

COPY --from=build /app/${SERVICE}/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
