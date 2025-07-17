#=================================================================================
#  Dockerfile: Multi-Stage Build for Spring Boot + Docker
#
#  Author: Ochwada
#  Date: 2025-07-17
#=================================================================================


# ========================
# 1. BUILD STAGE
# Heavy wait because it has maven.
# ========================
# Use Maven with Java 24 (Eclipse Temurin) on Alpine Linux for a lightweight build image
FROM maven:3.9-eclipse-temurin-24-alpine AS build

# Set the working directory inside the container to /app
# All subsequent commands (like COPY or RUN) will be relative to this directory
WORKDIR /app

# Copy the Maven project file (pom.xml) to the container
# This is done first to leverage Docker's caching for dependency downloads
COPY pom.xml .

# Copy the source code directory into the container
# This should include all Java code, resources, and configuration under src/
COPY src ./src

# Build the application using Maven, skipping tests to speed up the build
# The output will be a .jar file inside /app/target/
RUN mvn clean package -DskipTests


# ========================
# 2. RUN STAGE
# ========================
# Use a minimal Java 24 JDK Alpine image for running the app
FROM eclipse-temurin:24-jdk-alpine

# Set working directory inside the runtime container
WORKDIR /app

# Copy the generated JAR file from the build stage into the runtime container
COPY --from=build /app/target/*.jar app.jar

#Expose the default Spring Boot port
EXPOSE 6060

# Define the entrypoint command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
