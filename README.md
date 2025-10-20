# Microservicio de Órdenes de Entrega

Este proyecto es un microservicio para la gestión de órdenes de entrega, desarrollado con Spring Boot. La aplicación expone una API REST para crear, consultar y actualizar órdenes, utilizando una arquitectura moderna orientada a eventos y con caché de alto rendimiento.

---
## ✨ Características Principales

* **API REST Completa:** Endpoints para operaciones CRUD sobre las órdenes.
* **Persistencia en MongoDB:** Almacenamiento de datos NoSQL flexible y escalable.
* **Caché con Redis:** Optimización de las consultas frecuentes para obtener órdenes por ID.
* **Mensajería con Kafka:** Publicación de eventos en un tópico cada vez que una orden cambia de estado.
* **Documentación Interactiva:** Documentación automática de la API con Springdoc OpenAPI (Swagger UI).
* **Contenerizado:** Toda la infraestructura (MongoDB, Redis, Kafka) se gestiona con Docker Compose para un despliegue local sencillo.
* **Testing Robusto:** Incluye tests unitarios (Mockito) y de integración (Testcontainers) para garantizar la calidad del código.

---
## 🛠️ Stack Tecnológico

| Componente              | Tecnología Utilizada                                     |
| ----------------------- | -------------------------------------------------------- |
| **Lenguaje y Framework**| Java 17, Spring Boot 3.2.0                               |
| **Base de Datos** | MongoDB                                                  |
| **Caché** | Redis                                                    |
| **Mensajería** | Apache Kafka                                             |
| **Build Tool** | Apache Maven                                             |
| **API Documentation** | Springdoc OpenAPI 3 (Swagger UI)                         |
| **Testing** | JUnit 5, Mockito, Testcontainers                         |
| **Infraestructura Local** | Docker & Docker Compose                                  |

---
## 🚀 Cómo Empezar

Sigue estos pasos para levantar y ejecutar el proyecto en tu entorno local.

### Prerrequisitos
* Git
* JDK 17 o superior
* Apache Maven 3.8+
* Docker y Docker Compose

### Instalación y Ejecución

1.  **Clona el repositorio:**
    ```bash
    git clone https://github.com/IgorRC/order-service.git
    cd order-service
    ```

2.  **Levanta la infraestructura con Docker:**
    Este comando iniciará los contenedores de MongoDB, Redis y Kafka en segundo plano.
    ```bash
    docker-compose up -d
    ```

3.  **Ejecuta la aplicación Spring Boot:**
    Puedes hacerlo desde tu IDE (ejecutando la clase `OrderServiceApplication`) o usando Maven en la terminal:
    ```bash
    mvn spring-boot:run
    ```
    La aplicación estará disponible en `http://localhost:8080`.

---
## <caption> Documentación de la API (Swagger UI)

Una vez que la aplicación esté corriendo, puedes acceder a la documentación interactiva de la API en tu navegador:

➡️ **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

Desde allí podrás ver todos los endpoints, sus modelos de datos y probarlos directamente.

### Endpoints Principales

#### 1. Crear una Orden
* `POST /orders`

**Ejemplo de Request Body:**
```json
{
  "customerId": "cliente-001",
  "items": [
    { "sku": "SKU-A1", "quantity": 2, "price": 150.50 },
    { "sku": "SKU-B2", "quantity": 1, "price": 200.00 }
  ]
}
```

#### 2. Obtener una Orden por ID
* `GET /orders/{id}`

#### 3. Listar Órdenes (con filtros)
* `GET /orders?status=NEW&customerId=cliente-001`

#### 4. Actualizar Estado de una Orden
* `PATCH /orders/{id}/status`
**Ejemplo de Request Body:**
```json
{
"newStatus": "IN_PROGRESS"
}
```