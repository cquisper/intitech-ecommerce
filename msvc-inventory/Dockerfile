ARG MSVC_NAME=msvc-inventory

FROM amazoncorretto:17.0.4-alpine as builder

ARG MSVC_NAME

WORKDIR /app/${MSVC_NAME}

COPY ./pom.xml /app

COPY ./${MSVC_NAME}/.mvn ./.mvn

COPY ./${MSVC_NAME}/mvnw .

COPY ./${MSVC_NAME}/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline
COPY ./${MSVC_NAME}/src ./src

RUN ./mvnw clean package -DskipTests

FROM amazoncorretto:17.0.4-alpine

ARG MSVC_NAME

WORKDIR /app

ARG TARGET_FOLDER=/app/$MSVC_NAME/target

COPY --from=builder $TARGET_FOLDER/msvc-inventory-0.0.1-SNAPSHOT.jar .

ARG PORT_APP=8082

ENV PORT $PORT_APP

EXPOSE $PORT

CMD ["java", "-jar", "msvc-inventory-0.0.1-SNAPSHOT.jar"]