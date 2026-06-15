package com.example.ms_carrito.Repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ms_carrito.Model.ItemCarrito;

public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Long> {
}