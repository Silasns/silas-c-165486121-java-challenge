FROM maven:3.8.5-openjdk-17 as build
LABEL authors="Silas"
RUN mkdir - p /usr/src/app
WORKDIR /usr/src/app
ADD . /usr/src/app
RUN mvn package

FROM eclipse-temurin:17-jdk
RUN mkdir - p /usr/src/app
WORKDIR /usr/src/app
COPY --from=build /usr/src/app/target/loja-virtual-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

