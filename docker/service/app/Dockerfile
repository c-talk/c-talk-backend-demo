FROM openjdk:11-jre-bullseye
WORKDIR /app
COPY ./target/*.jar /app/c-talk.jar
ENTRYPOINT ["java", "-jar", "/app/c-talk.jar"]