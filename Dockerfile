# Use an OpenJDK base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Install Maven and build JAR
RUN apt-get update && apt-get install -y maven && mvn clean package -DskipTests

# Run the JAR
CMD ["java", "-jar", "target/hibernate-crud-demo-1.0-SNAPSHOT.jar"]
