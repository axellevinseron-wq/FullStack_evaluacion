package com.example.ms_promociones.Dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CuponDTO {
    @NotBlank(message = "El código del cupón es obligatorio")
    private String codigo;

    @Min(value = 0, message = "El descuento no puede ser menor a 0%")
    @Max(value = 100, message = "El descuento no puede ser mayor a 100%")
    private Double descuento;
}