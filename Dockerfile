# Stage 1: Build the application
FROM bellsoft/liberica-runtime-container:jdk-cds-slim AS build
WORKDIR /app

# Copy the entire project into the container
COPY . .

# Package the application
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final image
FROM bellsoft/liberica-runtime-container:jdk-cds-slim
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/course-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the application will run on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]