package com.example.ms_carrito.Dto;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ItemCarritoDTO {
    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;

    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "Debe agregar al menos 1 producto")
    private Integer cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    private Double precioUnitario;

    public ItemCarritoDTO() {
    }

    public ItemCarritoDTO(Long clienteId, Long productoId, Integer cantidad, Double precioUnitario) {
        this.clienteId = clienteId;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
