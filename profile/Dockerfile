# Stage 1: Build
FROM maven:3.9.9-eclipse-temurin-17-alpine AS builder

WORKDIR /p_ss_bank_518

# Копируем только необходимые файлы
COPY pom.xml ./pom.xml
COPY profile/pom.xml ./profile/pom.xml
COPY profile/src ./profile/src

# Собираем модуль
RUN mvn -f profile/pom.xml clean package

# Stage 2: Run
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app
COPY --from=builder /p_ss_bank_518/profile/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

#FROM openjdk:17-jdk-slim
#VOLUME /tmp
#COPY target/*.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]