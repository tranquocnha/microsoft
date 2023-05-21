# base image: linux alpine os with open jdk 8
FROM openjdk:8-jdk-alpine
# copy jar from local into docker image
COPY target/microservices-0.0.1-SNAPSHOT.jar microservices-0.0.1-SNAPSHOT.jar
# command line to run jar
ENTRYPOINT ["java", "-jar","/microservices-0.0.1-SNAPSHOT.jar"]