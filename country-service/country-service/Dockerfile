FROM openjdk:21-jdk
VOLUME /tmp
COPY target/*.war app.war
ENTRYPOINT ["java", "-jar", "/app.war"]
