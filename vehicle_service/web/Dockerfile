FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["mvn","clean","package","-DskipTests"]
ENTRYPOINT ["java","-Xmx512m","-Dserver.port=8080","-jar","/app.jar"]