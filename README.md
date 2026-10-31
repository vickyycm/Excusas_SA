
# 🏢 Excusas S.A. — Sistema de Gestión de Excusas

Sistema desarrollado en Java para automatizar la gestión de excusas por llegadas tarde dentro de una empresa ficticia. Las excusas atraviesan distintos sectores organizacionales hasta que un responsable las procesa o se determina su rechazo.

El proyecto fue realizado en el marco de un **parcial de Diseño de Sistemas de Software**, aplicando principios de Programación Orientada a Objetos, patrones de diseño y buenas prácticas de desarrollo.

## 🎯 Objetivo

Diseñar e implementar un sistema flexible y desacoplado que permita gestionar excusas laborales mediante una cadena configurable de responsables, cada uno con criterios específicos para evaluar los motivos presentados.

La solución contempla distintos niveles organizacionales, el envío de notificaciones por email, la generación de prontuarios y la posibilidad de modificar temporalmente el comportamiento de los responsables según su desempeño laboral.

## ⚙️ Funcionalidades principales

- **Gestión de excusas:** registro del empleado, el motivo de la demora y el estado de aceptación.
- **Procesamiento por sectores:** cada responsable evalúa si puede procesar una excusa o si debe derivarla.
- **Cadena organizacional configurable:** permite definir el orden en el que intervienen los responsables.
- **Notificaciones por email:** envío de mensajes según el motivo de la excusa y la decisión del responsable.
- **Administración de prontuarios:** centralización de los registros especiales generados por determinadas decisiones.
- **Comportamientos dinámicos:** modificación del desempeño de los responsables entre los modos productivo, normal y vago.
- **Pruebas automatizadas:** utilización de JUnit 5 para verificar los comportamientos del sistema.

## 🧠 Patrones de diseño

### Chain of Responsibility

Permite que una excusa pase de un responsable al siguiente hasta encontrar uno que pueda procesarla. La cadena es configurable para modificar el orden de intervención e incorporar nuevos responsables sin tener que modificar la lógica de los existentes.

### Strategy

Permite representar los distintos modos de desempeño laboral de los responsables:

- **Productivo:** evalúa la excusa y, si no puede procesarla, la deriva después de notificar al CTO.
- **Normal:** actúa según el comportamiento esperado de su rol.
- **Vago:** no evalúa la excusa y la deriva al siguiente responsable.

Este patrón permite agregar nuevos modos de desempeño sin modificar la lógica central de los responsables.

### Observer

Permite notificar a otros componentes cuando ocurre un evento relevante en el sistema. Se aplica a la necesidad de mantener informado al equipo de dirección cuando se genera un nuevo prontuario, evitando que el administrador tenga que conocer directamente a todos los interesados en recibir las notificaciones.

### Singleton

Permite garantizar una única instancia compartida de un componente que centraliza una responsabilidad. En este proyecto, se utiliza para representar la centralización de la gestión de prontuarios mediante un administrador específico, evitando inconsistencias en los registros.

## 🎮 Excusa Controller

La interfaz `ExcusaController`, con el método `manejar(excusa)`, define un punto de entrada para iniciar el procesamiento de una excusa sin acoplar al cliente con los detalles internos de la cadena de responsables.

Esta abstracción facilita la separación entre quien solicita el procesamiento y los componentes que se encargan de resolverlo.

## 🧩 Principios de diseño aplicados

- **SOLID:** separación de responsabilidades, abstracciones e independencia entre componentes.
- **Open/Closed Principle:** posibilidad de agregar nuevos responsables y modos de desempeño sin modificar el comportamiento existente.
- **Tell, Don't Ask:** delegación de decisiones en los objetos responsables de ejecutarlas.
- **DRY (Don't Repeat Yourself):** reducción de lógica duplicada mediante abstracciones y reutilización de comportamiento.
- **Bajo acoplamiento:** uso de interfaces para facilitar la extensión y las pruebas.
- **Polimorfismo:** cada responsable y modo de desempeño define su comportamiento particular.

## 🛠️ Tecnologías utilizadas

- **Java 17:** lenguaje de programación.
- **Apache Maven:** gestión del proyecto y sus dependencias.
- **JUnit 5:** desarrollo y ejecución de pruebas automatizadas.
- **Programación Orientada a Objetos:** organización del sistema mediante clases, interfaces y polimorfismo.
- **Git y GitHub:** control de versiones y alojamiento del código fuente.

## 🧪 Pruebas

El proyecto contempla el enfoque **Test-Driven Development (TDD)** y el uso de pruebas automatizadas para verificar el comportamiento del sistema.

Entre los comportamientos relevantes para probar se encuentran:

- Aceptación de excusas por el responsable correspondiente.
- Derivación de excusas al siguiente sector.
- Rechazo cuando ningún responsable puede procesar una excusa.
- Envío de emails según el tipo de excusa.
- Generación y administración de prontuarios.
- Cambios de comportamiento según el modo de desempeño.
- Configuración del orden de los responsables.
- Incorporación de nuevos responsables y estrategias.

## 🚀 Cómo ejecutar el proyecto

### Requisitos previos

- JDK 17 o superior.
- Apache Maven.
- Un IDE compatible con proyectos Java, como IntelliJ IDEA.

### Clonar el repositorio

```bash
git clone https://github.com/vickyycm/Excusas.git
cd Excusas
```

### Ejecutar las pruebas

```bash
mvn test
```

### Compilar el proyecto

```bash
mvn clean package
```

## 🎓 Contexto académico

Proyecto desarrollado para un parcial de **Diseño de Sistemas de Software**, con foco en el diseño orientado a objetos, los patrones de diseño, los principios SOLID y las pruebas automatizadas.

La propuesta busca resolver un problema de negocio mediante una arquitectura extensible, mantenible y desacoplada, preparada para incorporar nuevos responsables y comportamientos sin rediseñar el sistema completo.

---

**Repositorio:** [github.com/vickyycm/Excusas](https://github.com/vickyycm/Excusas)
