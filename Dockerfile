FROM jelastic/maven:3.9.5-openjdk-21 AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/superheroes-1.0-SNAPSHOT.jar /app/superheroes.jar

EXPOSE 8080

CMD ["java", "-jar", "superheroes.jar"]
