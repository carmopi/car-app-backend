FROM openjdk:8-jre
COPY startup.sh startup.sh
COPY car-app-1.0.0-SNAPSHOT.war car-app-1.0.0-SNAPSHOT.war
COPY domain.xml domain.xml
COPY activemq-rar-5.16.2.rar activemq-rar-5.16.2.rar
COPY payara-micro-5.2021.3.jar payara-micro-5.2021.3.jar
ADD data.sql /docker-entrypoint-initdb.d
EXPOSE 61616
EXPOSE 8081
CMD ["/bin/sh", "startup.sh"]