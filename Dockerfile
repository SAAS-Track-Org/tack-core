# ── Stage 1: Build ──────────────────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -q

COPY src ./src
RUN mvn package -DskipTests -q

# Mostra o que foi gerado no target para debug
RUN ls -la /app/target/*.jar

# ── Stage 2: Runtime ─────────────────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN addgroup -S app && adduser -S app -G app

# spring-boot:repackage gera o fat JAR — usa o repackaged diretamente
COPY --from=builder /app/target/*.jar /tmp/
RUN find /tmp -name "*.jar" ! -name "*.original" -exec cp {} app.jar \; && \
    chown app:app app.jar

USER app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]