package com.example.ms_pedidos.Dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DetallePedidoDTO {
    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;

    @Min(value = 1, message = "La cantidad mínima es 1")
    private Integer cantidad;

    @Min(value = 0, message = "El subtotal no puede ser negativo")
    private Double precioSubtotal;
}