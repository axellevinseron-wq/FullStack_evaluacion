package com.example.ms_catalogo.Dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductoDTO {
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull
    @Min(value = 0)
    private Double precio;
}