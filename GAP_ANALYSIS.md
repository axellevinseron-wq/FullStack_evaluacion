# 🔴 GAP ANALYSIS - EcoMarket vs Rúbrica

## 1. PERSISTENCIA Y CRUD (IE 2.1.2)

**Estado:** ms-inventario y ms-promociones VACÍOS ❌

### Mínimo obligatorio para flujo funcional:

**ms-inventario:**
- Entidad: `Inventario { id, productId, stock, disponible, ultimaActualizacion }`
- Endpoints:
  - `POST /api/inventario/reservar` → valida stock, decrementa
  - `GET /api/inventario/producto/{id}` → consulta disponibilidad
  - `PUT /api/inventario/liberar` → devuelve stock

**ms-promociones:**
- Entidad: `Promocion { id, nombre, descuento, productos[], vigencia }`
- Endpoints:
  - `GET /api/promociones` → lista activas
  - `GET /api/promociones/producto/{id}` → descuento aplicable
  - `POST /api/promociones` → crear (admin)

**Critical path:**
- ms-carrito → ms-catalogo ✅ (Feign listo)
- ms-carrito → ms-inventario ❌ (PENDIENTE)
- ms-pedidos → ms-inventario ❌ (PENDIENTE)

---

## 2. VALIDACIÓN Y DTOs (IE 2.2.2)

**Estado:** ⚠️ Parcial - 7/10 DTOs sin validaciones completas

### Auditoría actual:
- ✅ ItemCarritoDTO, CartItemDTO → @NotNull, @Min
- ❌ ProductoDTO → falta @NotBlank nombre, @Min precio
- ❌ DetallePedidoDTO, PedidoDTO → sin validaciones
- ❌ CategoriaDTO → falta @NotBlank
- ❌ InventarioDTO, PromocionDTO → NO EXISTEN

### Arreglos inmediatos:

**ProductoDTO (ms-catalogo):**
```java
@Data
public class ProductoDTO {
    private Long id;
    @NotBlank(message = "Nombre es obligatorio")
    private String nombre;
    @NotNull @Min(0) private Double precio;
}
```

**InventarioDTO (ms-inventario):**
```java
@Data
public class InventarioDTO {
    @NotNull private Long productId;
    @NotNull @Min(0) private Integer stock;
}
```

**PromocionDTO (ms-promociones):**
```java
@Data
public class PromocionDTO {
    @NotBlank private String nombre;
    @NotNull @Min(0) @Max(100) private Double descuento;
    private List<Long> productosIds;
}
```

---

## 3. TRAZABILIDAD (IE 2.3.2)

**Estado:** ✅ ms-pedidos tiene @Slf4j, pero INSUFICIENTE

### 2 puntos críticos SIN logs:

**Punto 1: Fallos de comunicación Feign (ms-carrito)**
- Cuando ms-catalogo NO responde → sin fallback, sin logs
- **Solución:** Agregar `ProductoFeignClientFallback` con `log.error()`

**Punto 2: Errores de reserva (ms-inventario - cuando exista)**
```java
public void reservar(Long productId, int cantidad) {
    if (stock < cantidad) {
        log.warn("STOCK INSUFICIENTE - ProductoID: {}, Disponible: {}", 
            productId, stock);
    }
}
```

**Punto 3: Validaciones fallidas (todos controllers)**
```java
@PostMapping
public void crear(@Valid @RequestBody CartItemDTO item) {
    log.info("POST /api/carrito - ProductoID: {}", item.getProductId());
}
```

---

## 4. RESILIENCIA (IE 2.4.1)

**Estado:** ❌ SIN configuración Feign

### Problema: 
ProductoFeignClient → sin timeouts, sin reintentos, sin fallback → crash si ms-catalogo demora >1s

### Solución mínima:

**1. pom.xml (ms_carrito) - agregar:**
```xml
<dependency>
    <groupId>io.github.resilience4j</groupId>
    <artifactId>resilience4j-spring-boot3</artifactId>
</dependency>
```

**2. application.properties (ms_carrito):**
```properties
feign.client.config.default.connectTimeout=5000
feign.client.config.default.readTimeout=5000
resilience4j.circuitbreaker.instances.ms-catalogo.slidingWindowSize=10
resilience4j.circuitbreaker.instances.ms-catalogo.failureRateThreshold=50
```

**3. ProductoFeignClient - agregar fallback:**
```java
@FeignClient(name = "ms-catalogo", 
    url = "http://localhost:8080",
    fallback = ProductoFeignClientFallback.class)
public interface ProductoFeignClient {
    @GetMapping("/api/catalogo/productos/{id}")
    ProductoDTO obtenerProductoPorId(@PathVariable("id") Long id);
}

@Component @Slf4j
public class ProductoFeignClientFallback implements ProductoFeignClient {
    @Override
    public ProductoDTO obtenerProductoPorId(Long id) {
        log.error("FALLBACK - ms-catalogo DOWN. ProductID: {}", id);
        throw new BusinessException("CATALOGO_TIMEOUT", "Catálogo no disponible");
    }
}
```

---

## 5. DOCUMENTACIÓN TÉCNICA (README.md)

**Estado:** ❌ NO EXISTE

### Crear PROYECTO/README.md con:

**Secciones obligatorias:**
1. **Descripción General** - qué es EcoMarket, stack
2. **Requisitos** - Docker, Maven, Java 17
3. **Instalación** - mvn clean package, docker-compose up
4. **Endpoints críticos** - tabla con 3+ servicios
5. **Patrones** - Resiliencia (Feign), Trazabilidad (@Slf4j), Validación
6. **Monitoreo** - cómo ver logs
7. **Troubleshooting** - 3+ problemas comunes

### Ejemplo mínimo:
```markdown
# EcoMarket - Microservicios

## Stack
- Java 17, Spring Boot 4.0.6, MySQL 8
- Docker Compose, OpenFeign, SLF4J

## Quick Start
mvn clean package && docker-compose up -d

## Endpoints
- ms-catalogo (8081): GET /api/catalogo
- ms-carrito (8083): POST /api/carrito [Feign→catalogo]
- ms-pedidos (8082): PUT /api/pedidos/{id}/estado

## Logs
docker-compose logs -f ms-pedidos | grep PAGADO

## Forma de entrega
- Docker Compose ejecutable
- DB autoiniCializada (Flyway)
```

---

## RESUMEN EJECUTIVO - PRIORIDAD DE ARREGLOS

| Crítico | Acción | Impacto |
|---------|--------|--------|
| 🔴 Alto | Crear ms-inventario/ms-promociones CRUD | Flujo compra completo |
| 🔴 Alto | Fallback + logs en Feign (ms-carrito) | Resiliencia (IE 2.4.1) |
| 🟡 Medio | Validaciones DTOs (ProductoDTO, etc) | Validación (IE 2.2.2) |
| 🟡 Medio | Logs en ms-inventario (cuando exista) | Trazabilidad (IE 2.3.2) |
| 🟢 Bajo | README.md completo | Documentación forma entrega |

