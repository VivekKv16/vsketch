FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy Maven wrapper & project files
COPY . .

# Give execute permission to mvnw
RUN chmod +x mvnw

# Build the Spring Boot jar
RUN ./mvnw clean package -DskipTests

# Expose Spring Boot port
EXPOSE 8080

# Run the generated jar (Spring Boot default name)
CMD ["java", "-jar", "target/vsketch-0.0.1-SNAPSHOT.jar"]
