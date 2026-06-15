# 🐳 Docker Compose Setup - EcoMarket

## Requisitos previos
- Docker & Docker Compose instalados
- Maven 3.8+ (para compilar los JARs)
- Java 17+

## Pasos de setup

### 1. Compilar todos los microservicios
```bash
cd PROYECTO
mvn clean package -DskipTests
```

### 2. Verificar JARs generados
```bash
ls -la ms-catalogo/target/ms-catalogo-1.0.0.jar
ls -la ms-pedidos/target/ms-pedidos-1.0.0.jar
ls -la ms_carrito/target/ms_carrito-1.0.0.jar
```

### 3. Levantar contenedores
```bash
docker-compose up -d
```

### 4. Verificar servicios
```bash
docker-compose ps
```

## Acceso a servicios

| Servicio | URL | Puerto |
|----------|-----|--------|
| MySQL | localhost:3306 | 3306 |
| PhpMyAdmin | http://localhost:8080 | 8080 |
| ms-catalogo | http://localhost:8081 | 8081 |
| ms-pedidos | http://localhost:8082 | 8082 |
| ms-carrito | http://localhost:8083 | 8083 |

## Variables de entorno

SPRING_DATASOURCE_URL apunta internamente al contenedor `mysql:3306`:
```
jdbc:mysql://mysql:3306/ms_catalogo
```

**Nota:** Solo funciona dentro de la red `ecomarket-network`. Desde localhost usa `localhost:3306`.

## Comandos útiles

**Ver logs:**
```bash
docker-compose logs -f ms-catalogo
docker-compose logs -f ms-pedidos
docker-compose logs -f ms-carrito
```

**Detener contenedores:**
```bash
docker-compose down
```

**Detener y limpiar volúmenes:**
```bash
docker-compose down -v
```

**Reconstruir contenedores:**
```bash
docker-compose build --no-cache && docker-compose up -d
```

## Configuración de base de datos

Cada microservicio obtiene su propia BD gracias a Flyway:
- ms_catalogo
- ms_pedidos
- ms_carrito

Credenciales: root / root

## Red de comunicación

Todos los servicios están en la red `ecomarket-network`:
- ms-catalogo ↔ ms-carrito (vía Feign a `http://ms-catalogo:8080`)
- ms-pedidos ↔ ms-carrito (futuro)

## Troubleshooting

**Error "Connection refused":**
- Esperar a que MySQL esté listo (healthcheck ~30s)
- Ver logs: `docker-compose logs mysql`

**Puertos ya en uso:**
- Cambiar en docker-compose.yml: `"8081:8080"` → `"8091:8080"`

**BD no creada:**
- Verificar Flyway configuración en application.properties
- Ver logs de inicialización del contenedor
