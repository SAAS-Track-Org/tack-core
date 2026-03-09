# ── Stage 1: Build ──────────────────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -q

COPY src ./src
RUN mvn package -DskipTests -q

# Debug: lista tudo que foi gerado
RUN echo "=== Arquivos no target ===" && ls -la /app/target/

# ── Stage 2: Runtime ─────────────────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN addgroup -S app && adduser -S app -G app
USER app

COPY --from=builder /app/target/*.jar app.jar

RUN jar tf app.jar | grep "MANIFEST" || echo "MANIFEST NOT FOUND"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]