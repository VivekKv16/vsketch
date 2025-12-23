FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

# ✅ FIX: give execute permission to mvnw
RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/*.jar"]
