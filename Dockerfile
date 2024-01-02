FROM maven:3-eclipse-temurin-21 AS builder

LABEL MAINTAINER="khairulimran6810@gmail.com"
LABEL APPLICATION="Car Availability Application"

WORKDIR /app

# Remember to add the files you need for your app. Forgot to add the json file here.
COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY .mvn .mvn
COPY src src
COPY HDBCarparkInformation.csv .

RUN mvn package -Dmaven.test.skip=true

# Publish stage
FROM openjdk:21-jdk

ARG APP_DIR2=/app 
WORKDIR ${APP_DIR2}

# Remember to change the jar names!
COPY --from=builder /app/target/carparkapp-0.0.1-SNAPSHOT.jar carparkapp.jar 
COPY --from=builder /app/HDBCarparkInformation.csv .

# Need these for the environment variables
ENV PORT=8080
#Copy these to your .txt file
ENV SPRING_REDIS_HOST=localhost 
ENV SPRING_REDIS_PORT=1234
ENV SPRING_REDIS_DATABASE=0
ENV SPRING_REDIS_USERNAME=default 
ENV SPRING_REDIS_PASSWORD=abc123

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar carparkapp.jar