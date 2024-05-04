# Stage 1: build app
FROM gradle:8.7-alpine AS BUILDER
WORKDIR /usr/app/
COPY build.gradle settings.gradle ./
COPY src ./src
RUN gradle build --no-daemon -x test

# Stage 2: entrypoint
FROM amazoncorretto:17-alpine
ENV JAR_FILE=app-jpa-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app/

WORKDIR $APP_HOME

COPY --from=BUILDER $APP_HOME/build/libs/$JAR_FILE .

EXPOSE 9000

ENTRYPOINT java -jar $JAR_FILE
