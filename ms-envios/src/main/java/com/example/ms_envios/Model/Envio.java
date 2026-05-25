package com.example.ms_envios.Model;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // A qué pedido corresponde este envío
    private Long pedidoId;
    
    private String direccionDestino;
    private String empresaTransporte; // ej: "Starken", "Chilexpress"
    private String estado; // ej: "Preparando", "En camino", "Entregado"
    private String codigoSeguimiento;
    
    private LocalDateTime fechaDespacho = LocalDateTime.now();
}