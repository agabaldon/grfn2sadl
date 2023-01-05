# GraSEN Semantic Analysis

FROM openjdk:11
MAINTAINER Alfredo Gabaldon <alfredo.gabaldon@ge.com>
VOLUME /tmp
COPY target/semantic-analysis-1.0.1-SNAPSHOT.jar semantic-analysis.jar
ENTRYPOINT ["java","-jar","/semantic-analysis.jar"]
