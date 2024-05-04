FROM eclipse-temurin:11-jre
WORKDIR /app
COPY ./target/*.jar /app/c-talk.jar
ENTRYPOINT ["java", "-jar", "/app/c-talk.jar"]