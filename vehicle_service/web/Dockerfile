FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 80
ENTRYPOINT ["mvn","clean","package","-DskipTests"]
ENTRYPOINT ["java","-Xmx512m","-Dserver.port=80","-jar","/app.jar"]