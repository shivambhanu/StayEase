# Use Amazon Corretto 21 as the base image
FROM amazoncorretto:21-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the executable JAR file to the working directory
COPY build/libs/hotel-0.0.1-SNAPSHOT.jar app.jar


# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
