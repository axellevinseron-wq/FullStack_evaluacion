package com.example.ms_promociones.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Cupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private Double descuento;
}