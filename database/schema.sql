-- ============================================
-- SCHEMA COMPLETO DE LA BASE DE DATOS
-- Sistema de Biblioteca Digital
-- ============================================

-- Crear base de datos si no existe
CREATE DATABASE IF NOT EXISTS biblioteca_db 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE biblioteca_db;

-- ============================================
-- TABLA: usuarios
-- ============================================
DROP TABLE IF EXISTS prestamos;
DROP TABLE IF EXISTS usuarios;

CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol ENUM('ROLE_ADMIN', 'ROLE_USER') NOT NULL DEFAULT 'ROLE_USER',
    INDEX idx_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insertar usuarios iniciales
-- Contraseñas encriptadas con BCrypt
-- admin: admin123
-- usuario: usuario123
INSERT INTO usuarios (username, password, rol) VALUES 
('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhCu', 'ROLE_ADMIN'),
('usuario', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhCu', 'ROLE_USER');

-- ============================================
-- TABLA: libros
-- ============================================
DROP TABLE IF EXISTS libros;

CREATE TABLE libros (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    isbn VARCHAR(50) UNIQUE,
    ejemplares_disponibles INT NOT NULL DEFAULT 0,
    imagen_url VARCHAR(500),
    pdf_url VARCHAR(500),
    INDEX idx_titulo (titulo),
    INDEX idx_autor (autor),
    INDEX idx_isbn (isbn)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insertar libros iniciales
INSERT INTO libros (titulo, autor, isbn, ejemplares_disponibles, imagen_url, pdf_url) VALUES
('Cien años de soledad', 'Gabriel García Márquez', '978-0307474728', 5, '/uploads/portada_default.jpg', '/uploads/libro_default.pdf'),
('Don Quijote de la Mancha', 'Miguel de Cervantes', '978-8420412146', 3, '/uploads/portada_default.jpg', '/uploads/libro_default.pdf'),
('1984', 'George Orwell', '978-0451524935', 4, '/uploads/portada_default.jpg', '/uploads/libro_default.pdf'),
('El principito', 'Antoine de Saint-Exupéry', '978-0156012195', 6, '/uploads/portada_default.jpg', '/uploads/libro_default.pdf'),
('Rayuela', 'Julio Cortázar', '978-8420471891', 2, '/uploads/portada_default.jpg', '/uploads/libro_default.pdf'),
('La sombra del viento', 'Carlos Ruiz Zafón', '978-8408163381', 4, '/uploads/portada_default.jpg', '/uploads/libro_default.pdf'),
('Crónica de una muerte anunciada', 'Gabriel García Márquez', '978-0307387349', 3, '/uploads/portada_default.jpg', '/uploads/libro_default.pdf'),
('El amor en los tiempos del cólera', 'Gabriel García Márquez', '978-0307387738', 5, '/uploads/portada_default.jpg', '/uploads/libro_default.pdf');

-- ============================================
-- TABLA: prestamos
-- ============================================
CREATE TABLE prestamos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    libro_id BIGINT NOT NULL,
    fecha_prestamo DATE NOT NULL,
    fecha_devolucion DATE NOT NULL,
    estado VARCHAR(50) NOT NULL DEFAULT 'ACTIVO',
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (libro_id) REFERENCES libros(id) ON DELETE CASCADE,
    INDEX idx_usuario (usuario_id),
    INDEX idx_libro (libro_id),
    INDEX idx_estado (estado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- VERIFICACIÓN
-- ============================================
SELECT '=== TABLAS CREADAS ===' as '';
SHOW TABLES;

SELECT '=== USUARIOS ===' as '';
SELECT id, username, rol FROM usuarios;

SELECT '=== LIBROS ===' as '';
SELECT id, titulo, autor, ejemplares_disponibles FROM libros;

SELECT '=== ESTRUCTURA USUARIOS ===' as '';
DESCRIBE usuarios;

SELECT '=== ESTRUCTURA LIBROS ===' as '';
DESCRIBE libros;

SELECT '=== ESTRUCTURA PRESTAMOS ===' as '';
DESCRIBE prestamos;
