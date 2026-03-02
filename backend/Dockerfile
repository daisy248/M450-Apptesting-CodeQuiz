# build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /tmp
COPY . .
RUN mvn -DskipTests clean package
# deployment stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /tmp/target/*.jar wiss_quiz_rest.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/wiss_quiz_rest.jar"]
