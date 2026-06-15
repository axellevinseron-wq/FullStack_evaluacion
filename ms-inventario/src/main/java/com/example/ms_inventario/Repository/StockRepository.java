package com.example.ms_inventario.Repository;

import com.example.ms_inventario.Model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByProductoId(Long productoId);
}
