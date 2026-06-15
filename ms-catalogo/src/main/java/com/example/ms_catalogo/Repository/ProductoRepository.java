package com.example.ms_catalogo.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ms_catalogo.Model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}