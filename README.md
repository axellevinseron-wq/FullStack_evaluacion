# 🛒 EcoMarket - Arquitectura de Microservicios

Sistema e-commerce con 10 microservicios independientes, MySQL dedicada por servicio, Docker Compose.

## 📋 Stack Técnico

| Componente | Versión |
|-----------|---------|
| Java | 17 |
| Spring Boot | 4.0.6 |
| Spring Cloud | 2025.1.1 |
| MySQL | 8.0 |
| Lombok | 1.18.30 |
| Flyway | 9.22.3 |
| OpenFeign | Cloud |
| Docker | 3.8+ |

## 🏗️ Arquitectura de Microservicios

| MS | Puerto (host) | BD | Responsabilidad |
|----|--------|----|----|
| **api-gateway** | 9000 | - | Punto de entrada unico, enruta hacia los 10 microservicios |
| **ms-catalogo** | 8081 | ms_catalogo | Productos, categorías |
| **ms-pedidos** | 8082 | ms_pedidos | Órdenes, estados (PENDIENTE/PAGADA/ENVIADO/ENTREGADO/CANCELADA) |
| **ms-carrito** | 8083 | ms_carrito | Carrito usuario, Feign→catalogo |
| **ms-usuarios** | 8001 | ms_usuarios | Autenticación, cuentas |
| **ms-pagos** | 8002 | ms_pagos | Transacciones |
| **ms-notificaciones** | 8003 | ms_notificaciones | Email, SMS |
| **ms-envios** | 8005 | ms_envios | Tracking |
| **ms-clientes** | 8008 | ms_clientes | Perfiles cliente |
| **ms-promociones** | 8009 | ms_promociones | Descuentos, vigencia |
| **ms-inventario** | 8010 | ms_inventario | Stock, reservas |

## 🌐 API Gateway (Spring Cloud Gateway)

Todas las peticiones pueden entrar por un unico punto: **http://localhost:9000**. El Gateway
enruta cada request al microservicio correspondiente segun el prefijo de la ruta (mismo path que
usarias directo contra el microservicio, solo cambia el puerto):

| Ruta a traves del Gateway | Redirige a |
|---|---|
| `http://localhost:9000/api/catalogo/**` | ms-catalogo (8081) |
| `http://localhost:9000/api/pedidos/**` | ms-pedidos (8082) |
| `http://localhost:9000/api/carrito/**` | ms-carrito (8083) |
| `http://localhost:9000/api/usuarios/**` | ms-usuarios (8001) |
| `http://localhost:9000/api/pagos/**` | ms-pagos (8002) |
| `http://localhost:9000/api/notificaciones/**` | ms-notificaciones (8003) |
| `http://localhost:9000/api/envios/**` | ms-envios (8005) |
| `http://localhost:9000/api/clientes/**` | ms-clientes (8008) |
| `http://localhost:9000/api/promociones/**` | ms-promociones (8009) |
| `http://localhost:9000/api/inventario/**` | ms-inventario (8010) |

El Gateway se levanta como un servicio mas dentro de `docker-compose.yml` (`api-gateway`, perfil
`docker`) y expone un filtro global (`GatewayLoggingFilter`) que deja traza de metodo, ruta, status
y tiempo de respuesta de cada solicitud enrutada.

## 📄 Documentación Swagger / OpenAPI

Cada microservicio expone su propia UI de Swagger (interactiva, permite probar los endpoints
directo desde el navegador):

| MS | Swagger UI | JSON OpenAPI |
|---|---|---|
| ms-catalogo | http://localhost:8081/swagger-ui.html | http://localhost:8081/v3/api-docs |
| ms-pedidos | http://localhost:8082/swagger-ui.html | http://localhost:8082/v3/api-docs |
| ms-carrito | http://localhost:8083/swagger-ui.html | http://localhost:8083/v3/api-docs |
| ms-usuarios | http://localhost:8001/swagger-ui.html | http://localhost:8001/v3/api-docs |
| ms-pagos | http://localhost:8002/swagger-ui.html | http://localhost:8002/v3/api-docs |
| ms-notificaciones | http://localhost:8003/swagger-ui.html | http://localhost:8003/v3/api-docs |
| ms-envios | http://localhost:8005/swagger-ui.html | http://localhost:8005/v3/api-docs |
| ms-clientes | http://localhost:8008/swagger-ui.html | http://localhost:8008/v3/api-docs |
| ms-promociones | http://localhost:8009/swagger-ui.html | http://localhost:8009/v3/api-docs |
| ms-inventario | http://localhost:8010/swagger-ui.html | http://localhost:8010/v3/api-docs |

## ⚙️ Configuración con YAML

Todos los microservicios usan `application.yml` (no `.properties`) organizados en 3 perfiles:

- **default** (sin perfil activo): pensado para correr el servicio desde el IDE, apunta a `localhost:3306`.
- **docker**: activado por `SPRING_PROFILES_ACTIVE=docker` en `docker-compose.yml`, apunta al contenedor `ecomarket_mysql_db` (o a los contenedores de cada MS en el caso del Gateway) y fuerza `server.port=8080`.
- **prod**: pensado para despliegue remoto (Railway/Render); las credenciales y URLs se inyectan por variables de entorno (`SPRING_DATASOURCE_URL`, `PORT`, etc.) en vez de quedar hardcodeadas.

## 📦 Requisitos Previos

- **Docker** & **Docker Compose** v3.8+
- **Maven** 3.8+ (compilar JARs)
- **Java 17+**
- **Git**

## 🚀 Instalación y Ejecución

### 1. Clonar y compilar todos los servicios
```bash
cd PROYECTO
mvn clean package -DskipTests
```

### 2. Verificar JARs compilados
```bash
ls -la ms-catalogo/target/ms-catalogo-1.0.0.jar
ls -la ms-pedidos/target/ms-pedidos-1.0.0.jar
ls -la ms_carrito/target/ms_carrito-1.0.0.jar
```

### 3. Levantar infraestructura con Docker
```bash
docker-compose up -d
docker-compose ps  # Verificar servicios
```

### 4. Verificar conectividad
```bash
# MySQL (localhost:3306, user: root, pass: root)
mysql -h localhost -u root -proot -e "SHOW DATABASES;"

# PhpMyAdmin (http://localhost:8080)

# API Catalogo
curl http://localhost:8081/api/catalogo

# API Carrito (con Feign a catalogo)
curl -X POST http://localhost:8083/api/carrito \
  -H "Content-Type: application/json" \
  -d '{"clienteId":1,"productoId":1,"cantidad":2}'

# API Pedidos
curl http://localhost:8082/api/pedidos
```

## 🔗 Endpoints Críticos

### ms-catalogo (8081) - Catalog Service
```
GET    /api/catalogo                  → Listar categorías con productos
POST   /api/catalogo                  → Crear categoría [ADMIN]
GET    /api/catalogo/productos/{id}   → Obtener producto por ID [Consumidor: ms-carrito Feign]
```

### ms-carrito (8083) - Shopping Cart Service
```
POST   /api/carrito                   → Crear item (valida precio vía Feign)
GET    /api/carrito                   → Listar items carrito
GET    /api/carrito/{id}              → Obtener item específico
DELETE /api/carrito/{id}              → Eliminar item
```

**Flujo:** `POST /api/carrito` → ProductoFeignClient → ms-catalogo (8081) → validar precio/existencia

### ms-pedidos (8082) - Orders Service
```
POST   /api/pedidos                   → Crear pedido (estado: PENDIENTE)
GET    /api/pedidos                   → Listar pedidos
GET    /api/pedidos/{id}              → Obtener pedido
PUT    /api/pedidos/{id}/estado       → Cambiar estado [PAGADA/CANCELADA] + LOGS
```

**Estados válidos:**
- `PENDIENTE` → `PAGADA` o `CANCELADA`
- `PAGADA` → `ENVIADO` o `CANCELADA`
- `ENVIADO` → `ENTREGADO`
- `ENTREGADO` → (sin cambios)
- `CANCELADA` → (sin cambios)

### ms-inventario (8084) - Inventory Service
```
POST   /api/inventario/reservar       → Validar y reservar stock
GET    /api/inventario/producto/{id}  → Consultar disponibilidad
PUT    /api/inventario/liberar        → Devolver stock (si cancela pedido)
```

## 🏛️ Patrones Implementados

### 1. Resiliencia Inter-Servicio (Circuit Breaker)
**Ubicación:** ms-carrito → ProductoFeignClient

- **Timeout:** 5s (connect + read)
- **Fallback:** ProductoFeignClientFallback (devuelve error controlado)
- **Circuit Breaker:** Resilience4j (abre si 50% de llamadas fallan)

```java
@FeignClient(name = "ms-catalogo", 
    url = "http://localhost:8080",
    fallback = ProductoFeignClientFallback.class)
```

**Cómo probar fallo:**
```bash
docker-compose stop ms-catalogo
curl -X POST http://localhost:8083/api/carrito \
  -d '{"clienteId":1,"productoId":999,"cantidad":1}' 
# Respuesta: 400 + "Catálogo temporalmente no disponible"
```

### 2. Trazabilidad (@Slf4j)
**Ubicación:** ms-pedidos Service

**Logs generados:**
- `log.info()` - Creación pedido, cambio estado (PAGADA/CANCELADA)
- `log.warn()` - Transiciones inválidas, stock bajo
- `log.error()` - Fallos Feign, BD caída

**Ejemplo salida:**
```
INFO - Guardando nuevo pedido para cliente: 1
INFO - ✓ PEDIDO PAGADO: AUDITORIA - Pedido 1: Pendiente -> Pagado
ERROR - Excepción de negocio - Código: TRANSICION_INVALIDA, Mensaje: Transición inválida de ENTREGADO a PAGADA
```

### 3. Validación (Bean Validation)
**Global:** GlobalExceptionHandler (@RestControllerAdvice)

```java
@ExceptionHandler(MethodArgumentNotValidException.class)
// → 400 + ErrorResponseDTO { code: "VALIDATION_ERROR", details: {...}, timestamp, status }

@ExceptionHandler(BusinessException.class)
// → 400 + ErrorResponseDTO { code: "PEDIDO_NO_ENCONTRADO", message, status }
```

**DTOs validados:**
- CartItemDTO: @NotNull productId, @Min(1) quantity, @Min(0) price
- ProductoDTO: @NotBlank nombre, @Min(0) precio
- PedidoDTO: @NotNull clienteId

### 4. Persistencia (JPA + Flyway)
**Ubicación:** Cada servicio tiene su BD independiente

- **DDL:** `ddl-auto=validate` (no auto-create en prod)
- **Migraciones:** Flyway (classpath:db/migration/)
- **Script automático:** Al levantar container, Flyway crea tablas

## 🔍 Monitoreo y Logs

### Ver logs en tiempo real
```bash
# Catalogo
docker-compose logs -f ms-catalogo

# Pedidos (auditoría de cambios de estado)
docker-compose logs -f ms-pedidos | grep -i "PAGADO\|CANCELADO"

# Carrito (consumo de Feign)
docker-compose logs -f ms-carrito

# Todos los servicios
docker-compose logs -f --tail=50
```

### Buscar errores
```bash
docker-compose logs | grep -i "error\|exception"
```

### Verificar estado MySQL
```bash
docker-compose logs mysql
docker exec ecomarket_mysql_db mysql -u root -proot -e "SHOW DATABASES;"
```

## 🛠️ Troubleshooting

| Problema | Síntoma | Solución |
|----------|---------|----------|
| "Connection refused" en ms-carrito | Error 500 POST /api/carrito | Esperar 30s a MySQL, ver `docker-compose logs mysql` |
| ms-catalogo no responde | 400 "Catálogo no disponible" | Fallback activado, revisar `docker-compose logs ms-catalogo` |
| Base de datos no creada | Error "Unknown database" | Verificar Flyway scripts en `resources/db/migration/`, ver logs Flyway |
| Puerto ya en uso | "bind: address already in use" | Cambiar puerto en docker-compose.yml: `"8081:8080"` → `"8091:8080"` |
| Feign timeout | 500 después de 5s | Incrementar timeout en application.properties: `feign.client.config.default.readTimeout=10000` |

## 🧪 Testing Flujo Completo

### 1. Crear categoría + producto (ms-catalogo)
```bash
curl -X POST http://localhost:8081/api/catalogo \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "GPUs",
    "productos": [
      {"nombre": "RTX 4090", "precio": 1599.99}
    ]
  }'
# Response: Categoria { id: 1, productos: [{ id: 1, ... }] }
```

### 2. Agregar a carrito (ms-carrito con Feign)
```bash
curl -X POST http://localhost:8083/api/carrito \
  -H "Content-Type: application/json" \
  -d '{
    "clienteId": 1,
    "productoId": 1,
    "cantidad": 2
  }'
# Response: ItemCarrito { id: 1, productoId: 1, precio: 1599.99 (del Feign) }
```

### 3. Crear pedido (ms-pedidos)
```bash
curl -X POST http://localhost:8082/api/pedidos \
  -H "Content-Type: application/json" \
  -d '{
    "clienteId": 1
  }'
# Response: Pedido { id: 1, estado: PENDIENTE, ... }
```

### 4. Cambiar estado a PAGADA + logs
```bash
curl -X PUT "http://localhost:8082/api/pedidos/1/estado?estado=PAGADA"
# Response: Pedido { id: 1, estado: PAGADA, ... }
# Logs: "✓ PEDIDO PAGADO: AUDITORIA - Pedido 1: Pendiente -> Pagado"
```

## 📐 Forma de Entrega

**Entregables:**
- ✅ Código fuente compilado: `ms-*/target/*.jar`
- ✅ docker-compose.yml ejecutable
- ✅ BD autoiniCializada por Flyway
- ✅ Endpoints funcionales inter-servicio (Feign)
- ✅ Logs de auditoría para cambios de estado
- ✅ Manejo de errores global (@RestControllerAdvice)

**Verificación:**
```bash
docker-compose up -d
# Esperar 30s
curl http://localhost:8081/api/catalogo  # OK → 200
curl http://localhost:8082/api/pedidos    # OK → 200
curl http://localhost:8083/api/carrito    # OK → 200
```

## 📚 Documentación Adicional

- **GAP_ANALYSIS.md** - Análisis de brechas vs rúbrica
- **DOCKER_SETUP.md** - Guía detallada Docker Compose
- **pom.xml (padre)** - Dependencias comunes, versiones

---

**Última actualización:** 2026-05-13  
**Mantenedor:** EcoMarket Team

