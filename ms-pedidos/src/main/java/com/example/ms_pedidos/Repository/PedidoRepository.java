package com.example.ms_pedidos.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ms_pedidos.Model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}