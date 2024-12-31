
# Use the official OpenJDK image as the base image for building
FROM openjdk:17-jdk-slim as build

# Set the working directory in the container
WORKDIR /app

# Copy the Maven build configuration file
COPY pom.xml .

# Download dependencies and go offline
COPY .mvn/ .mvn
COPY mvnw .
RUN ./mvnw dependency:go-offline -B

# Copy the entire project and build the application
COPY . .
RUN ./mvnw clean package -DskipTests

# Use the same JDK image for the runtime environment
FROM openjdk:17-jdk-slim

# Set the working directory for the runtime
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application's port
EXPOSE 5000

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
