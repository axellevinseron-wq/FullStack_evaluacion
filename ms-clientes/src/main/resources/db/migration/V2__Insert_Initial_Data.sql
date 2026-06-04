-- MS-Clientes: Insertar datos iniciales
INSERT INTO cliente (nombre, email, telefono) VALUES
('Juan Pérez', 'juan@example.com', '123456789'),
('María García', 'maria@example.com', '987654321');

INSERT INTO direccion (cliente_id, calle, ciudad, codigo_postal, pais) VALUES
(1, 'Calle Principal 123', 'Madrid', '28001', 'España'),
(2, 'Avenida Central 456', 'Barcelona', '08002', 'España');
