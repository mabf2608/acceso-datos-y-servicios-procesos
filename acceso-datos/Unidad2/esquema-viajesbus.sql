-- 1. Crear la base de datos (opcional)
CREATE DATABASE IF NOT EXISTS gestion_viajes;
USE gestion_viajes;

-- 2. Crear la tabla de Clientes
-- Se crea primero porque la tabla de Reservas dependerá de ella.
CREATE TABLE clientes (
    codigo_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL
);

-- 3. Crear la tabla de Viajes
CREATE TABLE viajes (
    codigo_viaje INT AUTO_INCREMENT PRIMARY KEY,
    destino VARCHAR(100) NOT NULL,
    plazas_disponibles INT NOT NULL,
    total_plazas INT NOT NULL,
    fecha DATE NOT NULL
);

-- 4. Crear la tabla de Reservas
CREATE TABLE reservas (
    num_reserva INT AUTO_INCREMENT PRIMARY KEY,
    codigo_viaje INT NOT NULL,
    codigo_cliente INT NOT NULL,
    plazas_reservadas INT NOT NULL,
    estado CHAR(1) NOT NULL,
    FOREIGN KEY (codigo_viaje) REFERENCES viajes(codigo_viaje) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (codigo_cliente) REFERENCES clientes(codigo_cliente) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- ==========================================
-- PRECARGA DE DATOS DE EJEMPLO
-- ==========================================

-- Insertar Clientes (necesario para poder relacionar la reserva de ejemplo)
INSERT INTO clientes (codigo_cliente, nombre, telefono) VALUES 
(7, 'Juan Pérez', '600123456');

-- Insertar Viajes
INSERT INTO viajes (codigo_viaje, destino, plazas_disponibles, total_plazas, fecha) VALUES 
(1, 'Cuenca', 50, 50, '2026-11-15'),
(2, 'Sevilla', 100, 100, '2026-11-20'),
(3, 'Madrid', 60, 60, '2026-12-01');

-- Insertar Reserva de ejemplo (3, 1, 7, 2, 'A')
INSERT INTO reservas (num_reserva, codigo_viaje, codigo_cliente, plazas_reservadas, estado) VALUES 
(3, 1, 7, 2, 'A');
