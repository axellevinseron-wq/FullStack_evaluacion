# 📊 TABLA DE ACCESO - EcoMarket Microservicios
**Documento para la defensa/presentación - 15 de Mayo 2026**

---

## 1️⃣ PUERTOS Y UBICACIÓN DE MICROSERVICIOS

| Microservicio | Puerto Host | Puerto Interno (App) | Estado | Docker Name |
|---|---|---|---|---|
| **MS-Catálogo** | `8081` | 8080 | ✅ Up | `ecomarket_ms_catalogo` |
| **MS-Pedidos** | `8082` | 8080 | ✅ Up | `ecomarket_ms_pedidos` |
| **MS-Carrito** | `8083` | 8080 | ✅ Up | `ecomarket_ms_carrito` |
| **MySQL DB** | `3306` | 3306 | ✅ Up (healthy) | `ecomarket_mysql_db` |
| **phpMyAdmin** | `8080` | 80 | ✅ Up | `ecomarket_phpmyadmin` |

---

## 2️⃣ URLS DE PRUEBA EN EL NAVEGADOR

### 🛒 **MS-CATÁLOGO** (Puerto 8081)
**Base Path:** `http://localhost:8081`

| Endpoint | Método | URL Completa | Descripción |
|---|---|---|---|
| **Obtener Categorías** | GET | `http://localhost:8081/api/catalogo` | Lista todas las categorías |
| **Crear Categoría** | POST | `http://localhost:8081/api/catalogo` | Crea nueva categoría (JSON body) |
| **Obtener Producto por ID** | GET | `http://localhost:8081/api/catalogo/productos/1` | Obtiene producto específico |

**Prueba rápida GET:**
```
http://localhost:8081/api/catalogo
```

---

### 📦 **MS-PEDIDOS** (Puerto 8082)
**Base Path:** `http://localhost:8082`

| Endpoint | Método | URL Completa | Descripción |
|---|---|---|---|
| **Listar Todos los Pedidos** | GET | `http://localhost:8082/api/pedidos` | Lista todos los pedidos |
| **Obtener Pedido por ID** | GET | `http://localhost:8082/api/pedidos/1` | Obtiene un pedido específico |
| **Crear Nuevo Pedido** | POST | `http://localhost:8082/api/pedidos` | Crea nuevo pedido (JSON body) |
| **Cambiar Estado Pedido** | PUT | `http://localhost:8082/api/pedidos/1/estado?estado=COMPLETADO` | Actualiza estado |

**Prueba rápida GET:**
```
http://localhost:8082/api/pedidos
```

---

### 🛍️ **MS-CARRITO** (Puerto 8083)
**Base Path:** `http://localhost:8083`

| Endpoint | Método | URL Completa | Descripción |
|---|---|---|---|
| **Listar Items del Carrito** | GET | `http://localhost:8083/api/carrito` | Lista todos los items |
| **Agregar Item al Carrito** | POST | `http://localhost:8083/api/carrito` | Agrega nuevo item (JSON body) |

**Prueba rápida GET:**
```
http://localhost:8083/api/carrito
```

---

## 3️⃣ DOCUMENTACIÓN DE API (Swagger)

❌ **Estado:** No configurado
- **Descripción:** No hay dependencias de Swagger/SpringFox instaladas en el proyecto
- **Alternativa:** Los endpoints están documentados en este archivo y en el código fuente
- **Recomendación para futuro:** Agregar `springdoc-openapi-starter-webmvc-ui` al pom.xml para auto-documentación

---

## 4️⃣ COMANDOS DOCKER LOGS PARA DEBUGGING

### 📋 Comando 1: Ver logs en tiempo real de MS-Catálogo
```bash
docker logs -f ecomarket_ms_catalogo
```
**Uso:** Ejecuta esto si hay error al acceder a `http://localhost:8081/api/catalogo`

---

### 📋 Comando 2: Ver logs en tiempo real de MS-Pedidos
```bash
docker logs -f ecomarket_ms_pedidos
```
**Uso:** Ejecuta esto si hay error al acceder a `http://localhost:8082/api/pedidos`

---

### 📋 Comando 3: Ver logs en tiempo real de MS-Carrito
```bash
docker logs -f ecomarket_ms_carrito
```
**Uso:** Ejecuta esto si hay error al acceder a `http://localhost:8083/api/carrito`

---

### 📋 Comando 4: Ver últimas 50 líneas de logs de todos
```bash
docker logs ecomarket_ms_catalogo 2>&1 | tail -20 && \
docker logs ecomarket_ms_pedidos 2>&1 | tail -20 && \
docker logs ecomarket_ms_carrito 2>&1 | tail -20
```
**Uso:** Diagnóstico rápido simultáneo de los 3 servicios

---

### 📋 Comando 5: Verificar salud de la BD y containers
```bash
docker ps && docker exec ecomarket_mysql_db mysql -u root -proot -e "SHOW DATABASES LIKE 'ms_%';"
```
**Uso:** Confirmar que todos los containers están Up y las BDs existen

---

## 5️⃣ VERIFICACIÓN PRE-PRESENTACIÓN (CHECKLIST)

Antes de tu defensa, ejecuta esto en orden:

```bash
# 1. Verificar que todos los containers están UP
docker ps

# 2. Verificar las bases de datos existen
docker exec ecomarket_mysql_db mysql -u root -proot -e "SHOW DATABASES LIKE 'ms_%';"

# 3. Probar conectividad MS-Catálogo
curl http://localhost:8081/api/catalogo

# 4. Probar conectividad MS-Pedidos
curl http://localhost:8082/api/pedidos

# 5. Probar conectividad MS-Carrito
curl http://localhost:8083/api/carrito
```

✅ Si todos los comandos responden sin "error" → **Sistema listo para presentar**

---

## 6️⃣ JSON DE EJEMPLO PARA POST (Body)

### Crear Categoría (MS-Catálogo)
```json
{
  "nombre": "Electrónica",
  "descripcion": "Productos electrónicos en general"
}
```

### Crear Pedido (MS-Pedidos)
```json
{
  "clienteId": 1,
  "estado": "PENDIENTE"
}
```

### Crear Item Carrito (MS-Carrito)
```json
{
  "productoId": 1,
  "cantidad": 2,
  "precioUnitario": 19.99
}
```

---

## 7️⃣ PUERTOS UTILIZADOS - RESUMEN RÁPIDO

```
localhost:8081  ← MS-Catálogo     [/api/catalogo]
localhost:8082  ← MS-Pedidos      [/api/pedidos]
localhost:8083  ← MS-Carrito      [/api/carrito]
localhost:8080  ← phpMyAdmin      [Interfaz gráfica BD]
localhost:3306  ← MySQL           [Acceso directo BD]
```

---

## 8️⃣ ACCESO RÁPIDO PARA COPIAR-PEGAR EN NAVEGADOR

| Servicio | URL |
|---|---|
| Catálogo | `http://localhost:8081/api/catalogo` |
| Pedidos | `http://localhost:8082/api/pedidos` |
| Carrito | `http://localhost:8083/api/carrito` |
| phpMyAdmin | `http://localhost:8080` |

---

**Última actualización:** 15 de Mayo 2026, 04:22 UTC  
**Estado del Sistema:** ✅ OPERACIONAL

