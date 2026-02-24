# Sistema de Gestion de Biblioteca

Este es un proyecto de Spring Boot para gestionar una biblioteca con prestamos de libros.

## Requisitos

- Java 25
- MySQL 8.0 o superior
- Maven

## Configuracion

1. Crear una base de datos MySQL llamada `biblioteca_db` (o la aplicacion la creara automaticamente)

2. Configurar las credenciales de MySQL en `application.properties`:
   - Usuario: root
   - Contrasena: root
   - Puerto: 3306

3. Si tus credenciales son diferentes, edita el archivo `src/main/resources/application.properties`

## Como ejecutar

1. Abrir una terminal en la carpeta del proyecto

2. Ejecutar el comando:
   ```
   mvnw spring-boot:run
   ```

3. Abrir el navegador en: http://localhost:8080

## Usuarios de prueba

Los usuarios NO se crean automaticamente. Debes crearlos manualmente:

### Opcion 1: Usar el script SQL (Recomendado)
1. Ejecutar la aplicacion una vez para que cree las tablas
2. Ejecutar el archivo `usuarios_ejemplo.sql` en MySQL:
   ```bash
   mysql -u root -p biblioteca_db < usuarios_ejemplo.sql
   ```
   
Esto creara:
- Admin: `admin` / `admin123` (ROLE_ADMIN)
- Usuario: `user` / `user123` (ROLE_USER)

### Opcion 2: Usar la pagina de registro
1. Ir a http://localhost:8080/registro
2. Crear usuarios desde el formulario web

### Opcion 3: Insertar manualmente en MySQL
```sql
-- Insertar usuario
INSERT INTO usuarios (username, password) 
VALUES ('tu_usuario', '$2a$10$...');  -- Contrasena encriptada con BCrypt

-- Asignar rol
INSERT INTO roles (usuario_id, rol) 
VALUES (LAST_INSERT_ID(), 'ADMIN');  -- o 'USER'
```

## Funcionalidades

### Administrador (ROLE_ADMIN)
- Agregar, editar y eliminar libros del catalogo
- Ver todos los libros
- Ver lista de usuarios con prestamos activos
- Ver todos los prestamos activos

### Usuario (ROLE_USER)
- Ver catalogo de libros
- Buscar libros por titulo o autor
- Solicitar prestamo de libros (si hay ejemplares disponibles)
- Ver sus prestamos activos
- Consultar estado de sus prestamos

## Estructura del proyecto

- `model/` - Entidades de la base de datos (Usuario, Libro, Prestamo)
- `repository/` - Interfaces para acceder a la base de datos
- `service/` - Logica de negocio
- `controller/` - Controladores web
- `config/` - Configuracion de seguridad y datos iniciales
- `templates/` - Vistas HTML con Thymeleaf

## Notas

- Los prestamos tienen una duracion de 15 dias
- Cuando se solicita un prestamo, se reduce automaticamente el numero de ejemplares disponibles
- Solo se pueden solicitar prestamos si hay ejemplares disponibles
- Las contrasenas se guardan encriptadas con BCrypt
