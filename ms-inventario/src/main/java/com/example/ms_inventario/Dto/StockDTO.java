package com.example.ms_inventario.Dto;





// Estas son las importaciones clave que VS Code no te estaba agregando:
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockDTO {

    @NotNull(message = "El ID del producto no puede ser nulo")
    private Long productoId;

    @NotNull(message = "La cantidad de kilos es obligatoria")
    @Min(value = 0, message = "Los kilos no pueden ser negativos")
    private Double kilos;
}