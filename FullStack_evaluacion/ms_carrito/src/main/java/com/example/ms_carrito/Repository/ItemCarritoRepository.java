package com.example.ms_carrito.Repository;



import com.example.ms_carrito.Model.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Long> {
}