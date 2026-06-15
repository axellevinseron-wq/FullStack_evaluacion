package com.example.ms_catalogo.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ms_catalogo.Model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}