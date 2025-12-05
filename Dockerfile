# ----------- STAGE 1: BUILD ----------------
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Instalar Maven
RUN apt-get update && apt-get install -y maven

# Copiamos solo los POMs para caché
COPY pom.xml .

COPY application/pom.xml application/pom.xml
COPY domain/pom.xml domain/pom.xml
COPY infrastructure/driven-adapters/repository/pom.xml infrastructure/driven-adapters/repository/pom.xml
COPY infrastructure/entry-points/api-rest/pom.xml infrastructure/entry-points/api-rest/pom.xml

# Descargar dependencias (cache)
RUN mvn -B -e -ntp dependency:go-offline

# Copiar el código completo
COPY . .

# Build multi-module
RUN mvn -B -e -ntp clean package -DskipTests

# ----------- STAGE 2: RUN -----------------
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copiar el JAR del módulo ejecutable
COPY --from=build /app/application/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
