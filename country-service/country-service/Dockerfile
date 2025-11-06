FROM openjdk:21-jdk-oracle
VOLUME /tmp
COPY target/*.war app.war
ENTRYPOINT ["java", "-jar", "/app.war"]
