FROM maven:3.8.5-openjdk-17 AS builder
LABEL authors="IGOR RAMOS CRUZADO"
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-jammy
RUN apt-get update && apt-get install -y ca-certificates && rm -rf /var/lib/apt/lists/*
WORKDIR /app
ARG JAR_FILE=target/order-service-0.0.1-SNAPSHOT.jar
COPY --from=builder /app/${JAR_FILE} order-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "order-service.jar"]