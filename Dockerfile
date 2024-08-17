# Stage 1: Build the Spring Boot application
FROM openjdk:17-slim AS build

ARG JAR_FILE=build/libs/housekeeping-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Stage 2: Set up Nginx, Spring Boot application, and Certbot
FROM nginx:alpine

# Install required tools, Java, and Certbot
RUN apk add --no-cache openjdk17-jre certbot certbot-nginx supervisor

# Copy the built JAR file and Nginx config
COPY --from=build /app.jar /app.jar
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Set up directories for Certbot
RUN mkdir -p /var/www/certbot /etc/letsencrypt

# Expose ports for HTTP and HTTPS
EXPOSE 80 443

# Copy Supervisor configuration file
COPY supervisord.conf /etc/supervisord.conf

# Start Supervisor (which will manage Nginx, Certbot, and the Spring Boot application)
CMD ["supervisord", "-c", "/etc/supervisord.conf"]
