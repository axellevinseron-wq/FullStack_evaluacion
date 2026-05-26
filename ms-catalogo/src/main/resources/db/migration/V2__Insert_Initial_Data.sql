-- MS-Catálogo: Insertar datos iniciales
INSERT INTO categoria (nombre, descripcion) VALUES
('Electrónica', 'Productos electrónicos en general'),
('Ropa', 'Prendas de vestir'),
('Libros', 'Libros y material de lectura');

INSERT INTO producto (nombre, precio, categoria_id) VALUES
('Laptop', 1200.00, 1),
('Smartphone', 800.00, 1),
('Camiseta', 25.00, 2),
('Novela de Ficción', 15.00, 3);
