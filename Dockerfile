FROM adoptopenjdk/openjdk8:latest
COPY controller-1.0-SNAPSHOT.jar /home
CMD java -jar /home/controller-1.0-SNAPSHOT.jar