package com.example.ms_pedidos.Dto;



import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Data
public class PedidoDTO {
    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;

    @NotNull(message = "El pedido debe tener detalles")
    @Size(min = 1, message = "Debe haber al menos un producto en el pedido")
    private List<DetallePedidoDTO> detalles;
}