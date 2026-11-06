
# ✈️ Confort Travel Backend

API REST desarrollada en **Spring Boot 4** para la gestión interna de la agencia de viajes **Confort Travel**. Permite administrar clientes, ventas, tramos, reservas, check-ins, notificaciones, reportes y usuarios con autenticación JWT basada en roles.

![Java](https://img.shields.io/badge/Java-21-blue?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-green?logo=springboot)
![SQL Server](https://img.shields.io/badge/SQL%20Server-2022-red?logo=microsoftsqlserver)
![JWT](https://img.shields.io/badge/Auth-JWT-black?logo=jsonwebtokens)
![MapStruct](https://img.shields.io/badge/MapStruct-1.6.3-orange)
![License](https://img.shields.io/badge/license-Private-lightgrey)

---

## 📑 Tabla de Contenido

- [Características principales](#-características-principales)
- [Stack tecnológico](#-stack-tecnológico)
- [Arquitectura del proyecto](#-arquitectura-del-proyecto)
- [Modelo de dominio](#-modelo-de-dominio)
- [Requisitos previos](#-requisitos-previos)
- [Variables de entorno](#-variables-de-entorno)
- [Instalación y ejecución](#-instalación-y-ejecución)
- [Documentación de la API (Swagger)](#-documentación-de-la-api-swagger)
- [Endpoints principales](#-endpoints-principales)
- [Seguridad y roles](#-seguridad-y-roles)
- [Reglas de negocio destacadas](#-reglas-de-negocio-destacadas)
- [Scheduler de check-ins](#-scheduler-de-check-ins)
- [Testing](#-testing)
- [Despliegue con Docker](#-despliegue-con-docker)
- [Convenciones del proyecto](#-convenciones-del-proyecto)
- [Contacto](#-contacto)

---

## 🚀 Características principales

- **Autenticación y autorización** con JWT (Bearer Token) y Spring Security.
- **Control de acceso basado en roles** (`ADMIN` y `AGENTE`) con `@PreAuthorize`.
- **CRUD completo** de: Clientes, Ventas, Tramos, Reservas, Servicios Adicionales, Aerolíneas, Usuarios.
- **Gestión de check-ins** con estados (`PENDIENTE`, `NOTIFICADO`, `ATENDIDO`).
- **Scheduler automático** que notifica check-ins disponibles **48 h antes** de la salida del tramo.
- **Notificaciones internas** para el agente responsable.
- **Dashboard** con resumen de ventas, ganancias y estado de check-ins.
- **Reportes**: ventas por período, por agente, por aerolínea, ganancia por período y check-ins por estado.
- **Auditoría** completa de operaciones (`CREATE`, `UPDATE`, `DELETE`, `ATENDER`, etc.).
- **Optimistic Locking** (`@Version`) en entidades sensibles (Venta, Usuario).
- **Generación automática de códigos** (`CLI001`, `VNT001`, `TRM001`, `AER001`).
- **Manejo global de excepciones** con respuestas JSON estandarizadas.
- **Documentación OpenAPI/Swagger UI** integrada.
- **Health checks** con Spring Actuator (incluye un `HealthIndicator` personalizado).
- **Docker Compose** listo para SQL Server + Backend.

---

## 🧰 Stack tecnológico

| Categoría            | Tecnología                                |
| -------------------- | ----------------------------------------- |
| Lenguaje             | Java 21                                   |
| Framework            | Spring Boot 4.1.1                         |
| Persistencia         | Spring Data JPA + Hibernate               |
| Base de datos (prod) | SQL Server 2022                           |
| Base de datos (test) | H2 (modo MSSQLServer)                     |
| Seguridad            | Spring Security + JWT (jjwt 0.12.6)       |
| Mapeo DTO ↔ Entity   | MapStruct 1.6.3 + Lombok                  |
| Documentación        | SpringDoc OpenAPI 3.0.0 (Swagger UI)      |
| Monitoreo            | Spring Boot Actuator                      |
| Contenedores         | Docker + Docker Compose                   |
| Build                | Maven (mvnw / mvnw.cmd)                   |
| Testing              | JUnit 5 + Mockito + Spring Boot Test      |

---

## 🏗️ Arquitectura del proyecto

El proyecto sigue una **arquitectura por módulos funcionales** (feature-based / vertical slice), con capas internas bien definidas:

```
src/main/java/com/conforttravel/api/
├── aerolinea/         # Gestión de aerolíneas
├── auditoria/         # Logs de auditoría
├── auth/              # Autenticación y seguridad (JWT)
├── checkin/           # Seguimiento de check-in + notificaciones
├── cliente/           # Gestión de clientes
├── config/            # Configuración global (CORS, Jackson, OpenAPI, etc.)
├── dashboard/         # Resumen y próximos viajes
├── notificacion/      # Notificaciones internas
├── reportes/          # Reportes y estadísticas
├── reserva/           # Reservas por tramo
├── scheduler/         # Scheduler de check-in
├── servicio/          # Servicios adicionales
├── shared/            # Código transversal (DTOs, excepciones, utilidades)
├── tramo/             # Tramos (ida / retorno)
├── usuario/           # Gestión de usuarios
├── venta/             # Ventas + servicios asociados
└── ConfortTravelBackendApplication.java
```

Cada módulo contiene, cuando aplica:

```
<modulo>/
├── controller/        # Endpoints REST
├── dto/               # Request / Response / Filter (records)
├── entity/            # Entidades JPA
├── mapper/            # MapStruct mappers
├── repository/        # Spring Data JPA repositories
├── service/           # Interfaces de servicio
│   └── impl/          # Implementaciones
└── specification/     # Specifications para búsquedas dinámicas
```

---

## 🧩 Modelo de dominio

```
Usuario ──── agente ──────────┐
▼
Cliente ────────────────► Venta ──► Tramo ──► Reserva ──► Aerolinea
│        │
│        └──► CheckIn ──► Notificacion
│
└──► VentaServicio ──► ServicioAdicional
```

**Estados del Check-In:** `PENDIENTE → NOTIFICADO → ATENDIDO`

**Roles:** `ADMIN`, `AGENTE`

---

## ✅ Requisitos previos

- **Java 21**
- **Maven 3.9+** (o usar el wrapper `mvnw` incluido)
- **Docker** y **Docker Compose** (recomendado para SQL Server)
- **SQL Server 2022** (si ejecutas local sin Docker)

---

## 🔐 Variables de entorno

El proyecto incluye un archivo `.env.example`. Copia ese archivo a `.env` y completa los valores:

```bash
cp .env.example .env
```

Variables requeridas:

| Variable                 | Descripción                                                                |
| ------------------------ | -------------------------------------------------------------------------- |
| `MSSQL_SA_PASSWORD`      | Contraseña del usuario `sa` de SQL Server (mín. 8 caracteres, con símbolos) |
| `DB_URL`                 | URL JDBC de conexión                                                       |
| `DB_USERNAME`            | Usuario de la base de datos                                                |
| `DB_PASSWORD`            | Contraseña de la base de datos                                             |
| `JWT_SECRET`             | Clave secreta para firmar JWT (mín. **32 caracteres**)                     |
| `JWT_EXPIRATION`         | Tiempo de expiración del token en milisegundos (ej. `86400000` = 24 h)     |
| `INITIAL_ADMIN_EMAIL`    | Email del admin inicial (solo perfil `dev`)                                |
| `INITIAL_ADMIN_PASSWORD` | Contraseña del admin inicial (solo perfil `dev`)                           |

**Ejemplo de `.env`:**

```env
MSSQL_SA_PASSWORD=Admin123*
DB_URL=jdbc:sqlserver://localhost:1433;databaseName=confort_travel;encrypt=true;trustServerCertificate=true;
DB_USERNAME=sa
DB_PASSWORD=Admin123*
JWT_SECRET=tu-clave-secreta-super-segura-de-al-menos-32-caracteres
JWT_EXPIRATION=86400000
INITIAL_ADMIN_EMAIL=admin@conforttravel.com
INITIAL_ADMIN_PASSWORD=Admin123*
```

> ⚠️ **Nunca** subas tu `.env` real al repositorio. Ya está excluido en `.gitignore`.

---

## ⚙️ Instalación y ejecución

### Opción 1 · Docker Compose (recomendado)

Levanta **SQL Server + Backend** en un solo comando:

```bash
# 1. Clonar el repositorio
git clone <url-del-repositorio>
cd confort-travel-backend

# 2. Configurar .env
cp .env.example .env
# editar .env con tus credenciales

# 3. Levantar servicios
docker compose up -d --build

# 4. Ver logs
docker compose logs -f backend
```

La API quedará disponible en `http://localhost:8080`.

### Opción 2 · Ejecución local (solo SQL Server en Docker)

```bash
# 1. Levantar sólo SQL Server
docker compose up -d sqlserver

# 2. Ejecutar el backend con Maven
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

En Windows:

```cmd
mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
```

### Opción 3 · JAR empaquetado

```bash
./mvnw clean package -DskipTests
java -jar target/confort-travel-backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

> 🧠 En el perfil `dev`, `DataInitializer` crea automáticamente un usuario **ADMIN** si no existe.
> Credenciales por defecto: `admin@conforttravel.com` / `Admin123*` (configurables con `INITIAL_ADMIN_EMAIL` y `INITIAL_ADMIN_PASSWORD`).

---

## 📖 Documentación de la API (Swagger)

Una vez la aplicación esté arriba:

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8080/v3/api-docs

Incluye el esquema de seguridad `bearerAuth` para autorizar peticiones directamente desde Swagger.

---

## 📡 Endpoints principales

### 🔑 Autenticación

| Método | Endpoint          | Descripción                  | Rol   |
| ------ | ----------------- | ---------------------------- | ----- |
| POST   | `/api/auth/login` | Iniciar sesión y obtener JWT | Público |

**Request:**
```json
{ "email": "agente@conforttravel.com", "password": "password123" }
```

**Response:**
```json
{
  "token": "eyJhbGciOi...",
  "type": "Bearer",
  "userId": 1,
  "nombre": "Juan",
  "apellido": "Perez",
  "email": "agente@conforttravel.com",
  "rol": "AGENTE"
}
```

### 👥 Clientes

| Método | Endpoint              | Descripción                  |
| ------ | --------------------- | ---------------------------- |
| GET    | `/api/clientes`       | Listar todos                 |
| GET    | `/api/clientes/{id}`  | Obtener por ID               |
| GET    | `/api/clientes/page`  | Búsqueda paginada y filtrada |
| POST   | `/api/clientes`       | Crear (solo AGENTE)          |
| PUT    | `/api/clientes/{id}`  | Actualizar (solo AGENTE)     |
| DELETE | `/api/clientes/{id}`  | Eliminar (solo AGENTE)       |

### 💰 Ventas

| Método | Endpoint             | Descripción            |
| ------ | -------------------- | ---------------------- |
| GET    | `/api/ventas`        | Listar todas           |
| GET    | `/api/ventas/{id}`   | Obtener por ID         |
| GET    | `/api/ventas/page`   | Filtros + paginación   |
| POST   | `/api/ventas`        | Crear venta            |
| PUT    | `/api/ventas/{id}`   | Actualizar (con `version`) |
| DELETE | `/api/ventas/{id}`   | Eliminar               |

### 🧾 Servicios de Venta

| Método | Endpoint                                              | Descripción               |
| ------ | ----------------------------------------------------- | ------------------------- |
| GET    | `/api/venta-servicios`                                | Listar todas las relaciones |
| GET    | `/api/venta-servicios/{ventaId}/{servicioAdicionalId}`| Obtener por ID compuesto  |
| GET    | `/api/venta-servicios/venta/{ventaId}`                | Servicios de una venta    |
| POST   | `/api/venta-servicios`                                | Agregar servicio a venta  |
| DELETE | `/api/venta-servicios/{ventaId}/{servicioAdicionalId}`| Quitar servicio           |

### 🛫 Tramos

| Método | Endpoint                        | Descripción                |
| ------ | ------------------------------- | -------------------------- |
| GET    | `/api/tramos`                   | Listar todos               |
| GET    | `/api/tramos/{id}`              | Obtener por ID             |
| GET    | `/api/tramos/venta/{ventaId}`   | Tramos de una venta        |
| POST   | `/api/tramos`                   | Crear (auto-genera CheckIn)|
| PUT    | `/api/tramos/{id}`              | Actualizar                 |
| DELETE | `/api/tramos/{id}`              | Eliminar                   |

### 📌 Reservas

| Método | Endpoint                          | Descripción               |
| ------ | --------------------------------- | ------------------------- |
| GET    | `/api/reservas`                   | Listar todas              |
| GET    | `/api/reservas/{id}`              | Obtener por ID            |
| GET    | `/api/reservas/tramo/{tramoId}`   | Reserva de un tramo       |
| POST   | `/api/reservas`                   | Crear reserva             |
| PUT    | `/api/reservas/{id}`              | Actualizar                |
| DELETE | `/api/reservas/{id}`              | Eliminar                  |

### ✅ Check-Ins

| Método | Endpoint                            | Descripción                       |
| ------ | ----------------------------------- | --------------------------------- |
| GET    | `/api/checkins`                     | Listar todos                      |
| GET    | `/api/checkins/{id}`                | Obtener por ID                    |
| GET    | `/api/checkins/tramo/{tramoId}`     | Check-in del tramo                |
| GET    | `/api/checkins/estado/{estado}`     | Listar por estado                 |
| GET    | `/api/checkins/page`                | Paginado con prioridad (NOTIFICADO primero) |
| PATCH  | `/api/checkins/{id}/atender`        | Marcar como ATENDIDO              |

### 🔔 Notificaciones

| Método | Endpoint                             | Descripción                    |
| ------ | ------------------------------------ | ------------------------------ |
| GET    | `/api/notificaciones`                | Listar todas                   |
| GET    | `/api/notificaciones/{id}`           | Obtener por ID                 |
| GET    | `/api/notificaciones/no-leidas`      | Sólo las no leídas             |
| PATCH  | `/api/notificaciones/{id}/leer`      | Marcar como leída              |

### ✈️ Aerolíneas y Servicios Adicionales

| Método | Endpoint                  | Descripción                |
| ------ | ------------------------- | -------------------------- |
| CRUD   | `/api/aerolineas/**`      | Gestión de aerolíneas      |
| CRUD   | `/api/servicios/**`       | Gestión de servicios extra |

### 👤 Usuarios (solo `ADMIN`)

| Método | Endpoint                        | Descripción                       |
| ------ | ------------------------------- | --------------------------------- |
| GET    | `/api/usuarios`                 | Listar todos                      |
| GET    | `/api/usuarios/{id}`            | Obtener por ID                    |
| GET    | `/api/usuarios/page`            | Búsqueda paginada                 |
| POST   | `/api/usuarios`                 | Crear usuario                     |
| PUT    | `/api/usuarios/{id}`            | Actualizar (con `version`)        |
| PATCH  | `/api/usuarios/{id}/password`   | Cambiar contraseña                |
| PATCH  | `/api/usuarios/{id}/estado`     | Activar / desactivar              |
| DELETE | `/api/usuarios/{id}`            | Eliminar                          |

### 📊 Dashboard

| Método | Endpoint                         | Descripción                     |
| ------ | -------------------------------- | ------------------------------- |
| GET    | `/api/dashboard/resumen`         | KPIs generales                  |
| GET    | `/api/dashboard/proximos-viajes` | Top 5 próximos tramos           |

### 📈 Reportes

| Método | Endpoint                                   | Descripción                    |
| ------ | ------------------------------------------ | ------------------------------ |
| GET    | `/api/reportes/ventas-por-periodo`         | Ventas por rango de fechas     |
| GET    | `/api/reportes/ventas-por-agente`          | Ranking por agente             |
| GET    | `/api/reportes/ventas-por-aerolinea`       | Ranking por aerolínea          |
| GET    | `/api/reportes/ganancia-por-periodo`       | Ganancia total en un período   |
| GET    | `/api/reportes/check-ins/pendientes`       | Check-ins en estado `PENDIENTE`|
| GET    | `/api/reportes/check-ins/notificados`      | Check-ins en estado `NOTIFICADO`|
| GET    | `/api/reportes/check-ins/atendidos`        | Check-ins en estado `ATENDIDO` |

> Todos los reportes reciben `fechaDesde` y `fechaHasta` como `LocalDate` (`YYYY-MM-DD`).

### 🗂️ Auditoría (solo `ADMIN`)

| Método | Endpoint               | Descripción                         |
| ------ | ---------------------- | ----------------------------------- |
| GET    | `/api/auditoria/page`  | Búsqueda paginada de logs           |

### 🩺 Actuator

| Endpoint                    | Descripción                       |
| --------------------------- | --------------------------------- |
| `/actuator/health`          | Estado general + integraciones    |
| `/actuator/info`            | Información de la aplicación      |
| `/actuator/metrics`         | Métricas (solo `dev`)             |
| `/actuator/mappings`        | Mapeo de endpoints (solo `dev`)   |

---

## 🔐 Seguridad y roles

### Matriz de autorización

| Recurso / Acción                        | Público | AGENTE | ADMIN |
| --------------------------------------- | :-----: | :----: | :---: |
| `POST /api/auth/login`                  |    ✅   |   ✅   |  ✅   |
| Swagger / Actuator health / info        |    ✅   |   ✅   |  ✅   |
| CRUD de recursos operativos (GET)       |    ❌   |   ✅   |  ✅   |
| CRUD de recursos operativos (POST/PUT/PATCH/DELETE) | ❌ | ✅ | ❌ |
| `/api/usuarios/**`                      |    ❌   |   ❌   |  ✅   |
| `GET /api/auditoria/**`                 |    ❌   |   ❌   |  ✅   |
| `/api/dashboard/**`, `/api/reportes/**` |    ❌   |   ✅   |  ✅   |

### Flujo de autenticación

1. El cliente envía `POST /api/auth/login` con email y contraseña.
2. El backend valida con `AuthenticationManager` + `BCryptPasswordEncoder`.
3. Se genera un **JWT** con claims (`roles`, `sub`, `iat`, `exp`).
4. En cada petición el cliente envía el header:

   ```http
   Authorization: Bearer <token>
   ```

5. `JwtAuthenticationFilter` valida el token y coloca la autenticación en el `SecurityContext`.
6. `JwtAuthenticationEntryPoint` y `JwtAccessDeniedHandler` devuelven `401` / `403` en formato JSON estandarizado.

### Manejo de errores estandarizado

Todas las excepciones se manejan centralizadamente en `GlobalExceptionHandler` y devuelven:

```json
{
  "timestamp": "2026-01-10T12:34:56Z",
  "status": 404,
  "code": "RESOURCE_NOT_FOUND",
  "message": "Cliente no encontrado con id: 99",
  "path": "/api/clientes/99"
}
```

---

## 📋 Reglas de negocio destacadas

- 🔢 **Generación de códigos secuenciales** (`CLI001`, `VNT001`, `TRM001`, `AER001`).
- 💵 **El precio final** de una venta **no puede ser menor** que el precio inicial.
- 👤 **El usuario asignado a una venta** debe tener rol `AGENTE` y estar activo.
- 🔗 **Un tramo sólo puede tener una reserva** (unique constraint).
- 🔗 **Un tramo sólo puede tener un check-in** (unique constraint).
- 📅 **Fecha de llegada** del tramo debe ser **posterior** a la de salida.
- 🕒 **Regla IDA/RETORNO**: la salida del `RETORNO` no puede ser anterior a la `IDA` y viceversa.
- ⏰ **La fecha/hora de salida** de un tramo nuevo no puede ser en el pasado (`@FutureOrPresent`).
- ✅ **El check-in** sólo puede marcarse como `ATENDIDO` si está en estado `NOTIFICADO`.
- 🛎️ **Un check-in se calcula** como `fechaHoraSalida − 48 horas`.
- 🚫 **No se puede agregar un servicio adicional inactivo** a una venta.
- 🔁 **No se puede duplicar el mismo servicio** en la misma venta.
- 🔒 **Optimistic locking** en `Usuario` y `Venta` (requiere enviar `version` en updates).

---

## ⏰ Scheduler de Check-ins

El proyecto incluye `CheckInScheduler` que se ejecuta de forma periódica y notifica automáticamente los check-ins disponibles al agente responsable.

- Configurable en `application-dev.yaml`:

```yaml
confort:
  travel:
    scheduler:
      check-in:
        fixed-delay-ms: 10000  # 10 segundos en dev
```

- En producción se recomienda **60000 ms** (1 minuto) o mayor.

**Flujo del scheduler:**

```
1. Busca CheckIns en estado PENDIENTE con fechaDisponible <= ahora
2. Para cada uno:
   ├── Crea una Notificacion (tipo = CHECK_IN_DISPONIBLE)
   ├── Cambia estado a NOTIFICADO
   └── Registra fechaNotificacion
3. Asigna la notificación al usuario AGENTE responsable de la venta
```

---

## 🧪 Testing

El proyecto incluye pruebas unitarias y de contexto con **JUnit 5 + Mockito + H2**.

Ejecutar:

```bash
./mvnw test
```

Configuración en `src/test/resources/application-test.yaml` (H2 en memoria con modo MSSQLServer).

**Tests existentes:**

- `AuthServiceImplTest` — cubre login exitoso y credenciales inválidas.
- `ConfortTravelBackendApplicationTests` — verifica que el contexto levante correctamente.

---

## 🐳 Despliegue con Docker

El `docker-compose.yml` está configurado para:

- **`sqlserver`** — SQL Server 2022 Developer con volumen persistente.
- **`backend`** — Spring Boot en perfil `dev`, conectado a `sqlserver` por red interna.

```bash
# Levantar todo
docker compose up -d --build

# Detener sin perder datos
docker compose down

# Detener y eliminar volúmenes (⚠️ borra la BD)
docker compose down -v
```

Endpoints desde el host:

| Servicio    | URL                                              |
| ----------- | ------------------------------------------------ |
| API REST    | http://localhost:8080                            |
| Swagger UI  | http://localhost:8080/swagger-ui.html            |
| Health      | http://localhost:8080/actuator/health            |
| SQL Server  | `localhost:1433` (usuario `sa`)                  |

---

## 🧭 Convenciones del proyecto

- **DTOs como `record`** inmutables (Request / Response / Filter).
- **MapStruct** con `componentModel = "spring"` para mapeo entre capas.
- **`unmappedTargetPolicy = ERROR`** — no se permite dejar campos sin mapear (protege contra regresiones silenciosas).
- **`Specification` + Builder** para todos los filtros dinámicos (`findAll(spec, pageable)`).
- **`PageResponse<T>`** unificado para respuestas paginadas.
- **`@Transactional(readOnly = true)`** a nivel de clase en servicios; `@Transactional` sólo en métodos de escritura.
- **Auditoría** invocada desde cada operación de mutación (create/update/delete).
- **`@PreAuthorize`** a nivel de controlador para recursos administrativos.
- **Códigos HTTP correctos**: `201 Created` para POST, `204 No Content` para DELETE, `409 Conflict` para violaciones de negocio, `422 Unprocessable Entity` para errores de regla.
- **`OffsetDateTime.now(ZoneOffset.UTC)`** en todos los timestamps (coherente con SQL Server y Jackson en UTC).

---

## 👥 Autores
Erick Torres - Desarrollo inicial - ETC Tech
## 📄 Licencia
Este proyecto está bajo la Licencia Apache 2.0 - ver el archivo LICENSE para más detalles.

## 🙏 Agradecimientos
Spring Boot por su excelente framework
La comunidad de desarrolladores por su soporte
## 📞 Contacto
Para cualquier consulta o sugerencia, por favor contacta a:

Email: etorresca8@gmail.com
GitHub: https://github.com/E-TorresC