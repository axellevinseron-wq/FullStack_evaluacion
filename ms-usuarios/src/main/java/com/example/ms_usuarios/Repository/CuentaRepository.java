package com.example.ms_usuarios.Repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ms_usuarios.Model.Cuenta;
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
}