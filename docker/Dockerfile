FROM openjdk:17.0.1-buster

ENV APP_HOME /usr/src/app

ARG JAR_FILE=payment-0.0.1-SNAPSHOT.jar

COPY target/${JAR_FILE} $APP_HOME/app.jar

WORKDIR $APP_HOME

ENTRYPOINT ["java","-jar","/app.jar"]
