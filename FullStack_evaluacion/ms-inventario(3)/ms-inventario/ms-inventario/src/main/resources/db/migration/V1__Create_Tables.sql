-- MS-Inventario: Crear tabla de Stock
CREATE TABLE IF NOT EXISTS stock (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL DEFAULT 0,
    cantidad_minima INT NOT NULL DEFAULT 10,
    ultima_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_producto_id ON stock(producto_id);
