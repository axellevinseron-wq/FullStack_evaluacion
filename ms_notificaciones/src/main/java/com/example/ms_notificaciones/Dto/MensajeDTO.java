package com.example.ms_notificaciones.Dto;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MensajeDTO {
    @Email(message = "Debe ser un correo electrónico válido")
    @NotBlank(message = "El email del destinatario es obligatorio")
    private String destinatarioEmail;

    @NotBlank(message = "El asunto no puede estar vacío")
    private String asunto;

    @NotBlank(message = "El contenido del mensaje es obligatorio")
    private String contenido;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}