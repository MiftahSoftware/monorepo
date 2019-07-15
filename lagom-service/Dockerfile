FROM openjdk:8-jdk-alpine

WORKDIR /lagom-app

COPY employee-details-impl/target/employee-details-impl-1.0-SNAPSHOT-standalone-bundle.zip .

RUN unzip employee-details-impl-1.0-SNAPSHOT-standalone-bundle.zip && rm employee-details-impl-1.0-SNAPSHOT-standalone-bundle.zip

CMD ./employee-details-impl-1.0-SNAPSHOT/bin/employee-details-impl

