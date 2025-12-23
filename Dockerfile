# ----------- BUILD STAGE -----------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy everything
COPY . .

# Give execute permission to mvnw
RUN chmod +x mvnw

# Build the jar
RUN ./mvnw clean package -DskipTests

# ----------- RUN STAGE -----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Render uses PORT env variable
EXPOSE 8080

# Start the app
CMD ["java", "-jar", "app.jar"]
