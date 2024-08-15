# Use the official OpenJDK slim image as a base image
FROM openjdk:17-slim

# Install required tools including apt-get
USER root
RUN apt-get update && apt-get install -y default-mysql-client

# Set the argument for the JAR file location
ARG JAR_FILE=build/libs/housekeeping-0.0.1-SNAPSHOT.jar

# Copy the JAR file to the container
COPY ${JAR_FILE} app.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app.jar"]