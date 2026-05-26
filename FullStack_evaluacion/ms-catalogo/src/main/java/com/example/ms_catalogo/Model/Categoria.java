package com.example.ms_catalogo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Data
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    private String nombre;

    // Relación: Una categoría tiene muchos productos
    // El JsonManagedReference evita bucles infinitos al mostrar el JSON en Postman
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Producto> productos;
}