FROM openjdk:11
VOLUME /tmp
COPY target/semantic-annotator-1.0.0-SNAPSHOT.jar semantic-annotator.jar
ENTRYPOINT ["java","-jar","/semantic-annotator.jar"]
