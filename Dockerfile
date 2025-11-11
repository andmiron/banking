# Build stage

FROM gradle:8.14.3-jdk17 AS build
ENV BUILD_PATH=/workspace
WORKDIR $BUILD_PATH
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
RUN gradle build --no-daemon --build-cache || return 0
COPY . .
RUN gradle build --no-daemon -x test

# Package stage
FROM eclipse-temurin:17-alpine
WORKDIR /app
COPY --from=build /workspace/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]