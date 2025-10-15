# -------------------------------
# Step 1: Build Stage (Maven + Java 17)
# -------------------------------
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy Maven config for dependency caching
COPY pom.xml . 
COPY mvnw . 
COPY .mvn .mvn

# Download dependencies only
RUN mvn -B dependency:go-offline

# Copy source code
COPY src ./src

# Build the project (skip tests to speed up)
RUN mvn clean package -DskipTests

# -------------------------------
# Step 2: Runtime Stage (Java 17)
# -------------------------------
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy the built JAR
COPY --from=build /app/target/*.jar app.jar

# Expose Spring Boot default port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]

