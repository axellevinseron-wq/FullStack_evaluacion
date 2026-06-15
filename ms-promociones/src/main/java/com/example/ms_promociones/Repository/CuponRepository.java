package com.example.ms_promociones.Repository;



import com.example.ms_promociones.Model.Cupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuponRepository extends JpaRepository<Cupon, Long> {
}