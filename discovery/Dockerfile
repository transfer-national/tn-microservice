FROM eclipse-temurin:17-alpine

RUN adduser ensa -D

USER ensa

WORKDIR /home/ensa/app

COPY target/build.jar .

CMD ["java", "-jar", "build.jar"]