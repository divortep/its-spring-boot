FROM openjdk:12-alpine
COPY target/itsspringboot.jar app/itsapp.jar
EXPOSE 8080
CMD ["java", "-jar", "app/itsapp.jar"]