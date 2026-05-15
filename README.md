# PROPER
## Sistema de Gestión de Ventas de Productos de Aseo

---

## 📌 Descripción 
Sistema desarrollado mediante arquitectura de microservicios para gestionar:

- Clientes
- Empresas
- Productos
- Pedidos
- Bonificaciones
- Vendedores

---

# 💻 Tecnologías Utilizadas
| Categoría | Tecnologías |
|---|---|
| 🔧 Backend | Java 21, Spring Boot, Maven, WebClient |
| 🗄️ Base de Datos | MySQL, Oracle SQL Developer Data Modeler, XAMPP |
| 👥 Desarrollo Colaborativo / Entorno de Desarrollo | Git, GitHub, Visual Studio Code |
| 🧪 Testing y APIs | Postman, APIs REST / JSON |
---

# 🧩 Microservicios
| Microservicio | Puerto |
|---|---|
| service-productos | 8082 |
| service-cliente | 8083 |
| service-vendedor | 8084 |
| service-pedido | 8085 |
| service-bonificacion | 8086 |
| api-gateway | 9090 |

---

# 🔌 Endpoints Principales

## Service-Producto
### Productos
``` http://localhost:8082/api/v1/productos ```

``` http://localhost:8082/api/v1/productos/{id} ```

| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/v1/productos | Obtener productos |
| GET | /api/v1/productos/{id} | Obtener producto por ID |
| POST | /api/v1/productos | Crear producto |
| PUT | /api/v1/productos/{id} | Actualizar producto |
| DELETE | /api/v1/productos/{id} | Eliminar producto |

### Líneas
``` http://localhost:8082/api/v1/productos/linea ```

``` http://localhost:8082/api/v1/productos/linea/{id} ```

| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/v1/lineas | Obtener líneas |
| GET | /api/v1/lineas/{id} | Obtener línea por ID |
| POST | /api/v1/lineas | Crear línea |
| PUT | /api/v1/lineas/{id} | Actualizar línea |
| DELETE | /api/v1/lineas/{id} | Eliminar línea |

## Service-Cliente
### Clientes
``` http://localhost:8083/api/v1/clientes ```

``` http://localhost:8083/api/v1/clientes/{id} ```

| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/v1/clientes | Obtener clientes |
| GET | /api/v1/clientes/{id} | Obtener cliente por ID |
| POST | /api/v1/clientes | Crear cliente |
| PUT | /api/v1/clientes/{id} | Actualizar cliente |
| DELETE | /api/v1/clientes/{id} | Eliminar cliente |

### Empresas
``` http://localhost:8083/api/v1/clientes/empresas ```

``` http://localhost:8083/api/v1/clientes/empresas/{id} ```

| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/v1/empresas | Obtener empresas |
| GET | /api/v1/empresas/{id} | Obtener empresa por ID |
| POST | /api/v1/empresas | Crear empresa |
| PUT | /api/v1/empresas/{id} | Actualizar empresa |
| DELETE | /api/v1/empresas/{id} | Eliminar empresa |

## Service-Vendedor
### Vendedores
``` http://localhost:8084/api/v1/vendedores ```

``` http://localhost:8084/api/v1/vendedores/{id} ```

| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/v1/vendedores | Obtener vendedores |
| GET | /api/v1/vendedores/{id} | Obtener vendedor por ID |
| POST | /api/v1/vendedores | Crear vendedor |
| PUT | /api/v1/vendedores/{id} | Actualizar vendedor |
| DELETE | /api/v1/vendedores/{id} | Eliminar vendedor |

## Service-Pedido
### Pedidos
``` http://localhost:8085/api/v1/pedido ```

``` http://localhost:8085/api/v1/pedido/{id} ```

| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/v1/pedido | Obtener pedidos |
| GET | /api/v1/pedido/{id} | Obtener pedido por ID |
| POST | /api/v1/pedido | Crear pedido |
| PUT | /api/v1/pedido/{id} | Actualizar pedido |
| DELETE | /api/v1/pedido/{id} | Eliminar pedido |

> Los registros de detalle-pedido se generan, actualizan y eliminan automáticamente al crear, modificar o eliminar un pedido.

## Service-Bonificacion
### Bonificaciones
``` http://localhost:8086/api/v1/bonificaciones ```

``` http://localhost:8086/api/v1/bonificaciones/{id} ```

> Las bonificaciones se generan, actualizan y eliminan automáticamente al crear, modificar o eliminar un pedido

## API Gateway
``` http://localhost:9090/api/v1/ ```

---
# 🔗 Principales Funcionalidades

- CRUD de productos  
- CRUD de clientes  
- CRUD de vendedores
- CRUD de pedidos
- Cálculo automático de subtotales  
- Generación automática de bonificaciones  
- Comunicación entre microservicios

---
# ⚙️ Requisitos Previos

Antes de ejecutar el proyecto, es obligatorio contar con los siguientes programas instalados:
- Java JDK 21
- XAMPP
- Visual Studio Code
- Postman
- Git
- Apache Maven

Además, se recomienda configurar correctamente las variables de entorno de Java y Maven.
--- 
# ▶️ Ejecución del Proyecto 
Luego de clonar el proyecto desde GitHub y contar con las tecnologías necesarias instaladas, se deben seguir los siguientes pasos:

## 1. Iniciar XAMPP
Abrir XAMPP y activar los módulos:
- Apache
- MySQL
Posteriormente, presionar el botón **Admin** de MySQL para acceder a phpMyAdmin

## 2. Ejecutar todos los microservicios
Levantar todos los microservicios Spring Boot junto con el API Gateway para permitir la correcta comunicación entre servicios.

### Puertos utilizados:
| Servicio | Puerto |
|---|---|
| API Gateway | 9090 |
| service-productos | 8082 |
| service-cliente | 8083 |
| service-vendedor | 8084 |
| service-pedido | 8085 |
| service-bonificacion | 8086 |

## 3. Importar Bases de Datos
Este paso es opcional, ya que el proyecto incluye scripts SQL con bases de datos previamente pobladas para facilitar las pruebas.

Para importarlas:

1. Ingresar a:
```txt
http://localhost/phpmyadmin/
```

2. Crear las bases de datos correspondientes.

3. Importar cada archivo `.sql` entregado en el proyecto.

## 4. Probar Endpoints en Postman
Abrir Postman y utilizar los endpoints documentados anteriormente para comprobar el correcto funcionamiento de los microservicios y la comunicación entre ellos.
