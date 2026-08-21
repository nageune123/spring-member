# 1단계: Build
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

COPY . .

RUN ./gradlew clean build -x test


# 2단계: Run
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/build/libs/spring-member-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
