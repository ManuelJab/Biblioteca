# Instrucciones de Instalacion y Ejecucion

## Requisitos Previos

### 1. Instalar Java JDK 25
- Descargar desde: https://www.oracle.com/java/technologies/downloads/
- Asegurarse de instalar el JDK (no solo el JRE)
- Configurar la variable de entorno JAVA_HOME

### 2. Instalar MySQL
- Descargar desde: https://dev.mysql.com/downloads/installer/
- Durante la instalacion, configurar:
  - Usuario: root
  - Contrasena: root
  - Puerto: 3306

## Pasos para Ejecutar el Proyecto

### Opcion 1: Usando el archivo ejecutar.bat (Mas facil)
Simplemente haz doble clic en el archivo `ejecutar.bat` en la carpeta del proyecto.

### Opcion 2: Desde PowerShell

#### 1. Verificar Java
Abrir una terminal y ejecutar:
```
java -version
```
Debe mostrar version 25 o superior

#### 2. Configurar JAVA_HOME (solo la primera vez)
En PowerShell como Administrador:
```powershell
[System.Environment]::SetEnvironmentVariable('JAVA_HOME', 'C:\Program Files\Java\jdk-25', 'User')
$currentPath = [System.Environment]::GetEnvironmentVariable('Path', 'User')
$newPath = $currentPath + ';C:\Program Files\Java\jdk-25\bin'
[System.Environment]::SetEnvironmentVariable('Path', $newPath, 'User')
```

Luego cierra y vuelve a abrir PowerShell.

#### 3. Iniciar MySQL
- Asegurarse de que el servicio MySQL este corriendo
- Puedes verificarlo en Servicios de Windows

#### 4. Ejecutar la Aplicacion
Abrir una terminal en la carpeta del proyecto y ejecutar:
```
.\mvnw.cmd spring-boot:run
```

Nota: En PowerShell necesitas usar `.\` antes del comando.

### 4. Acceder a la Aplicacion
- Abrir el navegador en: http://localhost:8080
- Seras redirigido a la pagina de login

## Usuarios de Prueba

La aplicacion crea automaticamente estos usuarios:

### Administrador
- Usuario: admin
- Contrasena: admin123
- Puede: agregar, editar, eliminar libros y ver prestamos

### Usuario Normal
- Usuario: user
- Contrasena: user123
- Puede: ver catalogo, buscar libros y solicitar prestamos

## Estructura del Proyecto

```
src/main/java/com/example/demo/
├── model/              # Entidades (Usuario, Libro, Prestamo)
├── repository/         # Acceso a base de datos
├── service/            # Logica de negocio
├── controller/         # Controladores web
└── config/             # Configuracion de seguridad

src/main/resources/
├── templates/          # Vistas HTML
│   ├── admin/         # Vistas de administrador
│   └── usuario/       # Vistas de usuario
└── application.properties  # Configuracion
```

## Funcionalidades Implementadas

### Para Administradores (ROLE_ADMIN)
1. Ver dashboard con todos los libros
2. Agregar nuevos libros al catalogo
3. Editar libros existentes
4. Eliminar libros del catalogo
5. Ver todos los prestamos activos
6. Ver lista de usuarios con prestamos

### Para Usuarios (ROLE_USER)
1. Ver catalogo completo de libros
2. Buscar libros por titulo o autor
3. Solicitar prestamo de libros disponibles
4. Ver sus prestamos activos
5. Consultar fechas de devolucion

## Caracteristicas de Seguridad

- Contrasenas encriptadas con BCrypt
- Autenticacion basada en roles
- Sesiones seguras con Spring Security
- Proteccion de rutas segun rol

## Notas Importantes

- Los prestamos tienen duracion de 15 dias
- Al solicitar un prestamo, se reduce automaticamente el numero de ejemplares
- Solo se pueden solicitar prestamos si hay ejemplares disponibles
- La base de datos se crea automaticamente al iniciar la aplicacion
- Se agregan 5 libros de ejemplo al iniciar por primera vez

## Solucion de Problemas

### Error: "JAVA_HOME no esta definido correctamente"
- Ejecutar en PowerShell como Administrador:
  ```powershell
  [System.Environment]::SetEnvironmentVariable('JAVA_HOME', 'C:\Program Files\Java\jdk-25', 'User')
  ```
- Cerrar y volver a abrir PowerShell
- O usar el archivo ejecutar.bat que configura todo automaticamente

### Error de conexion a MySQL
- Verificar que MySQL este corriendo
- Verificar usuario y contrasena en application.properties
- Verificar que el puerto 3306 este disponible

### Puerto 8080 ocupado
- Cambiar el puerto en application.properties:
  ```
  server.port=8081
  ```
