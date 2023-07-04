# Use an OpenJDK base image
FROM openjdk:11-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file and any other necessary files to the working directory
COPY target/core-ws-0.0.1-SNAPSHOT.jar .
COPY src/main/resources/ src/main/resources/
# Expose port 8080
EXPOSE 8080

# Start the Spring Boot application
CMD ["java", "-jar", "core-ws-0.0.1-SNAPSHOT.jar"]