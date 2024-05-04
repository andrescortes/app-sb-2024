FROM gradle:8.7-alpine AS BUILDER
WORKDIR /usr/app/
COPY build.gradle settings.gradle ./
COPY src ./src
RUN gradle build --no-daemon -x test

FROM amazoncorretto:17-alpine

ENV APP_HOME=/usr/app/
ENV JAR_NAME=app-jpa-0.0.1-SNAPSHOT.jar

WORKDIR $APP_HOME

COPY --from=BUILDER $APP_HOME/build/libs/$JAR_NAME .

EXPOSE 9000

ENTRYPOINT exec java -jar $JAR_NAME