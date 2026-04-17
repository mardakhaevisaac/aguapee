FROM eclipse-temurin:17-jdk-alpine
COPY . .
RUN ./mvnw clean install -DskipTests
EXPOSE 8080
ENTRYPOINT ["java","-jar","target/*.jar"]
