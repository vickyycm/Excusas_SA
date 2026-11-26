# 🏢 Excusas S.A. — API REST de Gestión de Excusas

API REST desarrollada en Java con Spring Boot para automatizar la gestión de excusas por llegadas tarde dentro de una empresa ficticia. Las excusas atraviesan distintos sectores organizacionales hasta que un responsable las procesa o se determina su rechazo.

El proyecto fue realizado en el marco de la materia **Diseño de Sistemas de Software**, partiendo del modelo de la primera entrega y llevándolo a una API REST con persistencia, aplicando programación orientada a objetos, patrones de diseño y desarrollo guiado por pruebas (TDD).

## 🎯 Objetivo

Exponer el sistema de gestión de excusas como una API REST en tres capas (controllers, services y models), con persistencia en base de datos, DTOs, validaciones y manejo de errores con los códigos HTTP correspondientes.

La solución mantiene la cadena configurable de encargados, el envío de notificaciones por email, la generación de prontuarios y los modos de desempeño, y agrega la posibilidad de sumar encargados nuevos y cambiar su modo en tiempo real.

## 🌿 Ramas del repositorio

- **`main`:** primera entrega (modelo en Java puro).
- **`parcial2`:** API REST con base de datos **H2** en memoria (desarrollo y pruebas).
- **`parcial2-productiva`:** misma API con base de datos **MySQL** (perfil `prod` activo).

## 🌐 Endpoints

Todas las solicitudes y respuestas son en formato JSON.

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/empleados` | Lista los empleados |
| POST | `/empleados` | Registra un empleado |
| POST | `/excusas` | Registra una excusa y la evalúa automáticamente con la cadena de encargados |
| GET | `/excusas/{legajo}` | Lista las excusas de un empleado |
| GET | `/excusas` | Lista las excusas con filtros opcionales: `fechaDesde`, `fechaHasta`, `motivo`, `encargado` |
| GET | `/excusas/busqueda?legajo=&fechaDesde=&fechaHasta=` | Busca las excusas de un legajo entre fechas |
| GET | `/excusas/rechazadas` | Lista las excusas rechazadas |
| DELETE | `/excusas/eliminar?fechaLimite=` | Elimina las excusas anteriores a la fecha y devuelve cuántas se eliminaron. Sin `fechaLimite` no se ejecuta y devuelve un error claro |
| GET | `/prontuarios` | Lista los prontuarios |
| GET | `/encargados` | Lista los encargados con su orden y su modo |
| POST | `/encargados` | Agrega un encargado nuevo con los motivos que atiende (por ejemplo, "Coach Motivacional") |
| PUT | `/encargados/modo` | Cambia el modo de un encargado (`NORMAL`, `VAGO`, `PRODUCTIVO`) |

### Códigos de respuesta

- **200 / 201:** operación exitosa.
- **400:** datos inválidos, motivo o modo inexistente, fecha mal escrita, JSON mal formado o parámetro obligatorio faltante.
- **404:** empleado o encargado inexistente.
- **409:** legajo duplicado.

Todos los errores responden con el mismo formato (`status`, `error`, `mensaje`, `fecha`), centralizado en `ManejadorDeErrores`.

## 🗂️ Arquitectura

- **controller:** recibe las solicitudes HTTP y devuelve DTOs.
- **service:** lógica de negocio. Coordina el modelo con los repositorios.
- **repository:** acceso a datos con Spring Data JPA.
- **model:** dominio y patrones de diseño (primera entrega adaptada).
- **dto:** objetos de entrada y salida de la API (`records`).
- **mapper:** convierte entre DTOs y objetos del modelo.
- **exception:** excepciones propias y manejador global de errores.

## 🛠️ Tecnologías

- **Java 21**
- **Spring Boot 4.1.2** (Web MVC, Data JPA, Validation)
- **H2** (desarrollo y pruebas) y **MySQL** (rama productiva)
- **Apache Maven**
- **JUnit 6** y **Mockito**
- **Git y GitHub**

## 🧪 Pruebas

Desarrollado con **TDD**: **141 tests** con `@DisplayName` numerados (T01 a T119, más tests parametrizados). Cobertura de líneas: **97%**.

Cubren, entre otros:
- El modelo de la primera entrega: tipos de excusa, encargados, cadena, modos, Singleton y Observer.
- Los enums `Motivo` y `ModoTrabajo` y la creación de sus objetos.
- Los servicios, con Mockito.
- Todos los endpoints de la API con MockMvc: respuestas correctas, validaciones y códigos de error.

## 📐 Diagramas

En el archivo de diagramas se incluyen:
- Diagrama de clases del modelo con sus patrones.
- Diagrama de casos de uso de la API.
- Diagrama de arquitectura con el flujo de punta a punta de `POST /excusas`.
