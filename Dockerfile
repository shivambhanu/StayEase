# Use Amazon Corretto 21 as the base image
FROM amazoncorretto:21-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the executable JAR file to the working directory
COPY build/libs/hotel-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Set environment variables for MySQL (adjust as needed)
ENV SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/hotel_db
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=sql_pass


# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
