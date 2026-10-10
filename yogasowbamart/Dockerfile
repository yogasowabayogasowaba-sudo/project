# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
# target ஃபோல்டரில் இருக்கும் .jar / .war ஃபைலை சரியாக app.jar என காப்பி செய்ய
COPY --from=build /app/target/*.[jw]ar app.jar
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]
