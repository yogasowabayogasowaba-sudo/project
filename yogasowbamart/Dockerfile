# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run stage - Tomcat Server
FROM tomcat:10.1-jdk21
WORKDIR /usr/local/tomcat/webapps/

RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/*.war ./ROOT.war

ENV PORT=8080
EXPOSE 8080

CMD ["catalina.sh", "run"]
