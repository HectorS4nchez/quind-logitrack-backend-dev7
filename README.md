# Package Tracking System - API REST

Sistema de seguimiento de paquetes con arquitectura hexagonal implementado en Spring Boot.

## Características

- **Gestión completa de paquetes (CRUD)**
- **Máquina de estados para el ciclo de vida del paquete**
- **Validaciones robustas de datos**
- **Patrón Builder para creación de objetos**
- **Cliente REST para geocodificación**
- **Arquitectura hexagonal (Puertos y Adaptadores)**
- **Pruebas unitarias completas**

## Estados del Paquete

El sistema implementa una máquina de estados con las siguientes transiciones válidas:

```
CREATED → IN_TRANSIT
IN_TRANSIT → OUT_FOR_DELIVERY | DELIVERY_FAILED | RETURNED
OUT_FOR_DELIVERY → DELIVERED | DELIVERY_FAILED
DELIVERY_FAILED → IN_TRANSIT | RETURNED
DELIVERED → [Estado final]
RETURNED → [Estado final]
```

## Requisitos

- Java 17 o superior
- Maven 3.6+
- Spring Boot 3.x

## Instalación

```bash
# Clonar el repositorio
git clone <repository-url>
cd package-tracking-system

# Compilar el proyecto
mvn clean install

# Ejecutar las pruebas
mvn test

# Ejecutar la aplicación
mvn spring-boot:run
```

## Estructura del Proyecto

```
quind-logitrack-backend-dev7/

│── application/                
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/quind/application/
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
│
├── domain/
│   ├── src/
│   │   ├── main/java/com/quind/domain/
│   │   │   ├── model/
│   │   │   ├── usecase/
│   │   │   └── exception/          
│   │   │       
│   │   └── test/
│   └── pom.xml
│
├── infrastructure/                 
│   ├── driven-adapters/             
│   │   ├── repository/              
│   │   │   ├── src/
│   │   │   │   └── main/java/com/quind/repository/
│   │   │   │       └── adapter/
│   │   │   │           
│   │   │   └── pom.xml
│   │   └── pom.xml
│   │
│   ├── entry-points/                
│   │   ├── api-rest/                
│   │   │   ├── src/
│   │   │   │   └── main/java/com/quind/apirest/
│   │   │   │       └── controller
│   │   │   └── pom.xml
│   │   └── pom.xml
│   │
│   └── pom.xml
│
└── target/
```

## API Endpoints

### Packages

#### Crear un paquete
```bash
POST /api/packages
Content-Type: application/json

{
  "trackingId": "LT-123456789",
  "recipient": {
    "name": "John Doe",
    "address": "123 Main St, Bogota, Colombia"
  },
  "dimensions": {
    "height": 10.0,
    "width": 20.0,
    "depth": 30.0
  },
  "weight": 5.5
}
```

#### Obtener un paquete
```bash
GET /api/packages/{trackingId}
```

#### Listar todos los paquetes
```bash
GET /api/packages
```

#### Actualizar un paquete
```bash
PUT /api/packages/{trackingId}
Content-Type: application/json

{
  "trackingId": "LT-123456789",
  "recipient": {
    "name": "John Doe Updated",
    "address": "456 New St"
  },
  "dimensions": {
    "height": 15.0,
    "width": 25.0,
    "depth": 35.0
  },
  "weight": 6.0
}
```

#### Eliminar un paquete
```bash
DELETE /api/packages/{trackingId}
```

#### Cambiar el estado de un paquete
```bash
PATCH /api/packages/{trackingId}/status?status=IN_TRANSIT
```

Estados válidos: `CREATED`, `IN_TRANSIT`, `OUT_FOR_DELIVERY`, `DELIVERED`, `DELIVERY_FAILED`, `RETURNED`

#### Agregar ubicación al historial
```bash
POST /api/packages/{trackingId}/locations?city=Bogota&country=Colombia
```

Con timestamp específico:
```bash
POST /api/packages/{trackingId}/locations?city=Madrid&country=Spain&timestamp=2024-01-15T10:30:00
```

### Recipients

#### Crear un destinatario
```bash
POST /api/recipients
Content-Type: application/json

{
  "name": "Jane Smith",
  "address": "789 Oak Ave"
}
```

#### Obtener un destinatario
```bash
GET /api/recipients/{id}
```

#### Listar todos los destinatarios
```bash
GET /api/recipients
```

#### Buscar destinatarios por nombre
```bash
GET /api/recipients/search?name=John
```

#### Actualizar un destinatario
```bash
PUT /api/recipients/{id}
Content-Type: application/json

{
  "name": "Jane Smith Updated",
  "address": "999 New Street"
}
```

#### Eliminar un destinatario
```bash
DELETE /api/recipients/{id}
```

### Dimensions

#### Crear dimensiones
```bash
POST /api/dimensions
Content-Type: application/json

{
  "height": 10.0,
  "width": 20.0,
  "depth": 30.0
}
```

#### Obtener dimensiones
```bash
GET /api/dimensions/{id}
```

#### Listar todas las dimensiones
```bash
GET /api/dimensions
```

#### Actualizar dimensiones
```bash
PUT /api/dimensions/{id}
Content-Type: application/json

{
  "height": 15.0,
  "width": 25.0,
  "depth": 35.0
}
```

#### Eliminar dimensiones
```bash
DELETE /api/dimensions/{id}
```

### Location History

#### Obtener historial de ubicación
```bash
GET /api/location-history/{id}
```

#### Listar todo el historial
```bash
GET /api/location-history
```

#### Buscar por ciudad
```bash
GET /api/location-history/by-city?city=Bogota
```

#### Buscar por país
```bash
GET /api/location-history/by-country?country=Colombia
```

#### Buscar por rango de fechas
```bash
GET /api/location-history/by-date-range?start=2024-01-01T00:00:00&end=2024-12-31T23:59:59
```

## Ejemplos de Uso con cURL

### Crear un paquete completo
```bash
curl -X POST http://localhost:8080/api/packages \
  -H "Content-Type: application/json" \
  -d '{
    "trackingId": "LT-987654321",
    "recipient": {
      "name": "Maria Garcia",
      "address": "Calle 100 #15-20, Bogota"
    },
    "dimensions": {
      "height": 25.0,
      "width": 30.0,
      "depth": 40.0
    },
    "weight": 8.5
  }'
```

### Cambiar estado a IN_TRANSIT
```bash
curl -X PATCH http://localhost:8080/api/packages/LT-987654321/status?status=IN_TRANSIT
```

### Agregar ubicación
```bash
curl -X POST "http://localhost:8080/api/packages/LT-987654321/locations?city=Bogota&country=Colombia"
```

### Obtener el paquete
```bash
curl http://localhost:8080/api/packages/LT-987654321
```

## Validaciones Implementadas

### Package
- **trackingId**: No puede ser nulo o vacío
- **recipient**: No puede ser nulo
- **dimensions**: No puede ser nulo
- **weight**: Debe ser mayor que cero
- **city/country**: No pueden ser nulos o vacíos al agregar ubicación
- **Transiciones de estado**: Solo se permiten transiciones válidas según la máquina de estados

### Dimensions
- **height, width, depth**: Deben ser mayores que cero

## Patrón Builder

El sistema implementa el patrón Builder para la creación flexible de paquetes:

```java
Package pkg = new Package.Builder()
    .trackingId("LT-123456")
    .recipient(recipient)
    .dimensions(dimensions)
    .weight(5.5)
    .status(PackageStatus.CREATED)
    .build();
```

## Pruebas

### Ejecutar todas las pruebas
```bash
mvn test
```

### Ejecutar una prueba específica
```bash
mvn test -Dtest=PackageTest
```

### Cobertura de pruebas
El proyecto incluye pruebas para:
- ✅ Validación de transiciones de estados (válidas e inválidas)
- ✅ Creación de objetos con datos válidos e inválidos
- ✅ Operaciones CRUD
- ✅ Cliente REST con mocks
- ✅ Casos borde y manejo de errores

## Configuración de Base de Datos

El proyecto usa H2 en memoria por defecto. Para usar otra base de datos, actualiza `application.properties`:

```properties
# PostgreSQL example
spring.datasource.url=jdbc:postgresql://localhost:5432/package_tracking
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

## Tecnologías Utilizadas

- **Spring Boot 3.x**
- **Spring Data JPA**
- **H2 Database** (desarrollo)
- **Lombok**
- **JUnit 5**
- **Mockito**
- **RestTemplate**
