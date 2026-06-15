package com.example.ms_notificaciones.Repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ms_notificaciones.Model.Mensaje;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
}