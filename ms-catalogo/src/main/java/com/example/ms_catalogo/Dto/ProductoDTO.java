package com.example.ms_catalogo.Dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductoDTO {
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    @Min(value = 0, message = "El precio no puede ser negativo")
    private Double precio;
}