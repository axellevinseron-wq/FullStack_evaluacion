package com.example.ms_notificaciones.Model;



import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // A quién va dirigido (relacionado lógicamente con ms-clientes o ms-usuarios)
    private String destinatarioEmail; 
    
    private String asunto;
    private String contenido;
    private String estado; // ej: "Enviado", "Pendiente", "Fallido"
    
    private LocalDateTime fechaEnvio = LocalDateTime.now();
}