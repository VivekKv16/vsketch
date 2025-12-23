FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

# Make mvnw executable (VERY IMPORTANT)
RUN chmod +x mvnw

# Build the app
RUN ./mvnw clean package -DskipTests

# Expose Spring Boot port
EXPOSE 8080

# Run the generated jar (CORRECT WAY)
CMD ["sh", "-c", "java -jar target/*.jar"]
