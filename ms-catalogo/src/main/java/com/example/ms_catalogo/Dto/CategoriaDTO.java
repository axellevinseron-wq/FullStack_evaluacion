package com.example.ms_catalogo.Dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class CategoriaDTO {
    private Long id;

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    private String nombre;

    // Fíjate que aquí usamos ProductoDTO, no la entidad Producto
    private List<ProductoDTO> productos;
}