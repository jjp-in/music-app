FROM adoptopenjdk/openjdk11:latest
WORKDIR usr/src
ENV MYSQL_DATABASE=userDB
ENV MYSQL_USER=testuser
ENV MYSQL_PASSWORD=root
ENV MYSQL_URL=jdbc:mysql://localhost:3306/userDB
ADD ./target/UserService-0.0.1-SNAPSHOT.jar /usr/src/UserService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","UserService-0.0.1-SNAPSHOT.jar"]
EXPOSE 8083
