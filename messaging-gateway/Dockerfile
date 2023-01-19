FROM maven:3.8.3-openjdk-17 as build

WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17 as run
WORKDIR /app
COPY --from=build ./build/target/*.jar ./application.jar

EXPOSE 8080
ENTRYPOINT java -jar application.jar