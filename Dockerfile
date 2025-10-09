# -------------------------------
# Step 1: Build Stage (Maven + Java 17)
# -------------------------------
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy Maven config files for dependency caching
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Download dependencies only (speeds up rebuilds)
RUN mvn -B dependency:go-offline

# Copy the source code
COPY src ./src

# Build the project (produces the JAR in target/)
RUN mvn clean package -DskipTests

# -------------------------------
# Step 2: Runtime Stage (Java 17)
# -------------------------------
FROM eclipse-temurin:17-jdk-jammy

# Set working directory in runtime container
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose Spring Boot default port
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]


