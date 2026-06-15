package com.example.ms_pedidos.Repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ms_pedidos.Model.DetallePedido;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
}