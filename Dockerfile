FROM eclipse-temurin:17-alpine

COPY target/build.jar .

CMD ["java", "-jar", "build.jar"]