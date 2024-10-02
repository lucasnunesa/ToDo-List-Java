FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-21-jdk -y

COPY . .

RUN apt-get install maven -y
RUN mvn clean install

EXPOSE 8080

COPY --from=build /target/classes/com/list/todolist app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

