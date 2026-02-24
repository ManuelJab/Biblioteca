# Etapa de construccion
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Primero copiamos el pom.xml para descargar las dependencias
# Esto permite que Docker cachee las capas de dependencias si el pom.xml no ha cambiado
COPY pom.xml .
RUN mvn dependency:go-offline

# Ahora copiamos el codigo fuente y construimos el proyecto
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa de produccion (ejecucion)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiamos el archivo JAR generado en la etapa de construccion
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto de Spring Boot (Render lo detectara o usara el que se especifique)
EXPOSE 8080

# Comando para iniciar la aplicacion
ENTRYPOINT ["java", "-jar", "app.jar"]
