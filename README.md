# Olympics Tokyo 2020 - Sistema de Gestión

Sistema de gestión de deportistas, países y disciplinas para los Juegos Olímpicos de Tokio 2020. Desarrollado en 2021 por **Lisandro Vicens** y **Tomás E. Schattmann**.

## Descripción

Aplicación de escritorio desarrollada en Java que permite administrar información relacionada con los Juegos Olímpicos, incluyendo el registro y consulta de deportistas, países participantes y disciplinas deportivas. El proyecto evolucionó a través de dos versiones principales, desde una aplicación de consola hasta una interfaz gráfica completa.

## Tecnologías Utilizadas

- **Lenguaje:** Java
- **IDE:** Eclipse
- **Base de Datos:** MySQL 8.0.26
- **Servidor Local:** XAMPP
- **Framework GUI:** Java Swing
- **Driver JDBC:** MySQL Connector/J 8.0.26

## Estructura del Proyecto

El proyecto contiene dos versiones evolutivas:

### Versión 1.0 (Aplicación de Consola)
- Interfaz basada en línea de comandos
- Gestión básica de datos
- Conexión directa a base de datos MySQL

### Versión 2.0 (Aplicación con Interfaz Gráfica)
- Interfaz gráfica desarrollada con Java Swing
- Implementación del patrón DAO (Data Access Object)
- Sistema de autenticación de usuarios
- Gestión completa de deportistas, países y disciplinas
- Manejo de excepciones personalizadas

## Características Principales

### Versión 1.0

#### Paquetes y Componentes:
- **conexiones.servidor:**
  - `Conexion.java` - Gestión de conexiones a MySQL
  - `Servidor.java` - Lógica del servidor de datos

- **data:**
  - `Deportista.java` - Modelo de datos de deportistas
  - `Disciplina.java` - Modelo de disciplinas olímpicas
  - `AtletaEnDisciplina.java` - Relación deportista-disciplina
  - `Volatil.java` - Gestión de datos en memoria

- **interaccionUsuario:**
  - `Menu.java` - Sistema de menús de consola
  - `Frontend.java` - Interfaz de usuario en consola

- **ejecutables:**
  - `Ejecutable.java` - Punto de entrada de la aplicación

- **utiles:**
  - `Utiles.java` - Funciones auxiliares y utilidades

#### Funcionalidades:
- Registro de deportistas por consola
- Consulta de información de disciplinas
- Gestión básica de datos olímpicos
- Interacción mediante menús de texto

### Versión 2.0

#### Arquitectura Mejorada:

**1. Capa de Datos (data):**
- `Deportista.java` - Entidad de deportista con información completa (ID, nombre, apellido, email, teléfono, país, disciplina)
- `Pais.java` - Entidad de país con identificador y nombre
- `Disciplina.java` - Entidad de disciplina deportiva
- `AtletaEnDisciplina.java` - Tabla de relación entre deportistas y disciplinas
- `Volatil.java` - Gestión de datos temporales

**2. Capa de Acceso a Datos - DAO:**
- `DeportistaDAO.java` / `DeportistaDAOImp.java` - CRUD de deportistas
- `PaisDAO.java` / `PaisDAOImp.java` - CRUD de países
- `DisciplinaDAO.java` / `DisciplinaDAOImp.java` - CRUD de disciplinas
- `AtletaEnDisciplinaDAO.java` / `AtletaEnDisciplinaDAOImp.java` - Gestión de relaciones

**3. Capa de Conexión:**
- `Conexion.java` - Singleton para gestión de conexiones MySQL
- `Servidor.java` - Servicios de base de datos

**4. Interfaz Gráfica (gui):**

Ventanas principales:
- `LoginGUI.java` - Autenticación de usuarios con credenciales MySQL
- `MainGUI.java` - Menú principal con acceso a los módulos (Deportistas, Países, Disciplinas)
- `DeportistaGUI.java` - Listado y gestión de deportistas
- `IngresoDeportistaGUI.java` - Formulario de registro de nuevos deportistas
- `EdicionDeportistaGUI.java` - Modificación de datos de deportistas existentes
- `PaisGUI.java` - Gestión de países participantes
- `IngresoPaisGUI.java` - Registro de nuevos países
- `DisciplinaGUI.java` - Administración de disciplinas

Componentes de utilidad (gui/stuff):
- `Tabla.java` - Componente personalizado de tablas
- `TableRenderer.java` - Renderizado personalizado de celdas
- `ModeloTablaPais.java` - Modelo de datos para tabla de países
- `Render.java` - Utilidades de renderizado

**5. Excepciones Personalizadas:**
- `LoginErrorException.java` - Manejo de errores de autenticación
- `PaisNotFoundException.java` - Excepción cuando no se encuentra un país

**6. Recursos (resources):**
- `/gral/` - Iconos generales de la aplicación
- `/main/` - Imágenes para el menú principal (deportistas, disciplinas, países)
- `/login/` - Recursos de la pantalla de login
- `/ingresoDeportista/` - Imágenes para formularios de deportistas
- `/ingresoPais/` - Recursos para formularios de países

#### Funcionalidades Avanzadas:

**Sistema de Autenticación:**
- Login con credenciales de MySQL
- Validación de usuarios contra la base de datos
- Manejo de sesiones

**Gestión de Deportistas:**
- Registro completo con datos personales y país de origen
- Edición de información existente
- Asignación a disciplinas deportivas
- Visualización en tablas interactivas
- Búsqueda y filtrado

**Gestión de Países:**
- Alta de países participantes
- Listado con visualización tabular
- Asociación con deportistas

**Gestión de Disciplinas:**
- Administración de disciplinas olímpicas
- Asignación de deportistas a disciplinas
- Consulta de participantes por disciplina

**Interfaz de Usuario:**
- Diseño con temática olímpica (colores oficiales de Tokio 2020)
- Navegación intuitiva entre módulos
- Iconografía personalizada
- Tablas con renderizado personalizado
- Formularios validados

## Base de Datos

### Configuración:
- **Motor:** MySQL 8.0.26
- **Base de datos:** `tokyo2021_e3`
- **Host:** localhost:3306
- **Driver:** com.mysql.cj.jdbc.Driver

### Estructura (Inferida):
La base de datos incluye al menos las siguientes tablas:
- `deportistas` - Información de deportistas
- `paises` - Países participantes
- `disciplinas` - Disciplinas olímpicas
- `atleta_disciplina` - Relación muchos a muchos entre deportistas y disciplinas

## Instalación y Configuración

### Requisitos Previos:
1. Java Development Kit (JDK) 8 o superior
2. MySQL Server 8.0 o superior
3. XAMPP (recomendado) o servidor MySQL standalone
4. Eclipse IDE (opcional, para desarrollo)

### Pasos de Instalación:

1. **Clonar el repositorio:**
   ```bash
   git clone <url-del-repositorio>
   cd olympics-tokyo2020
   ```

2. **Configurar MySQL:**
   - Iniciar XAMPP o MySQL Server
   - Crear la base de datos `tokyo2021_e3`
   - Importar el esquema de base de datos (si está disponible)

3. **Configurar credenciales:**
   - Las credenciales de base de datos se ingresan en el login
   - Usuario y contraseña deben corresponder a un usuario válido de MySQL

4. **Ejecutar la aplicación:**

   **Versión 1.0:**
   ```bash
   java -jar olimpic-games-tokyo-v1.0.jar
   ```

   **Versión 2.0:**
   ```bash
   java -jar olimpic-games-tokyo-v2.0.jar
   ```

   O desde el código fuente:
   - Abrir el proyecto en Eclipse
   - Ejecutar `Launcher.java` (v2.0) o `Ejecutable.java` (v1.0)

## Uso de la Aplicación

### Versión 2.0 (Recomendada):

1. **Inicio de Sesión:**
   - Ingresar usuario y contraseña de MySQL
   - La aplicación validará las credenciales contra el servidor

2. **Menú Principal:**
   - Seleccionar el módulo deseado: Deportistas, Países o Disciplinas

3. **Gestión de Deportistas:**
   - Ver listado completo de deportistas
   - Agregar nuevo deportista con el botón "Ingresar"
   - Editar información seleccionando un deportista
   - Asociar deportistas con países y disciplinas

4. **Gestión de Países:**
   - Consultar países registrados
   - Agregar nuevos países participantes
   - Ver deportistas por país

5. **Gestión de Disciplinas:**
   - Administrar disciplinas olímpicas
   - Asignar deportistas a competencias

## Patrones de Diseño Implementados

- **DAO (Data Access Object):** Separación de la lógica de acceso a datos
- **Singleton:** Gestión única de la conexión a base de datos
- **MVC (Model-View-Controller):** Separación de datos, lógica y presentación
- **Factory:** Creación de objetos DAO

## Estructura de Directorios

```
olympics-tokyo2020/
├── README.md
├── olimpic-games-tokyo-v1.0.jar
├── olimpic-games-tokyo-v1.0/
│   ├── src/
│   │   ├── conexiones/servidor/
│   │   ├── data/
│   │   ├── ejecutables/
│   │   ├── interaccionUsuario/
│   │   └── utiles/
│   ├── bin/
│   └── lib/
│       └── mysql-connector-java-8.0.26.jar
├── olimpic-games-tokyo-v2.0.jar
└── olimpic-games-tokyo-v2.0/
    ├── src/
    │   ├── DAO/
    │   ├── conexiones/
    │   ├── data/
    │   ├── ejecutable/
    │   ├── excepciones/
    │   ├── gui/
    │   │   └── stuff/
    │   ├── resources/
    │   │   ├── gral/
    │   │   ├── login/
    │   │   ├── main/
    │   │   ├── ingresoDeportista/
    │   │   └── ingresoPais/
    │   └── utiles/
    ├── bin/
    └── lib/
        └── mysql-connector-java-8.0.26.jar
```

## Características Técnicas

### Manejo de Excepciones:
- Validación de entrada de usuario
- Manejo de errores de conexión a base de datos
- Excepciones personalizadas para casos específicos

### Seguridad:
- Autenticación mediante credenciales MySQL
- Validación de permisos de usuario
- Conexiones seguras a base de datos

### Interfaz Gráfica:
- Layout personalizado con posicionamiento absoluto
- Paleta de colores temática de Tokio 2020
- Componentes Swing personalizados
- Renderizado de imágenes optimizado

## Autores

- **Lisandro Vicens** - Desarrollo
- **Tomás E. Schattmann** - Desarrollo

## Año de Desarrollo

2021

## Licencia

Este proyecto fue desarrollado con fines educativos.

## Notas Adicionales

- El módulo de "Disciplinas" en la versión 2.0 puede estar parcialmente implementado
- Se recomienda usar la versión 2.0 por su interfaz gráfica y funcionalidades mejoradas
- La aplicación requiere que MySQL esté ejecutándose localmente
- Las credenciales de acceso son las mismas que las del usuario de MySQL configurado en el sistema
