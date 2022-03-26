FROM adoptopenjdk/openjdk11:latest
WORKDIR usr/src

ADD ./target/TrackService-0.0.1-SNAPSHOT.jar /usr/src/TrackService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","TrackService-0.0.1-SNAPSHOT.jar"]
EXPOSE 8087