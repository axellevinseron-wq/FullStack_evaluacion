package com.example.ms_envios.Dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnvioDTO {
    @NotNull(message = "El ID del pedido es obligatorio")
    private Long pedidoId;

    @NotBlank(message = "La dirección de destino es obligatoria")
    private String direccionDestino;

    @NotBlank(message = "La empresa de transporte es obligatoria")
    private String empresaTransporte;

    @NotBlank(message = "El estado del envío es obligatorio")
    private String estado;
}