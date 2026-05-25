package com.example.ms_pagos.Dto;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PagoDTO {
    @NotNull(message = "El ID del pedido es obligatorio")
    private Long pedidoId;

    @Min(value = 1, message = "El monto debe ser mayor a 0")
    @NotNull(message = "El monto es obligatorio")
    private Double monto;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}