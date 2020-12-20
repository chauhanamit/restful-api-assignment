FROM openjdk:11
ADD target/RestFul-API.jar RestFul-API.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","RestFul-API.jar"]