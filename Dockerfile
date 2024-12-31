git# Use the official OpenJDK image as the base image
FROM openjdk:17-jdk-slim as build

# Set the working directory in the container
WORKDIR /app

# Copy the Maven or Gradle build files (pom.xml for Maven or build.gradle for Gradle)
COPY pom.xml .
# If you are using Gradle instead of Maven, use: COPY build.gradle .

# Download dependencies to cache them
RUN ./mvnw dependency:go-offline -B  # If you're using Maven Wrapper
# Or, if using Gradle:
# RUN ./gradlew build --no-daemon --parallel

# Copy the rest of the source code into the container
COPY . .

# Build the application using Maven or Gradle
RUN ./mvnw clean package -DskipTests  # If you're using Maven Wrapper
# Or, if using Gradle:
# RUN ./gradlew build --no-daemon

# Use a new image to run the application
FROM openjdk:17-jdk-slim

# Set the working directory for the runtime image
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar
# If you're using Gradle, the JAR file might be under 'build/libs'

# Expose the port your Spring Boot app will run on (default is 8080)
EXPOSE 5000

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
