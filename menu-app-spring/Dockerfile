FROM bellsoft/liberica-openjdk-alpine:17
COPY build/libs/menu-app-spring-1.0.jar /app/app.jar

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-jar", "/app/app.jar"]