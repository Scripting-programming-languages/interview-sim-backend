# 1: Сборка
FROM bellsoft/liberica-openjdk-debian:25 AS builder
WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts /app/

RUN chmod +x gradlew
RUN ./gradlew build -x test --no-daemon || true

COPY src src
RUN ./gradlew build -x test --no-daemon

# 2: Запуск
FROM bellsoft/liberica-openjdk-debian:25
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-Xmx512m", "-Dspring.profiles.active=dev", "-jar", "app.jar"]