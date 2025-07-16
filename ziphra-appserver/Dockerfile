# Usa Java 22 como base (porque estás usando OpenJDK 22.0.2)
FROM eclipse-temurin:22-jdk

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el JAR compilado (ajustado al nombre real del archivo)
COPY target/ziphra-appserver-2.3.2-SNAPSHOT.jar app.jar

# Expone el puerto en el que corre la app (ajustalo si no es 8080)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]