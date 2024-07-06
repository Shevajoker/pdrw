FROM openjdk:21
LABEL authors="AleksandrSh"
ARG JAR_FILE=target/*.jar
COPY $JAR_FILE app.jar

ENTRYPOINT ["java","-jar","/app.jar"]