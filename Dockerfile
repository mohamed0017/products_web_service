# Use the official OpenJDK base image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the fat JAR (or the JAR with all dependencies bundled) into the container
COPY build/libs/build/libs/build/libs/build/libs/products-1.0-SNAPSHOT.jar /app/your-app-all.jar

# Expose the port your application will listen on (example: 8080)
EXPOSE 8080

# Run the JAR file when the container starts
CMD ["java", "-jar", "your-app-all.jar"]
