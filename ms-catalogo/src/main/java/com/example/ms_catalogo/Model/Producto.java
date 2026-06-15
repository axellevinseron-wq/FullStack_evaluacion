package com.example.ms_catalogo.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Data
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    @Min(value = 0, message = "El precio no puede ser negativo")
    private Double precio;

    // Relación: Muchos productos pertenecen a una categoría
    // El JsonBackReference es la contraparte para que el JSON se vea limpio
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @JsonBackReference
    private Categoria categoria;
}