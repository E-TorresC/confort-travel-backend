# Usamos la imagen oficial y ligera de Java 21
FROM eclipse-temurin:21-jre-alpine

# Definimos un volumen temporal para Tomcat
VOLUME /tmp

# Declaramos el puerto que expone la aplicación
EXPOSE 8080

# Copiamos el archivo JAR compilado al contenedor
ARG JAR_FILE=target/confort-travel-backend-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]