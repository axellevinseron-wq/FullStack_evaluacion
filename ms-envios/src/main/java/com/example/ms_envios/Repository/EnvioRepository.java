package com.example.ms_envios.Repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ms_envios.Model.Envio;

public interface EnvioRepository extends JpaRepository<Envio, Long> {
}