# Use Amazon Corretto 21 as the base image
FROM amazoncorretto:21

# Set the working directory inside the container
WORKDIR /app

# Copy the executable JAR file to the working directory
COPY build/libs/hotel-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Set environment variables (if any)
# ENV EXAMPLE_ENV_VAR value

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
