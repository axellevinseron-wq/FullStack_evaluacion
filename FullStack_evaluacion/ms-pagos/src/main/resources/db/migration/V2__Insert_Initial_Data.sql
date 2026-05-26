-- MS-Pagos: Insertar datos iniciales
INSERT INTO pago (pedido_id, monto, metodo_pago, estado) VALUES
(1, 150.00, 'Tarjeta', 'Aprobado'),
(2, 250.50, 'Transferencia', 'Pendiente');
