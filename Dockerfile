# ========================
# 1. Build Stage
# ========================
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy pom.xml and download dependencies first (better cache)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# ========================
# 2. Runtime Stage
# ========================
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy built JAR from builder
COPY --from=builder /app/target/hibernate-crud-demo-1.0-SNAPSHOT.jar app.jar

# Expose port 8080 for Render
EXPOSE 8080

# Run app
CMD ["java", "-jar", "app.jar"]
