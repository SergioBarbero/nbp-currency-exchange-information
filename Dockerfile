FROM maven:3.6.0-jdk-12-alpine AS build
WORKDIR /app
COPY . .
RUN mvn package

FROM openjdk:12-jdk-alpine
COPY --from=build /app/nbp-rest-api/target/nbp-rest-api-*.jar /usr/local/lib/nbp-app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/nbp-app.jar"]