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
- Pagos
- Inventario
- Facturación
- Despachos
- Reportes

---

# 💻 Tecnologías Utilizadas

| Categoría | Tecnologías |
|---|---|
| 🔧 **Backend & Microservicios** | Java 21, Spring Boot 3.x, Spring Cloud Gateway (API Gateway), Spring Data JPA (Hibernate), Maven, Spring WebFlux (`WebClient`), Lombok |
| 🗄️ **Base de Datos** | MySQL, Oracle SQL Developer Data Modeler, XAMPP (phpMyAdmin) |
| 👥 **Entorno y Control** | Git, GitHub, Visual Studio Code |
| 🧪 **Testing y Documentación** | Postman, Swagger / OpenAPI 3 (Documentación de APIs), APIs REST / JSON |

---

# 🧩 Microservicios
| Microservicio | Puerto |
|---|---|
| service-auth | 8081 |
| service-productos | 8082 |
| service-cliente | 8083 |
| service-vendedor | 8084 |
| service-pedido | 8085 |
| service-bonificacion | 8086 |
| service-pago | 8087 |
| service-inventario | 8088 |
| service-facturacion | 8089 |
| service-despacho | 8090 |
| service-reporte | 8091 |
| api-gateway | 9090 |

---

# 🔌 Endpoints Principales

## Service-Auth
### Autenticación

```http://localhost:8081/auth/registrar```

```http://localhost:8081/auth/login```

| Método | Endpoint | Descripción |
|---|---|---|
| POST | /auth/registrar | Registrar usuario |
| POST | /auth/login | Iniciar sesión y obtener token JWT |

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

## Service-Pago
### Pagos

``` http://localhost:8087/api/v1/pagos ```

``` http://localhost:8087/api/v1/pagos/{id} ```

| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/v1/pagos | Obtener pagos |
| GET | /api/v1/pagos/{id} | Obtener pago por ID |
| POST | /api/v1/pagos | Crear pago |
| PUT | /api/v1/pagos/pedido/{pedidoId}/estado | Actualizar estado y método de pago |
| DELETE | /api/v1/pagos/pedido/{pedidoId} | Eliminar pago asociado a un pedido |

> Los pagos se generan automáticamente al crear un pedido, inicializándose con estado **PENDIENTE**.

> Al actualizar un pedido, el monto del pago se actualiza automáticamente.

> Al eliminar un pedido, el pago asociado también se elimina automáticamente.

## Service-Inventario
### Inventario

```http://localhost:8088/api/v1/inventario```

```http://localhost:8088/api/v1/inventario/{id}```

| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/v1/inventario | Obtener todo el inventario |
| GET | /api/v1/inventario/{id} | Obtener registro de inventario por ID |
| GET | /api/v1/inventario/producto/{productoId} | Obtener inventario por ID de producto |
| POST | /api/v1/inventario | Crear registro de inventario |
| PUT | /api/v1/inventario/{id} | Actualizar inventario |
| PUT | /api/v1/inventario/producto/{productoId}/descontar | Descontar stock de un producto |
| DELETE | /api/v1/inventario/{id} | Eliminar registro de inventario |
| DELETE | /api/v1/inventario/producto/{productoId} | Eliminar inventario asociado a un producto |

> El inventario permite controlar el stock disponible de cada producto.

> Cada registro se encuentra asociado a un producto mediante su identificador.

> El sistema permite descontar existencias en función de los pedidos realizados.

> Se almacena la fecha de última actualización para facilitar el seguimiento de movimientos de stock.

## Service-Facturacion
### Facturación

```http://localhost:8089/api/v1/facturacion```

```http://localhost:8089/api/v1/facturacion/{id}```

| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/v1/facturacion | Obtener facturas |
| GET | /api/v1/facturacion/{id} | Obtener factura por ID |
| POST | /api/v1/facturacion | Generar factura |
| PUT | /api/v1/facturacion/{id} | Actualizar factura |
| DELETE | /api/v1/facturacion/{id} | Eliminar factura |

> Al momento en que un pedido pasa a **PAGADO**, la facturación se crea automáticamente

## Service-Despacho
### Despachos

```http://localhost:8090/api/v1/despachos```

```http://localhost:8090/api/v1/despachos/{id}```

| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/v1/despachos | Obtener todos los despachos |
| GET | /api/v1/despachos/{id} | Obtener despacho por ID |
| POST | /api/v1/despachos | Crear despacho |
| PUT | /api/v1/despachos/pedido/{pedidoId}/estado | Actualizar estado de despacho |
| DELETE | /api/v1/despachos/pedido/{pedidoId} | Eliminar despacho asociado a un pedido |

> Los despachos se generan automáticamente al crear un pedido.

> La dirección de entrega se obtiene desde la empresa asociada al cliente.

> La fecha estimada de entrega se calcula automáticamente.

## Service-Reporte
### Reportes

```http://localhost:8091/api/v1/reportes```

```http://localhost:8091/api/v1/reportes/{id}```

| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/v1/reportes | Obtener reportes generados |
| GET | /api/v1/reportes/{id} | Obtener reporte por ID |
| POST | /api/v1/reportes/generar | Generar reporte por rango de fechas |
| DELETE | /api/v1/reportes/{id} | Eliminar reporte |

> Los reportes consolidan información de pedidos, pagos, bonificaciones y despachos.

> Incluyen estadísticas de desempeño por vendedor.

> Los reportes almacenan el período analizado mediante fechaInicio y fechaFin.

## API Gateway
``` http://localhost:9090/api/v1/ ```

---
# 🔗 Principales Funcionalidades

- CRUD de productos
- CRUD de líneas de productos
- CRUD de clientes
- CRUD de empresas
- CRUD de vendedores
- CRUD de pedidos
- Gestión de inventario
- Gestión de pagos
- Gestión de despachos
- Gestión de facturación
- Generación de reportes comerciales
- Autenticación mediante JWT
- Cálculo automático de subtotales
- Generación automática de bonificaciones
- Generación automática de pagos
- Generación automática de despachos
- Actualización automática de inventario
- Estadísticas por vendedor
- Comunicación entre microservicios mediante WebClient

---
# ⚙️ Requisitos Previos

Antes de ejecutar el proyecto, es necesario contar con las siguientes herramientas instaladas:

## Software

- Java JDK 21
- Apache Maven 3.9 o superior
- MySQL Server 8.x
- XAMPP (opcional para administración mediante phpMyAdmin)
- Git
- Visual Studio Code o IntelliJ IDEA
- Postman

## Dependencias utilizadas

- Spring Boot 3.x
- Spring Data JPA
- Spring Web
- Spring WebFlux (WebClient)
- Spring Security
- JWT (JSON Web Token)
- Lombok
- Hibernate
- MySQL Connector
- Spring Cloud Gateway
- OpenAPI / Swagger

--- 
# ▶️ Ejecución del Proyecto

Luego de clonar el proyecto desde GitHub y contar con las tecnologías necesarias instaladas, se deben seguir los siguientes pasos:

## 1. Iniciar XAMPP

Abrir XAMPP y activar los módulos:

- Apache
- MySQL

Posteriormente, presionar el botón **Admin** de MySQL para acceder a phpMyAdmin.

## 2. Ejecutar todos los microservicios

Levantar todos los microservicios Spring Boot junto con el API Gateway para permitir la correcta comunicación entre servicios.

### Puertos utilizados:

| Servicio | Puerto |
|---|---|
| service-auth | 8081 |
| service-productos | 8082 |
| service-cliente | 8083 |
| service-vendedor | 8084 |
| service-pedido | 8085 |
| service-bonificacion | 8086 |
| service-pago | 8087 |
| service-inventario | 8088 |
| service-facturacion | 8089 |
| service-despacho | 8090 |
| service-reporte | 8091 |
| API Gateway | 9090 |

## 3. Importar Bases de Datos

Este paso es opcional, ya que el proyecto incluye scripts SQL con bases de datos previamente pobladas para facilitar las pruebas.

Para importarlas:

1. Ingresar a:

```http://localhost/phpmyadmin/```

2. Crear las bases de datos correspondientes.

3. Importar cada archivo `.sql` entregado en el proyecto.

## 4. Registrar Usuario e Iniciar Sesión

Antes de acceder a los endpoints protegidos, se debe registrar un usuario mediante el microservicio de autenticación.

### Registrar Usuario

```POST http://localhost:8081/auth/registrar```

### Iniciar Sesión

```POST http://localhost:8081/auth/login```

El sistema retornará un token JWT que podrá ser utilizado para acceder a los recursos protegidos.

## 5. Probar Endpoints

Abrir Postman y utilizar los endpoints documentados anteriormente para comprobar el correcto funcionamiento de los microservicios y la comunicación entre ellos.

Se recomienda utilizar el API Gateway para centralizar las solicitudes:

```http://localhost:9090/```

A través de las pruebas es posible validar:

- Gestión de productos y líneas
- Gestión de clientes y empresas
- Gestión de vendedores
- Gestión de pedidos
- Generación automática de bonificaciones
- Generación automática de pagos
- Gestión de inventario y control de stock
- Generación automática de despachos
- Gestión de facturación
- Generación de reportes comerciales
- Estadísticas por vendedor
- Comunicación entre microservicios mediante WebClient
- Autenticación mediante JWT

## 6. Acceder a Swagger

Cada microservicio dispone de documentación Swagger/OpenAPI para facilitar la visualización y prueba de los endpoints.

Ejemplos:

```http://localhost:9090/swagger-ui.html```

Swagger permite probar los endpoints directamente desde el navegador sin necesidad de utilizar herramientas externas.
---

# 🧪 Datos de Prueba

## Línea

```json
{
  "nombre": "Linea Prueba"
}
```

---

## Producto

```json
{
  "productoNombre": "Producto Prueba",
  "precio": 2500,
  "linea": {
    "lineaId": 1
  }
}
```

---

## Empresa

```json
{
  "razonSocial": "Empresa Prueba SpA",
  "direccionEmpresa": "Av. Test 123, Santiago",
  "telefonoEmpresa": "+56911112222"
}
```

---
## Cliente

```json
{
  "runCliente": "22.222.222-2",
  "nombreCliente": "Cliente",
  "apellidoCliente": "Prueba",
  "telefonoCliente": "+56933334444",
  "correoCliente": "cliente.prueba@test.cl",
  "empresa": {
    "empresaId": 1
  }
}
```

---

## Vendedor

```json
{
  "runVendedor": "11.111.111-1",
  "nombreVendedor": "Vendedor",
  "apellidoVendedor": "Prueba",
  "telefonoVendedor": "+56922223333",
  "correoVendedor": "vendedor.prueba@test.cl"
}
```

---

## Pedido

```json
{
  "fecha": "2026-05-16",
  "estado": "COMPLETADO",
  "vendedorId": 1,
  "clienteId": 1,
  "detalles": [
    {
      "cantidad": 3,
      "productoId": 1
    }
  ]
}
```

---

## Bonificación

> Las bonificaciones se generan automáticamente al crear un pedido.

---

## Pago

> Los pagos se generan automáticamente al crear un pedido.

---

## Inventario

```json
{
  "stockActual": 100,
  "stockMinimo": 20,
  "fechaActualizacion": "2026-06-21",
  "productoId": 1,
  "pedidoId": null
}
```

---

## Facturación

> Las facturas pueden generarse a partir de los pagos registrados en el sistema.

---

## Despacho

> Los despachos se generan automáticamente al crear un pedido.

---

## Reporte

```json
{
  "fechaInicio": "2026-06-01",
  "fechaFin": "2026-06-30"
}
```

> Este JSON se utiliza para generar un reporte mediante:

```POST /api/v1/reportes/generar```

---

## Usuario (Auth)
### Registro

```json
{
  "nombreUsuario": "admin",
  "contrasena": "123456"
}
```

### Login

```json
{
  "nombreUsuario": "admin",
  "contrasena": "123456"
}
```

---
# 👥 Integrantes
- Josefina Isidora Pérez Huerta
- Benjamín Elías Pérez Alfaro
