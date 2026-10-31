# 🏢 Excusas S.A. — Sistema de Gestión de Excusas

Sistema desarrollado en Java para automatizar la gestión de excusas por llegadas tarde dentro de una empresa ficticia. Las excusas atraviesan distintos sectores organizacionales hasta que un responsable las procesa o se determina su rechazo.

El proyecto fue realizado en el marco de un **parcial de Diseño de Sistemas de Software**, aplicando programación orientada a objetos, patrones de diseño y desarrollo guiado por pruebas (TDD).

## 🎯 Objetivo

Diseñar e implementar un sistema flexible y desacoplado que permita gestionar excusas laborales mediante una cadena configurable de encargados, cada uno responsable de un tipo de excusa.

La solución contempla distintos niveles organizacionales, el envío de notificaciones por email, la generación de prontuarios y la posibilidad de modificar el comportamiento de los encargados según su desempeño laboral.

## 📏 Reglas de diseño

- **Todas las clases implementan una interfaz.**
- **Sin `if`, `else` ni `instanceof`** en el código del sistema: las decisiones se resuelven con polimorfismo, doble despacho y tablas.
- **Todo orientado a objetos:** cada objeto decide lo que le corresponde (*Tell, Don't Ask*).

## ⚙️ Funcionalidades principales

- **Tipos de excusa:** trivial, moderada (corte de luz o cuidado familiar), compleja e inverosímil.
- **Procesamiento por sectores:** recepcionista, supervisora de área, gerente de RRHH y CEO.
- **Cadena configurable:** el orden de los encargados se define al armar la línea.
- **Rechazo final:** si nadie la procesa, la excusa se rechaza con *"Excusa rechazada: necesitamos pruebas contundentes"*.
- **Notificaciones por email:** al empleado, a EDESUR o al CTO, según el caso.
- **Prontuarios:** el CEO genera un prontuario por cada excusa inverosímil aceptada, y se informa al equipo de dirección.
- **Modos de desempeño:** productivo, normal o vago, con cambios por café, break, cantidad de excusas procesadas y día siguiente.

## 🧠 Patrones de diseño

### Builder (tipos de excusa)
Una `Excusa` tiene un `TipoExcusa` (`Trivial`, `Moderada` → `CorteLuz` / `CuidadoFamiliar`, `Compleja`, `Inverosimil`). Cada tipo sabe qué método del encargado invocar (**doble despacho**), así ningún encargado pregunta de qué tipo es la excusa.

### Chain of Responsibility
`Encargado` implementa `EncargadoI` y conoce a su `siguiente`. Por defecto, todo encargado deriva; cada uno sobrescribe solo el tipo de excusa que le corresponde. El último eslabón es siempre un `Rechazador`, que evita preguntar si hay siguiente. `EncargadoI` cumple el rol de *ExcusaController* con el método `manejar(excusa)`.

### Builder (línea de encargados)
`Constructor` (que implementa `LineaBuild`) arma la `LineaDeEncargados` en el orden indicado y coloca el `Rechazador` al final:

```java
new Constructor()
        .agregar(recepcionista)
        .agregar(ceo)
        .construir()
        .manejar(excusa);
```

### Template Method
`Encargado.procesar(excusa)` fija los pasos: aceptar → `notificar` → `registrar` → contar la excusa. Cada encargado completa `notificar`, y solo el CEO sobrescribe el gancho `registrar` para generar el prontuario.

### Strategy
Cada encargado tiene un `Delivery` con su `Modo` actual:
- **Productivo:** evalúa la excusa y, si no puede, avisa al CTO antes de derivarla.
- **Normal:** evalúa la excusa y, si no puede, la deriva.
- **Vago:** no la evalúa y la deriva directamente.

Los cambios de modo se resuelven sin condicionales: una lista indexada por cantidad de excusas procesadas (menos de 5 → Productivo, 5 a 10 → Normal, 11 o más → Vago), un mapa de minutos válidos para el break (10 a 15) y un `Azar` para el 50% del día siguiente.

### Singleton
`AdministradorProntuario` tiene una única instancia que centraliza la persistencia de los prontuarios y evita inconsistencias.

### Observer
`AdministradorProntuario` implementa `Observable`: al persistir un prontuario notifica a los `Observador` registrados (`EquipoDireccion` y `CEO`), sin conocer sus detalles.

## 🧩 Principios aplicados

- **Open/Closed:** se pueden agregar encargados, tipos de excusa y modos sin modificar los existentes.
- **Tell, Don't Ask** y **polimorfismo** en lugar de condicionales.
- **Bajo acoplamiento:** las dependencias apuntan a interfaces, lo que permite reemplazarlas por mocks en los tests.
- **DRY:** el Template Method concentra el procesamiento común de todos los encargados.

## 🛠️ Tecnologías

- **Java 17**
- **Apache Maven**
- **JUnit 5**
- **Mockito** (mocks de las interfaces en los tests)
- **Git y GitHub**

## 🧪 Pruebas

Desarrollado con **TDD**: **95 tests** con `@DisplayName` numerados (T01 a T76, más un test parametrizado de 20 combinaciones encargado × tipo de excusa). Cobertura de líneas: **92%**.

Cubren, entre otros:
- La excusa y sus tipos (doble despacho).
- Qué acepta cada encargado y qué deriva.
- El rechazo al final de la cadena.
- Los emails enviados en cada caso.
- El armado y el orden configurable de la línea.
- Los modos de desempeño y sus transiciones.
- El Singleton, los prontuarios y los avisos a los observadores.

## 🚀 Cómo ejecutar

### Requisitos
- JDK 17 o superior (recomendado 21)
- Apache Maven
- IntelliJ IDEA u otro IDE para Java

### Clonar el repositorio
```bash
git clone https://github.com/vickyycm/Excusas_SA.git
cd Excusas_SA
```

### Ejecutar las pruebas
```bash
mvn test
```

### Ejecutar la demo
Correr la clase `ar.edu.davinci.Main`, que muestra por consola el recorrido de distintas excusas por la cadena.

## 📐 Diagrama UML

El diagrama de clases, con los patrones señalados, se encuentra en `docs/UML.png`.

## 🎓 Contexto académico

Proyecto desarrollado para un parcial de **Diseño de Sistemas de Software**, con foco en diseño orientado a objetos, patrones de diseño y pruebas automatizadas.

---

**Repositorio:** [github.com/vickyycm/Excusas_SA](https://github.com/vickyycm/Excusas_SA)
