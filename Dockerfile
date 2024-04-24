FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/superheroes-1.0-SNAPSHOT.jar /app/superheroes.jar

EXPOSE 8080

CMD ["java", "-jar", "superheroes.jar"]
