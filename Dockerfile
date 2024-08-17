# Stage 1: Build the Spring Boot application
FROM openjdk:17-slim AS build

ARG JAR_FILE=build/libs/housekeeping-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Stage 2: Set up Nginx and Spring Boot application
FROM nginx:alpine

# Install required tools and Java
RUN apk add --no-cache openjdk17-jre

# Copy the built JAR file and Nginx config
COPY --from=build /app.jar /app.jar
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Ensure the SSL-related files exist to prevent errors
RUN mkdir -p /etc/letsencrypt/live/back.bit-two.com && \
    touch /etc/letsencrypt/options-ssl-nginx.conf && \
    touch /etc/letsencrypt/live/back.bit-two.com/fullchain.pem && \
    touch /etc/letsencrypt/live/back.bit-two.com/privkey.pem

# Expose port for HTTP
EXPOSE 80

# Start Nginx and the Spring Boot application
CMD ["sh", "-c", "java -jar /app.jar & nginx -g 'daemon off;'"]
