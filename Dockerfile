# --------Build stage--------
FROM eclipse-temurin:22-jdk AS build
WORKDIR /src
COPY . .
RUN chmod +x mvnw || true
RUN ./mvnw -B -DskipTests package

# --------Run stage--------
FROM eclipse-temurin:22-jre
WORKDIR /app
# Copy JAR built in first stage
COPY --from=build /src/target/*.jar /app/app.jar
COPY .env /app/.env

# Expose the port (value will come from .env → docker-compose.yml)
EXPOSE ${APP_PORT}

# Optional: allow passing JVM options
ENV JAVA_OPTS=""

# Use APP_PORT instead of hardcoding PORT
CMD ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${APP_PORT} -jar /app/app.jar"]