FROM openjdk:17
VOLUME /tmp
EXPOSE 8088
ARG JAR_FILE=target/booking-0.0.1.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]