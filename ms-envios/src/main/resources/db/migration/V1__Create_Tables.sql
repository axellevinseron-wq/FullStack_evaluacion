-- MS-Envíos: Crear tabla de Envíos
CREATE TABLE IF NOT EXISTS envio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    direccion VARCHAR(500) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    fecha_envio TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_pedido_id ON envio(pedido_id);
CREATE INDEX idx_estado ON envio(estado);
