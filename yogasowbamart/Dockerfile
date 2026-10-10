# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run stage - Tomcat
FROM tomcat:10.1-jdk21
WORKDIR /usr/local/tomcat/webapps/

RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/*.war ./ROOT.war

EXPOSE 8080

# Railway வழங்கும் PORT-ஐ Tomcat-க்கு பொருத்துவது
CMD ["sh", "-c", "sed -i 's/port=\"8080\"/port=\"'$PORT'\"/g' /usr/local/tomcat/conf/server.xml && catalina.sh run"]
