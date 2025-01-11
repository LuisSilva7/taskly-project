FROM maven:3.8.7-openjdk-18 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM amazoncorretto:17
ARG APP_VERSION=1.0.0

WORKDIR /app
COPY --from=build /build/target/taskly-backend-*.jar /app/

EXPOSE 8888

ENV DB_URL=jdbc:postgresql://postgresql_taskly:5432/taskly_db
ENV JAR_VERSION=${APP_VERSION}

CMD java -jar -Dspring.datasource.url=${DB_URL} taskly-backend-${JAR_VERSION}.jar