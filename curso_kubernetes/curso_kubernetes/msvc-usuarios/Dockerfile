ARG MSVC_NAME=msvc-usuarios

FROM openjdk:11 as builder

ARG MSCV_NAME=msvc-usuarios

WORKDIR /app/$MSCV_NAME

COPY ./pom.xml /app
COPY ./$MSCV_NAME/.mvn ./.mvn
COPY ./$MSCV_NAME/mvnw .
COPY ./$MSCV_NAME/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
COPY ./$MSCV_NAME/src ./src

RUN ./mvnw clean package -DskipTests

#A partir de una imagen a partir de otra
FROM openjdk:11

ARG MSVC_NAME

WORKDIR /app
RUN mkdir ./logs
ARG TARGET_FOLDER=/app/$MSVC_NAME/target
COPY --from=builder $TARGET_FOLDER/msvc-usuarios-0.0.1-SNAPSHOT.jar .
ARG PORT_APP=8001
#Puerto interno
ENV PORT $PORT_APP
EXPOSE $PORT

CMD ["java", "-jar", "msvc-usuarios-0.0.1-SNAPSHOT.jar"]