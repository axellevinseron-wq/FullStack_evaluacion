package com.example.ms_pagos.Repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ms_pagos.Model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long> {
}