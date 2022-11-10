FROM openjdk:18
ADD target/tpAchatProject-1.0.jar tpAchatProject
EXPOSE 8089:8089
ENTRYPOINT ["java","-jar","tpAchatProject"]