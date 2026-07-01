# 🚀 Guía de Despliegue Remoto - EcoMarket (Railway)

Esta guía cubre el despliegue de **al menos un entorno remoto**, tal como pide la rúbrica
(IE 3.3.1 / IE 3.3.6). Se usa **Railway** porque construye directamente desde un `Dockerfile`
con build-args, que es exactamente el patrón que ya usa este proyecto. Al final se incluye la
alternativa con Render.

> Necesitas tu propia cuenta (gratuita) en https://railway.app — este es un paso que **debes
> ejecutar tú**, ya que requiere tus credenciales y no puedo hacerlo por ti desde aquí.

## Estrategia recomendada para la defensa

No hace falta desplegar los 10 microservicios remotamente para cumplir la pauta (pide "al menos
un entorno remoto" y que "sea parte del alcance definido por el docente"). Lo más sólido para tu
defensa individual es desplegar **tus 3 servicios propios** (ms-catalogo, ms-pedidos,
ms-notificaciones) + el api-gateway, y dejar el resto corriendo local con Docker Compose. Así
puedes explicar con propiedad cada paso.

## 1. Preparar el repositorio

```bash
git add .
git commit -m "feat: agrega tests unitarios, swagger, api-gateway y config yaml"
git push origin main
```

Railway se conecta directo a GitHub y reconstruye automáticamente en cada push.

## 2. Crear el proyecto en Railway

1. Entra a https://railway.app → **New Project** → **Deploy from GitHub repo**.
2. Autoriza Railway y selecciona tu repositorio `FullStack_evaluacion`.
3. Railway va a detectar el `Dockerfile` en la raíz. **No dejes que construya automáticamente
   todavía** — primero hay que configurar las variables de build (`MODULE`, `JAR_NAME`) porque el
   `Dockerfile` es genérico y sirve para cualquiera de los 10 microservicios + gateway.

## 3. Agregar una base de datos MySQL administrada

1. Dentro del proyecto Railway: **New** → **Database** → **Add MySQL**.
2. Railway te entrega automáticamente las variables `MYSQLHOST`, `MYSQLPORT`, `MYSQLUSER`,
   `MYSQLPASSWORD`, `MYSQLDATABASE`. Las vas a mapear a `SPRING_DATASOURCE_*` en el paso 5.
3. Conéctate una vez con un cliente MySQL (o el mismo Railway Data tab) y crea las bases de datos
   necesarias, igual que hace `mysql-init/create-databases.sql` localmente:
   ```sql
   CREATE DATABASE IF NOT EXISTS ms_catalogo;
   CREATE DATABASE IF NOT EXISTS ms_pedidos;
   CREATE DATABASE IF NOT EXISTS ms_notificaciones;
   ```

## 4. Desplegar ms-catalogo (repetir el mismo patrón para ms-pedidos y ms-notificaciones)

1. **New** → **GitHub Repo** (el mismo repo, un servicio Railway por microservicio).
2. En **Settings → Build**:
   - Builder: `Dockerfile`
   - Dockerfile Path: `Dockerfile`
   - Build Args:
     - `MODULE=ms-catalogo`
     - `JAR_NAME=ms-catalogo-1.0.0`
3. En **Variables**, agrega:
   ```
   SPRING_PROFILES_ACTIVE=prod
   PORT=8080
   SPRING_DATASOURCE_URL=jdbc:mysql://${{MySQL.MYSQLHOST}}:${{MySQL.MYSQLPORT}}/ms_catalogo?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
   SPRING_DATASOURCE_USERNAME=${{MySQL.MYSQLUSER}}
   SPRING_DATASOURCE_PASSWORD=${{MySQL.MYSQLPASSWORD}}
   ```
   (La sintaxis `${{MySQL.VARIABLE}}` referencia automáticamente el servicio de base de datos que
   creaste en el paso 3 — Railway resuelve la referencia por ti.)
4. En **Settings → Networking**, activa **Generate Domain** para obtener una URL pública tipo
   `ms-catalogo-production.up.railway.app`.
5. Repite exactamente lo mismo para `ms-pedidos` (build args `MODULE=ms-pedidos`,
   `JAR_NAME=ms-pedidos-1.0.0`, DB `ms_pedidos`) y para `ms-notificaciones` (build args
   `MODULE=ms_notificaciones`, `JAR_NAME=ms_notificaciones-1.0.0`, DB `ms_notificaciones`).

## 5. Desplegar el api-gateway

1. **New** → **GitHub Repo** (mismo repo, nuevo servicio).
2. Build Args: `MODULE=api-gateway`, `JAR_NAME=api-gateway-1.0.0`.
3. Variables:
   ```
   SPRING_PROFILES_ACTIVE=prod
   PORT=8080
   MS_CATALOGO_URL=https://ms-catalogo-production.up.railway.app
   MS_PEDIDOS_URL=https://ms-pedidos-production.up.railway.app
   MS_NOTIFICACIONES_URL=https://ms-notificaciones-production.up.railway.app
   ```
   (Usa las URLs reales que Railway generó en el paso 4 para cada servicio. Los demás
   `MS_*_URL` del perfil `prod` — carrito, usuarios, pagos, envíos, clientes, promociones,
   inventario — puedes dejarlos apuntando a `http://localhost:<puerto>` o desplegarlos también si
   tu equipo decide subir los 10.)
4. Activa **Generate Domain** también aquí. Esa URL pública es la que muestras en la defensa como
   punto único de entrada: `https://api-gateway-production.up.railway.app/api/catalogo`.

## 6. Verificar que todo quedó operativo

```bash
curl https://ms-catalogo-production.up.railway.app/api/catalogo
curl https://ms-catalogo-production.up.railway.app/swagger-ui.html
curl https://api-gateway-production.up.railway.app/api/catalogo
```

## Alternativa: Render

El proceso es equivalente:
1. **New → Web Service** → conecta el repo → Runtime: **Docker**.
2. En **Docker Build Context Directory**: `.` — en **Dockerfile Path**: `Dockerfile`.
3. Render no soporta build-args por UI tan directo como Railway; la forma más simple es crear
   un `Dockerfile` específico por servicio (copiar `Dockerfile` a `Dockerfile.catalogo` con
   `ARG MODULE=ms-catalogo` y `ARG JAR_NAME=ms-catalogo-1.0.0` ya fijados como valores por
   defecto) y apuntar cada Web Service de Render a su propio Dockerfile.
4. Crea una base de datos MySQL administrada aparte (Render no ofrece MySQL gratis nativo — se
   suele usar un addon externo como **PlanetScale** o **Railway MySQL** apuntado desde Render).
5. Variables de entorno: mismas `SPRING_DATASOURCE_*`, `SPRING_PROFILES_ACTIVE=prod`, `PORT`.

## Qué explicar en la defensa (IE 3.3.6)

- Qué plataforma usaste y por qué (Dockerfile + build-args = mismo patrón que ya tenían local).
- Cómo se inyectan las credenciales de BD (variables de entorno, perfil `prod`, nunca hardcodeadas).
- Qué puerto expone cada contenedor y cómo Railway/Render lo mapea a una URL pública.
- Cómo el api-gateway centraliza el acceso una vez desplegado.
- Qué error tuviste (si tuviste alguno) al desplegar y cómo lo resolviste — revisa los logs
  del build en la pestaña **Deployments** de Railway si algo falla.
