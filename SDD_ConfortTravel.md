# Documento de Diseño de Software — CONFORT TRAVEL

**Nombre del proyecto:** CONFORT TRAVEL — Sistema de Gestión y Seguimiento de Ventas y Viajes  
**Backend:** `confort-travel-backend`  
**Frontend:** `confort-travel-front`  
**Versión del documento:** 1.0  
**Estado:** Diseño inicial aprobado  
**Fecha:** 2026-09-17

---

# 1. Introducción

CONFORT TRAVEL es una agencia de viajes que comercializa pasajes aéreos nacionales e internacionales.

Actualmente, el proceso de búsqueda, reserva y compra de pasajes se realiza mediante plataformas externas como AGIL y las plataformas de las diferentes aerolíneas. Una vez realizada la compra, la información de la operación es registrada manualmente en archivos Excel utilizados por la agencia como mecanismo de control operativo.

El presente proyecto propone desarrollar una plataforma web interna que permita centralizar y estructurar esta información, facilitar la gestión de ventas y viajes, y automatizar el seguimiento de las operaciones relacionadas con el check-in.

La plataforma **no reemplazará las plataformas externas de las aerolíneas ni AGIL** y tampoco realizará automáticamente el proceso de check-in.

---

# 2. Problemática

CONFORT TRAVEL realiza la venta y adquisición de pasajes mediante plataformas externas como AGIL y los sistemas de las aerolíneas. Una vez realizada la compra, la información de la operación es registrada manualmente en archivos Excel que funcionan como mecanismo de control de clientes, ventas y viajes.

Estos registros contienen información relacionada con diferentes aspectos de una misma operación, como el cliente principal, cantidad y composición de pasajeros, fechas de viaje, rutas, códigos de reserva, aerolíneas, servicios adicionales, precios y observaciones.

Sin embargo, esta información se mantiene principalmente como registros tabulares, lo que dificulta representar explícitamente las relaciones existentes entre clientes, ventas, viajes, tramos, reservas y aerolíneas. Asimismo, dificulta realizar consultas, mantener historial y efectuar un seguimiento estructurado de las operaciones posteriores a la venta.

Adicionalmente, la agencia debe identificar oportunamente cuándo corresponde realizar el check-in de cada viaje. Esta actividad depende de reglas diferentes según el tipo de viaje y la aerolínea, por lo que el seguimiento manual de las fechas genera una carga operativa para los agentes.

Por ello, se requiere una plataforma interna que centralice la información de las ventas realizadas externamente, estructure la relación entre clientes, ventas, viajes y reservas, facilite el seguimiento de las operaciones y genere avisos cuando corresponda realizar el check-in.

---

# 3. Pregunta del problema

> ¿Cómo mejorar la gestión y el seguimiento de las ventas de pasajes realizadas por CONFORT TRAVEL mediante plataformas externas, reduciendo la dependencia de procesos manuales y facilitando la identificación oportuna de las operaciones que requieren atención respecto al check-in?

---

# 4. Propuesta de solución

Desarrollar una plataforma web interna para CONFORT TRAVEL que permita:

- Gestionar usuarios y agentes.
- Registrar clientes.
- Registrar ventas realizadas mediante plataformas externas.
- Registrar la composición de pasajeros.
- Registrar viajes.
- Registrar tramos de un viaje.
- Registrar aerolíneas.
- Registrar códigos de reserva.
- Registrar servicios adicionales.
- Calcular automáticamente la ganancia de cada venta.
- Determinar cuándo se encuentra disponible el check-in.
- Notificar al agente correspondiente.
- Gestionar el seguimiento mediante los estados:
  - `PENDIENTE`
  - `NOTIFICADO`
  - `ATENDIDO`
- Consultar próximas operaciones.
- Buscar y filtrar información.
- Consultar historial.
- Proporcionar indicadores operativos.
- Mantener trazabilidad mediante auditoría.

---

# 5. Objetivo general

Desarrollar una plataforma web interna para CONFORT TRAVEL que permita centralizar y gestionar la información de clientes, ventas, viajes, tramos, reservas y servicios asociados a los pasajes adquiridos mediante plataformas externas, incorporando mecanismos de seguimiento y notificación para las operaciones cuyo check-in se encuentre disponible.

---

# 6. Objetivos específicos

1. Centralizar en una única plataforma la información actualmente gestionada mediante archivos Excel.

2. Gestionar el cliente principal asociado a cada venta.

3. Registrar y consultar las ventas, incluyendo agente responsable, cantidad y composición de pasajeros, precios, servicios y observaciones.

4. Registrar viajes compuestos por uno o más tramos.

5. Permitir que los diferentes tramos de un mismo viaje estén asociados a diferentes aerolíneas.

6. Permitir que diferentes tramos tengan diferentes códigos de reserva.

7. Calcular automáticamente la ganancia de una venta a partir del precio inicial y precio final.

8. Automatizar el cálculo de la fecha y hora en que se encuentra disponible el check-in.

9. Generar avisos al agente responsable cuando el check-in se encuentre disponible.

10. Gestionar el seguimiento del check-in mediante los estados `PENDIENTE`, `NOTIFICADO` y `ATENDIDO`.

11. Facilitar la consulta, búsqueda, filtrado y paginación de la información.

12. Proporcionar indicadores operativos sobre ventas y seguimiento de check-in.

13. Mantener trazabilidad de las operaciones realizadas dentro de la plataforma.

---

# 7. Alcance

## 7.1. Alcance funcional

El sistema permitirá:

### Gestión de usuarios

- Inicio de sesión.
- Cierre de sesión.
- Gestión de agentes.
- Gestión de administradores.
- Activación y desactivación de usuarios.
- Asignación de rol.

### Gestión de clientes

- Registrar cliente.
- Editar cliente.
- Buscar cliente.
- Consultar historial de ventas del cliente.

### Gestión de ventas

- Registrar venta.
- Consultar venta.
- Editar venta.
- Anular venta.
- Buscar ventas.
- Filtrar ventas.
- Consultar ventas por agente.
- Registrar precio inicial.
- Registrar precio final.
- Calcular ganancia automáticamente.
- Registrar observaciones.

### Gestión de pasajeros

La plataforma almacenará la composición de pasajeros de manera agregada:

- Adultos.
- Infantes.

No se registrarán individualmente los acompañantes.

Ejemplos de representación:

```text
5
````

Para:

```text
5 adultos
```

y:

```text
4 ADT, 1 INF
```

Para:

```text
4 adultos + 1 infante
```

### Gestión de viajes

* Registrar viaje.
* Registrar uno o más tramos.
* Registrar origen.
* Registrar destino.
* Registrar fecha y hora de salida.
* Registrar fecha y hora de llegada cuando esté disponible.
* Diferenciar tramo de ida y retorno.
* Consultar próximos viajes.

### Gestión de reservas

* Registrar código de reserva.
* Asociar reserva a un tramo.
* Asociar reserva a una aerolínea.
* Consultar reserva.

### Gestión de aerolíneas

* Registrar aerolínea.
* Editar aerolínea.
* Activar/desactivar aerolínea.
* Consultar aerolíneas.

### Gestión de servicios adicionales

* Registrar servicio adicional.
* Asociar servicios a una venta.
* Consultar servicios asociados.

### Seguimiento de check-in

* Calcular disponibilidad del check-in.
* Detectar operaciones pendientes.
* Notificar al agente.
* Cambiar `PENDIENTE` a `NOTIFICADO`.
* Permitir que el agente cambie `NOTIFICADO` a `ATENDIDO`.
* Consultar operaciones pendientes.
* Consultar operaciones notificadas.
* Consultar operaciones atendidas.

### Dashboard

* Indicadores de ventas.
* Indicadores de ganancia.
* Próximos viajes.
* Check-ins pendientes.
* Check-ins notificados.
* Check-ins atendidos.

### Auditoría

* Registrar acciones relevantes realizadas dentro del sistema.
* Registrar usuario responsable.
* Registrar fecha y hora.
* Registrar entidad afectada.
* Registrar operación realizada.

---

# 8. Fuera del alcance

El sistema **no incluirá inicialmente**:

* Búsqueda de vuelos.
* Consulta de disponibilidad de vuelos.
* Consulta de tarifas de aerolíneas.
* Emisión de pasajes.
* Compra automática de pasajes.
* Integración inicial con AGIL.
* Integración inicial con LATAM.
* Integración inicial con SKY.
* Integración inicial con JetSMART.
* Integración inicial con otras aerolíneas.
* Check-in automático.
* Selección automática de asientos.
* Gestión de inventario de aerolíneas.
* CRM avanzado.
* Contabilidad completa.
* Facturación electrónica.
* Registro individual de acompañantes.
* Gestión de contacto con el cliente respecto al check-in.

El sistema funcionará como una **plataforma interna de gestión y seguimiento**, mientras que las operaciones de compra y check-in continuarán realizándose en las plataformas externas correspondientes.

---

# 9. Actores

## 9.1. Administrador

Representado por el rol:

```text
ADMIN
```

Responsabilidades:

* Gestionar usuarios.
* Gestionar catálogos.
* Consultar ventas.
* Consultar información operativa.
* Consultar indicadores.
* Consultar información de seguimiento.
* Acceder a funcionalidades administrativas.

---

## 9.2. Agente

Representado por el rol:

```text
AGENTE
```

Responsabilidades:

* Registrar clientes.
* Registrar ventas.
* Registrar viajes.
* Registrar tramos.
* Registrar reservas.
* Registrar servicios.
* Consultar operaciones.
* Recibir avisos de check-in.
* Realizar el check-in en la plataforma externa de la aerolínea.
* Marcar el seguimiento como `ATENDIDO`.

---

## 9.3. Sistemas externos

Los sistemas externos no serán actores autenticados directamente en la primera versión, pero forman parte del contexto del sistema:

* AGIL.
* Plataformas de aerolíneas.

Su función dentro del proceso es permitir al agente realizar las operaciones de búsqueda, reserva, compra y check-in.

---

# 10. Casos de uso

## 10.1. Autenticación

| Código | Caso de uso    | Actor          |
| ------ | -------------- | -------------- |
| CU01   | Iniciar sesión | ADMIN / AGENTE |
| CU02   | Cerrar sesión  | ADMIN / AGENTE |

---

## 10.2. Usuarios

| Código | Caso de uso                | Actor |
| ------ | -------------------------- | ----- |
| CU03   | Registrar usuario          | ADMIN |
| CU04   | Editar usuario             | ADMIN |
| CU05   | Activar/desactivar usuario | ADMIN |
| CU06   | Consultar usuarios         | ADMIN |

---

## 10.3. Clientes

| Código | Caso de uso                     | Actor          |
| ------ | ------------------------------- | -------------- |
| CU07   | Registrar cliente               | AGENTE         |
| CU08   | Editar cliente                  | AGENTE         |
| CU09   | Buscar cliente                  | AGENTE / ADMIN |
| CU10   | Consultar historial del cliente | AGENTE / ADMIN |

---

## 10.4. Ventas

| Código | Caso de uso                 | Actor          |
| ------ | --------------------------- | -------------- |
| CU11   | Registrar venta             | AGENTE         |
| CU12   | Consultar venta             | AGENTE / ADMIN |
| CU13   | Editar venta                | AGENTE         |
| CU14   | Anular venta                | AGENTE / ADMIN |
| CU15   | Buscar ventas               | AGENTE / ADMIN |
| CU16   | Consultar ventas por agente | ADMIN          |

---

## 10.5. Viajes

| Código | Caso de uso               | Actor          |
| ------ | ------------------------- | -------------- |
| CU17   | Registrar viaje           | AGENTE         |
| CU18   | Registrar tramo           | AGENTE         |
| CU19   | Consultar viaje           | AGENTE / ADMIN |
| CU20   | Consultar próximos viajes | AGENTE / ADMIN |

---

## 10.6. Reservas

| Código | Caso de uso             | Actor          |
| ------ | ----------------------- | -------------- |
| CU21   | Registrar reserva       | AGENTE         |
| CU22   | Consultar reserva       | AGENTE / ADMIN |
| CU23   | Asociar reserva a tramo | AGENTE         |

---

## 10.7. Servicios adicionales

| Código | Caso de uso                  | Actor          |
| ------ | ---------------------------- | -------------- |
| CU24   | Registrar servicio adicional | ADMIN          |
| CU25   | Asociar servicio a venta     | AGENTE         |
| CU26   | Consultar servicios          | AGENTE / ADMIN |

---

## 10.8. Check-in

| Código | Caso de uso                         | Actor          |
| ------ | ----------------------------------- | -------------- |
| CU27   | Calcular disponibilidad de check-in | SISTEMA        |
| CU28   | Consultar check-ins pendientes      | AGENTE / ADMIN |
| CU29   | Notificar agente                    | SISTEMA        |
| CU30   | Marcar check-in como atendido       | AGENTE         |

---

## 10.9. Dashboard

| Código | Caso de uso                       | Actor          |
| ------ | --------------------------------- | -------------- |
| CU31   | Consultar dashboard               | AGENTE / ADMIN |
| CU32   | Consultar indicadores de ventas   | ADMIN          |
| CU33   | Consultar indicadores de check-in | ADMIN          |

---

# 11. Reglas de negocio

## 11.1. Ventas

### RN-VENTA-01

Toda venta debe estar asociada a un cliente principal.

### RN-VENTA-02

Toda venta debe estar asociada a un agente responsable.

### RN-VENTA-03

Cada venta representa una única operación de compra.

### RN-VENTA-04

Cada venta está asociada a un único viaje.

### RN-VENTA-05

Un cliente puede tener múltiples ventas independientes.

### RN-VENTA-06

Una venta puede involucrar diferentes aerolíneas.

### RN-VENTA-07

Un viaje puede estar compuesto por uno o más tramos.

### RN-VENTA-08

Diferentes tramos de un mismo viaje pueden estar asociados a diferentes aerolíneas.

### RN-VENTA-09

Diferentes tramos pueden tener diferentes códigos de reserva.

### RN-VENTA-10

La ganancia se calcula como:

```text
ganancia = precioFinal - precioInicial
```

### RN-VENTA-11

La ganancia no será editable directamente por el usuario.

---

# 12. Reglas de pasajeros

### RN-PAS-01

El sistema registra únicamente los datos del cliente principal.

### RN-PAS-02

Los acompañantes no se registran individualmente.

### RN-PAS-03

La composición de pasajeros se registra mediante la cantidad de adultos e infantes.

### RN-PAS-04

La representación de pasajeros debe permitir casos como:

```text
5
```

y:

```text
4 ADT, 1 INF
```

### RN-PAS-05

No se incorporan categorías adicionales de pasajeros mientras la agencia no las defina.

---

# 13. Reglas de viajes

### RN-VIAJE-01

Una venta representa un único viaje.

### RN-VIAJE-02

Un viaje puede tener un único tramo de ida.

### RN-VIAJE-03

Un viaje puede tener ida y retorno.

### RN-VIAJE-04

Un viaje puede contener múltiples tramos cuando la operación lo requiera.

### RN-VIAJE-05

Un tramo representa un desplazamiento entre un origen y un destino.

### RN-VIAJE-06

La fecha/hora de retorno no puede ser anterior a la fecha/hora de ida cuando ambas correspondan al mismo viaje.

### RN-VIAJE-07

Una nueva compra independiente realizada por el mismo cliente debe registrarse como una nueva venta y un nuevo viaje.

Ejemplo:

```text
Cliente
└── Venta 001
    └── Viaje Lima → Cusco

Cliente
└── Venta 002
    └── Viaje Lima → Arequipa
```

---

# 14. Reglas de reservas y aerolíneas

### RN-RES-01

Una reserva debe estar asociada a un tramo.

### RN-RES-02

Una reserva debe identificar la aerolínea correspondiente.

### RN-RES-03

Una venta puede tener diferentes aerolíneas.

### RN-RES-04

Una venta puede tener diferentes códigos de reserva.

Ejemplo:

```text
Venta
└── Viaje
    ├── Tramo Lima → Madrid
    │   └── Reserva
    │       ├── Aerolínea: LATAM
    │       └── Código: ABC123
    │
    └── Tramo Madrid → Lima
        └── Reserva
            ├── Aerolínea: IBERIA
            └── Código: XYZ789
```

---

# 15. Reglas de check-in

## 15.1. Disponibilidad

### RN-CHECKIN-01

Cada tramo que requiera seguimiento de check-in debe tener un registro de seguimiento.

### RN-CHECKIN-02

Para vuelos internacionales, el check-in se considera disponible 24 horas antes de la salida.

### RN-CHECKIN-03

Para vuelos nacionales, el check-in se considera disponible 48 horas antes de la salida.

### RN-CHECKIN-04

Para JetSMART, el check-in se considera disponible 72 horas antes de la salida.

### RN-CHECKIN-05

La regla de JetSMART tiene prioridad sobre la regla nacional o internacional.

La lógica conceptual es:

```text
¿Aerolínea = JetSMART?
       │
       ├── Sí → salida - 72 horas
       │
       └── No
            │
            ├── Internacional → salida - 24 horas
            │
            └── Nacional → salida - 48 horas
```

---

# 16. Estados del seguimiento de check-in

Los únicos estados definidos son:

```text
PENDIENTE
NOTIFICADO
ATENDIDO
```

### PENDIENTE

El sistema determina que corresponde realizar el check-in, pero todavía no se ha notificado al agente.

### NOTIFICADO

El sistema ya notificó al agente.

### ATENDIDO

El agente realizó el check-in en la plataforma externa de la aerolínea y confirmó la atención en el sistema.

---

# 17. Transiciones del check-in

La máquina de estados es:

```text
PENDIENTE
    │
    │ Sistema notifica
    ▼
NOTIFICADO
    │
    │ Agente realiza check-in externamente
    │ y confirma en el sistema
    ▼
ATENDIDO
```

### RN-CHECKIN-06

No se permite:

```text
PENDIENTE → ATENDIDO
```

### RN-CHECKIN-07

El sistema es responsable de:

```text
PENDIENTE → NOTIFICADO
```

### RN-CHECKIN-08

El agente es responsable de:

```text
NOTIFICADO → ATENDIDO
```

### RN-CHECKIN-09

El sistema no realiza automáticamente el check-in.

### RN-CHECKIN-10

La agencia no requiere registrar dentro del sistema si el cliente fue contactado.

---

# 18. Flujo operativo principal

```text
Cliente
   │
   ▼
Agente
   │
   ├── Consulta AGIL / aerolínea
   │
   ├── Reserva / compra
   │
   ├── Entrega códigos de reserva
   │
   ▼
Sistema CONFORT TRAVEL
   │
   ├── Registra cliente
   ├── Registra venta
   ├── Registra pasajeros
   ├── Registra viaje
   ├── Registra tramos
   ├── Registra reservas
   ├── Registra aerolíneas
   └── Registra servicios
        │
        ▼
   Calcula disponibilidad
   de check-in
        │
        ▼
     PENDIENTE
        │
        │ Sistema notifica
        ▼
     NOTIFICADO
        │
        │ Agente ingresa a
        │ plataforma externa
        │
        │ Realiza check-in
        ▼
     ATENDIDO
```

---

# 19. Modelo de dominio

El modelo de dominio propuesto es:

```text
                        ┌──────────────┐
                        │   USUARIO    │
                        │              │
                        │ ADMIN        │
                        │ AGENTE       │
                        └──────┬───────┘
                               │
                               │ 1:N
                               ▼
┌──────────────┐        ┌──────────────┐
│   CLIENTE    │        │    VENTA     │
│              │ 1:N    │              │
└──────┬───────┘───────►│              │
       │                │ precioInicial│
       │                │ precioFinal  │
       │                │ ganancia     │
       │                └──────┬───────┘
       │                       │
       │                       │ 1:1
       │                       ▼
       │                ┌──────────────┐
       │                │    VIAJE     │
       │                └──────┬───────┘
       │                       │
       │                       │ 1:N
       │                       ▼
       │                ┌──────────────┐
       │                │    TRAMO     │
       │                └───┬──────┬───┘
       │                    │      │
       │                   1:1    1:1
       │                    │      │
       │                    ▼      ▼
       │              ┌────────┐ ┌─────────┐
       │              │RESERVA │ │ CHECKIN │
       │              └───┬────┘ └─────────┘
       │                  │
       │                 N:1
       │                  │
       │                  ▼
       │             ┌────────────┐
       │             │ AEROLINEA  │
       │             └────────────┘
       │
       │
       │
       └──────────────────────► VENTA
                                  │
                                  │ 1:N
                                  ▼
                           ┌───────────────┐
                           │VENTA_SERVICIO │
                           └───────┬───────┘
                                   │ N:1
                                   ▼
                           ┌──────────────────┐
                           │SERVICIO_ADICIONAL│
                           └──────────────────┘
```

---

# 20. Entidades principales

## 20.1. Usuario

Representa a un usuario del sistema.

```text
Usuario
--------
id
nombre
apellido
email
password
rol
activo
createdAt
updatedAt
version
```

---

## 20.2. Cliente

Representa al cliente principal de la operación.

```text
Cliente
--------
id
nombres
apellidos
telefono
createdAt
updatedAt
```

Los campos adicionales deberán incorporarse únicamente cuando hayan sido definidos por la agencia.

---

## 20.3. Venta

Representa una operación independiente de compra.

```text
Venta
--------
id
fechaCompra
precioInicial
precioFinal
observaciones
cliente
agente
viaje
createdAt
updatedAt
version
```

La ganancia será:

```text
ganancia = precioFinal - precioInicial
```

---

## 20.4. Viaje

Representa el viaje adquirido dentro de una venta.

```text
Viaje
--------
id
venta
tramos
```

Un viaje puede contener uno o más tramos.

---

## 20.5. Tramo

Representa un desplazamiento específico.

```text
Tramo
--------
id
tipo
origen
destino
fechaHoraSalida
fechaHoraLlegada
viaje
reserva
checkIn
```

---

## 20.6. Reserva

Representa el código de reserva asociado a un tramo.

```text
Reserva
--------
id
codigoReserva
tramo
aerolinea
```

---

## 20.7. Aerolínea

Representa el catálogo de aerolíneas.

```text
Aerolinea
--------
id
nombre
codigo
activo
```

---

## 20.8. CheckIn

Representa el seguimiento del check-in.

```text
CheckIn
--------
id
estado
fechaDisponible
fechaNotificacion
fechaAtencion
tramo
```

---

## 20.9. ServicioAdicional

Representa servicios que pueden asociarse a una venta.

```text
ServicioAdicional
--------
id
nombre
descripcion
activo
```

---

## 20.10. VentaServicio

Entidad intermedia entre venta y servicio adicional.

```text
VentaServicio
--------
id
venta
servicio
```

---

## 20.11. Notificacion

Representa los avisos generados por el sistema.

```text
Notificacion
--------
id
tipo
mensaje
fechaCreacion
fechaLectura
usuario
checkIn
```

---

## 20.12. AuditLog

Representa el registro de auditoría.

```text
AuditLog
--------
id
usuario
accion
entidad
entidadId
fecha
detalle
```

---

# 21. Cardinalidades

| Relación                  | Cardinalidad                 |
| ------------------------- | ---------------------------- |
| Cliente → Venta           | 1:N                          |
| Usuario → Venta           | 1:N                          |
| Venta → Viaje             | 1:1                          |
| Viaje → Tramo             | 1:N                          |
| Tramo → Reserva           | 1:1                          |
| Reserva → Aerolínea       | N:1                          |
| Tramo → CheckIn           | 1:1                          |
| Venta → ServicioAdicional | N:M mediante `VentaServicio` |
| CheckIn → Notificacion    | 1:N                          |
| Usuario → Notificacion    | 1:N                          |
| Usuario → AuditLog        | 1:N                          |

---

# 22. Ejemplo de dominio

## Cliente con varias compras independientes

```text
CLIENTE
Juan Pérez
│
├── VENTA #001
│   │
│   └── VIAJE
│       └── Lima → Cusco
│
├── VENTA #002
│   │
│   └── VIAJE
│       └── Lima → Arequipa
│
└── VENTA #003
    │
    └── VIAJE
        ├── Lima → Madrid
        └── Madrid → Lima
```

La misma persona puede tener múltiples ventas, pero cada operación de compra se mantiene independiente.

---

# 23. Ejemplo de viaje con diferentes aerolíneas

Una venta puede representar un viaje cuyos tramos tienen diferentes aerolíneas:

```text
VENTA #100
│
└── VIAJE
    │
    ├── TRAMO 1
    │   Lima → Madrid
    │   │
    │   └── RESERVA
    │       Aerolínea: LATAM
    │       Código: ABC123
    │
    └── TRAMO 2
        Madrid → Lima
        │
        └── RESERVA
            Aerolínea: IBERIA
            Código: XYZ789
```

Esto es una de las razones principales por las que la aerolínea y el código de reserva **no deben estar directamente en `Venta`**.

---

# 24. Arquitectura propuesta

Para la primera versión se utilizará un:

> **Monolito modular**

No se implementarán microservicios inicialmente.

La arquitectura estará organizada por módulos de negocio:

```text
com.conforttravel.api
│
├── auth
├── usuario
├── cliente
├── venta
├── viaje
├── tramo
├── reserva
├── aerolinea
├── servicio
├── checkin
├── notificacion
├── dashboard
├── auditoria
└── shared
```

Cada módulo podrá contener:

```text
controller
service
repository
entity
dto
mapper
exception
```

No todos los módulos necesitan obligatoriamente todas las capas.

---

# 25. Arquitectura lógica del backend

```text
┌─────────────────────────────────────────┐
│                REST API                 │
│             Controllers                 │
└───────────────────┬─────────────────────┘
                    │
                    ▼
┌─────────────────────────────────────────┐
│              Application               │
│                Services                │
└───────────────────┬─────────────────────┘
                    │
                    ▼
┌─────────────────────────────────────────┐
│                Domain                  │
│        Business Rules / Entities       │
└───────────────────┬─────────────────────┘
                    │
                    ▼
┌─────────────────────────────────────────┐
│             Persistence               │
│       Spring Data JPA / Hibernate      │
└───────────────────┬─────────────────────┘
                    │
                    ▼
┌─────────────────────────────────────────┐
│               SQL Server               │
└─────────────────────────────────────────┘
```

---

# 26. Stack tecnológico

## Backend

* Java 21 LTS
* Spring Boot 4.1.1
* Spring Web MVC
* Spring Data JPA
* Hibernate
* Spring Security
* JWT
* Jakarta Bean Validation
* MapStruct
* Lombok
* Maven
* SQL Server 2022
* Spring Scheduler
* OpenAPI
* JUnit 5
* Mockito
* Spring Boot Test
* Testcontainers
* Docker
* Docker Compose
* Spring Actuator

---

# 27. Stack frontend

* Angular 20
* TypeScript
* Node.js 24
* RxJS
* Angular Signals
* Angular Router
* HttpClient
* Reactive Forms
* Angular Material
* Angular CDK
* SCSS
* ECharts o Chart.js
* Vitest
* Playwright
* ESLint
* Prettier
* Docker
* Nginx

---

# 28. Persistencia

Base de datos:

```text
SQL Server 2022
```

La persistencia utilizará:

```text
Spring Data JPA
        +
Hibernate
```

Las entidades JPA representarán el modelo persistente, mientras que los DTO permitirán separar el contrato HTTP del modelo interno.

---

# 29. Patrón DTO y MapStruct

No se expondrán directamente las entidades JPA desde los controllers.

El flujo será:

```text
HTTP Request
     │
     ▼
Request DTO
     │
     ▼
Mapper
     │
     ▼
Entity
     │
     ▼
Service
     │
     ▼
Repository
```

Y para la respuesta:

```text
Entity
   │
   ▼
Mapper
   │
   ▼
Response DTO
   │
   ▼
HTTP Response
```

MapStruct será utilizado para reducir código repetitivo de conversión entre DTOs y entidades.

---

# 30. Seguridad

El sistema utilizará autenticación y autorización.

Conceptualmente:

```text
Usuario
   │
   ▼
Login
   │
   ▼
JWT
   │
   ▼
Requests autenticados
   │
   ├── ADMIN
   │
   └── AGENTE
```

Las operaciones administrativas estarán restringidas al rol `ADMIN`.

---

# 31. Validaciones

Las validaciones se implementarán en diferentes niveles:

### API

Mediante Bean Validation:

```text
@NotNull
@NotBlank
@Size
@Email
@Positive
@PositiveOrZero
```

### Dominio

Mediante reglas de negocio.

Ejemplos:

```text
precioFinal >= 0
precioInicial >= 0
```

y:

```text
fechaRetorno >= fechaIda
```

### Persistencia

Mediante restricciones de base de datos:

```text
NOT NULL
UNIQUE
FOREIGN KEY
CHECK
```

cuando corresponda.

---

# 32. Manejo de errores

Se utilizará un manejo global de excepciones mediante:

```text
@RestControllerAdvice
```

Las respuestas de error deberán tener una estructura consistente.

Ejemplo conceptual:

```json
{
  "timestamp": "2026-09-17T10:30:00",
  "status": 400,
  "code": "VALIDATION_ERROR",
  "message": "Los datos enviados no son válidos",
  "path": "/api/ventas"
}
```

---

# 33. Transacciones

Las operaciones que modifiquen múltiples entidades relacionadas deberán ejecutarse dentro de transacciones.

Ejemplo:

```text
Registrar Venta
    │
    ├── Crear venta
    ├── Crear viaje
    ├── Crear tramos
    ├── Crear reservas
    ├── Asociar servicios
    └── Crear seguimiento
```

La operación completa deberá mantener consistencia transaccional.

---

# 34. Automatización del check-in

El sistema tendrá un proceso programado mediante Spring Scheduler.

Flujo:

```text
Scheduler
    │
    ▼
Buscar check-ins
PENDIENTES
    │
    ▼
fechaDisponible <= ahora
    │
    ▼
Generar notificación
    │
    ▼
PENDIENTE → NOTIFICADO
```

El scheduler **no realizará ninguna operación sobre la plataforma de la aerolínea**.

---

# 35. Idempotencia

El proceso de notificación deberá diseñarse para evitar notificaciones duplicadas.

Por ejemplo, si el scheduler se ejecuta varias veces:

```text
PENDIENTE
   │
   ▼
NOTIFICADO
```

Una vez que el registro está en `NOTIFICADO`, una ejecución posterior no deberá volver a tratarlo como pendiente.

---

# 36. Auditoría

Las operaciones importantes deberán quedar registradas.

Ejemplo:

```text
Usuario: agente01
Entidad: Venta
ID: 150
Acción: ACTUALIZAR
Fecha: 2026-09-17 10:30
```

La auditoría permitirá conocer quién realizó determinadas modificaciones y cuándo.

---

# 37. Consultas y búsqueda

La plataforma deberá permitir búsquedas y filtros.

En backend se utilizarán:

* Paginación.
* Ordenamiento.
* Filtros dinámicos.
* Spring Data Specifications cuando corresponda.

Ejemplos:

```text
Buscar ventas por:
- Cliente
- Fecha
- Agente
- Aerolínea
- Estado
```

y:

```text
Buscar check-ins por:
- Estado
- Fecha
- Agente
- Aerolínea
```

---

# 38. Requisitos no funcionales

## RNF-01 — Seguridad

Las funcionalidades deberán estar protegidas mediante autenticación y autorización.

## RNF-02 — Mantenibilidad

El código deberá estar organizado por módulos de negocio.

## RNF-03 — Separación de responsabilidades

Controllers, servicios, repositorios, DTOs y entidades deberán mantener responsabilidades diferenciadas.

## RNF-04 — Integridad

Las relaciones entre entidades deberán mantenerse mediante restricciones y transacciones.

## RNF-05 — Trazabilidad

Las operaciones relevantes deberán contar con información de auditoría.

## RNF-06 — Escalabilidad

La solución deberá permitir crecimiento funcional sin requerir inicialmente una arquitectura distribuida.

## RNF-07 — Testabilidad

Las reglas de negocio y componentes principales deberán contar con pruebas automatizadas.

## RNF-08 — Documentación

La API deberá estar documentada mediante OpenAPI.

## RNF-09 — Despliegue

La aplicación deberá poder ejecutarse mediante contenedores Docker.

---

# 39. Estructura inicial del backend

```text
confort-travel-backend/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── conforttravel/
│   │   │           └── api/
│   │   │               ├── auth/
│   │   │               ├── usuario/
│   │   │               ├── cliente/
│   │   │               ├── venta/
│   │   │               ├── viaje/
│   │   │               ├── tramo/
│   │   │               ├── reserva/
│   │   │               ├── aerolinea/
│   │   │               ├── servicio/
│   │   │               ├── checkin/
│   │   │               ├── notificacion/
│   │   │               ├── dashboard/
│   │   │               ├── auditoria/
│   │   │               └── shared/
│   │   │
│   │   └── resources/
│   │       ├── application.yml
│   │       └── db/
│   │
│   └── test/
│
├── pom.xml
└── Dockerfile
```

---

# 40. Estructura inicial del frontend

```text
confort-travel-front/
│
├── src/
│   ├── app/
│   │   ├── core/
│   │   │   ├── auth/
│   │   │   ├── guards/
│   │   │   ├── interceptors/
│   │   │   └── services/
│   │   │
│   │   ├── shared/
│   │   │   ├── components/
│   │   │   ├── directives/
│   │   │   └── pipes/
│   │   │
│   │   └── features/
│   │       ├── dashboard/
│   │       ├── ventas/
│   │       ├── clientes/
│   │       ├── viajes/
│   │       ├── reservas/
│   │       ├── aerolineas/
│   │       ├── servicios/
│   │       ├── checkin/
│   │       └── usuarios/
│   │
│   └── styles.scss
│
├── package.json
└── Dockerfile
```

---

# 41. Estados principales

## Estado de usuario

```text
ACTIVO
INACTIVO
```

## Estado de check-in

```text
PENDIENTE
NOTIFICADO
ATENDIDO
```

## Rol

```text
ADMIN
AGENTE
```

## Tipo de tramo

```text
IDA
RETORNO
```

---

# 42. Catálogos iniciales

El sistema deberá contemplar catálogos para:

### Aerolíneas

Ejemplos observados en la operación:

```text
LATAM
SKY
JETSMART
AVIANCA
IBERIA
AIREUROPA
```

### Servicios adicionales

Ejemplos observados:

```text
MIGRATORIO
SEGURO COBERTURADO
RETORNO ANULABLE
CAMBIO DE FECHA
PRE RESERVA
```

Los catálogos deberán permitir incorporar nuevos valores sin modificar la estructura principal del sistema.

---

# 43. Reglas de integridad del dominio

El modelo debe garantizar, entre otras, las siguientes condiciones:

```text
Venta → siempre tiene Cliente
Venta → siempre tiene Agente
Venta → siempre tiene Viaje
Viaje → tiene uno o más Tramos
Tramo → tiene Reserva cuando corresponda
Reserva → tiene Aerolínea
Tramo → tiene seguimiento de Check-in cuando corresponda
```

No se debe permitir crear una estructura inconsistente.

---

# 44. Decisiones arquitectónicas

| Decisión        | Elección                         |
| --------------- | -------------------------------- |
| Arquitectura    | Monolito modular                 |
| Backend         | Spring Boot                      |
| Lenguaje        | Java 21                          |
| Persistencia    | Spring Data JPA                  |
| ORM             | Hibernate                        |
| BD              | SQL Server 2022                  |
| API             | REST                             |
| Seguridad       | Spring Security + JWT            |
| Mapping         | MapStruct                        |
| Validación      | Jakarta Bean Validation          |
| Scheduler       | Spring Scheduler                 |
| Testing         | JUnit + Mockito + Testcontainers |
| Contenedores    | Docker                           |
| Frontend        | Angular 20                       |
| Estado frontend | Signals + RxJS                   |
| UI              | Angular Material                 |
| CSS             | SCSS                             |

---

# 45. Principios de diseño

El proyecto seguirá principalmente estos principios:

### Separación de responsabilidades

Cada componente deberá tener una responsabilidad clara.

### Bajo acoplamiento

Los módulos deberán depender de contratos y abstracciones cuando corresponda.

### Alta cohesión

Las funcionalidades relacionadas deberán mantenerse agrupadas por dominio.

### Single Responsibility Principle

Una clase no deberá asumir responsabilidades que pertenecen a otras capas.

### DTO ≠ Entity

Las entidades JPA no serán utilizadas directamente como contrato de la API.

### Regla de negocio en el dominio/aplicación

Las reglas como el cálculo de disponibilidad del check-in no deberán quedar dispersas en controllers.

### Persistencia consistente

Las operaciones que involucren múltiples cambios relacionados deberán ser transaccionales.

---

# 46. Resumen del dominio

El concepto central del sistema es la **Venta**.

Una venta representa una única operación de compra y pertenece a:

```text
Cliente
+
Agente
+
Viaje
```

El viaje puede contener uno o más tramos:

```text
Venta
└── Viaje
    ├── Tramo
    ├── Tramo
    └── ...
```

Cada tramo puede estar asociado a:

```text
Reserva
└── Aerolínea
```

y:

```text
CheckIn
```

Los servicios adicionales se relacionan con la venta:

```text
Venta
└── VentaServicio
    └── ServicioAdicional
```

---

# 47. Flujo completo del negocio

```text
                  CLIENTE
                     │
                     ▼
                   AGENTE
                     │
                     │
             ┌───────▼────────┐
             │ Plataformas     │
             │ externas        │
             │                │
             │ AGIL            │
             │ Aerolíneas      │
             └───────┬────────┘
                     │
                     │ Compra realizada
                     ▼
              CONFORT TRAVEL
                     │
                     ▼
                  VENTA
                     │
          ┌──────────┼──────────┐
          ▼          ▼          ▼
       CLIENTE     AGENTE     VIAJE
                                │
                                ▼
                              TRAMO
                                │
                     ┌──────────┴─────────┐
                     ▼                    ▼
                  RESERVA              CHECK-IN
                     │                    │
                     ▼                    │
                 AEROLÍNEA                │
                                          │
                                fecha disponible
                                          │
                                          ▼
                                      PENDIENTE
                                          │
                                          │ Sistema
                                          ▼
                                      NOTIFICADO
                                          │
                                          │ Agente realiza
                                          │ check-in externo
                                          ▼
                                       ATENDIDO
```

---

# 48. Resultado esperado

Al finalizar la primera versión, CONFORT TRAVEL dispondrá de una plataforma interna que permita reemplazar el uso de Excel como mecanismo principal de gestión operativa, manteniendo la información estructurada y relacionada.

La plataforma permitirá conocer:

* Qué cliente realizó la compra.
* Qué agente gestionó la venta.
* Cuándo se realizó la compra.
* Cuál fue el precio inicial.
* Cuál fue el precio final.
* Cuál es la ganancia.
* Qué viaje fue adquirido.
* Qué tramos componen el viaje.
* Qué aerolínea corresponde a cada tramo.
* Qué código de reserva corresponde a cada tramo.
* Qué servicios adicionales tiene la venta.
* Cuándo corresponde realizar el check-in.
* Si el agente ya fue notificado.
* Si el agente ya atendió el check-in.

El sistema será una herramienta de **gestión y seguimiento operativo**, manteniendo las operaciones de compra y check-in en las plataformas externas utilizadas actualmente por la agencia.

---

# 49. Siguiente etapa de desarrollo

Con este diseño funcional y de dominio definido, la siguiente etapa será pasar del modelo conceptual al **diseño físico y técnico de la aplicación**.

El orden recomendado será:

```text
1. Diccionario de datos
        ↓
2. Modelo relacional SQL Server
        ↓
3. Diseño de tablas y claves
        ↓
4. Entidades JPA
        ↓
5. Enums
        ↓
6. Relaciones JPA
        ↓
7. DTOs
        ↓
8. MapStruct
        ↓
9. Repositories
        ↓
10. Services
        ↓
11. Reglas de negocio
        ↓
12. Controllers REST
        ↓
13. Seguridad JWT
        ↓
14. Scheduler de Check-in
        ↓
15. Auditoría
        ↓
16. Testing
        ↓
17. Docker
        ↓
18. Frontend Angular
```

La **primera actividad de implementación** será elaborar el **diccionario de datos y modelo relacional**, porque será la base para construir correctamente las entidades JPA y evitar modificaciones estructurales posteriores.

```
```
