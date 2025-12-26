# Build stage
FROM maven:3.9.12-eclipse-temurin-21 AS build
WORKDIR /workspace

# copy everything and build (works with plain Maven or with mvnw/.mvn)
COPY . .
RUN mvn -B -DskipTests package

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# copy the built jar (Spring Boot produces an executable jar in target/)
COPY --from=build /workspace/target/*.jar app.jar

EXPOSE 8080
ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]