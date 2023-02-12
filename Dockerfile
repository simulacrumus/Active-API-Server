FROM adoptopenjdk/openjdk11:latest
MAINTAINER simulacrumus
COPY ./*.jar ./app.jar
ENTRYPOINT ["java","-jar","app.jar"]