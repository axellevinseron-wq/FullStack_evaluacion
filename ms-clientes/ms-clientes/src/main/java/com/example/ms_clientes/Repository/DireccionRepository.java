package com.example.ms_clientes.Repository;


import com.example.ms_clientes.Model.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {
}