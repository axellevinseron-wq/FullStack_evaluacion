package com.example.ms_clientes.Repository;


import com.example.ms_clientes.Model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}