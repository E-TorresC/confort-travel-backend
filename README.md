# CONFORT TRAVEL — Backend

API REST para el sistema interno de gestión y seguimiento
de ventas y viajes de CONFORT TRAVEL.

## Stack

- Java 21
- Spring Boot 4.1.1
- Maven
- Spring Web MVC
- Jakarta Bean Validation
- Lombok
- Spring Boot Actuator

## Ejecución

mvn spring-boot:run

## Testing

mvn test

## SQL Server

El proyecto utiliza SQL Server 2022 para persistencia.

### Desarrollo

Levantar SQL Server:

```bash
docker compose up -d sqlserver
```
Detener SQL Server:

docker compose down

Ver logs:

docker compose logs -f sqlserver

La configuración de conexión se obtiene mediante variables de entorno.


---

# 49. Probar reinicio del contenedor

Detén:

```bash
docker compose down
```

Luego:

docker compose up -d sqlserver

Comprueba que SQL Server vuelva a estar disponible.

Gracias al volumen:

volumes:
  sqlserver_data:

los datos del contenedor podrán persistir entre recreaciones normales.