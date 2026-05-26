package com.example.ms_pedidos.Enum;

public enum EstadoPedido {
    PENDIENTE("Pendiente de pago"),
    PAGADA("Pedido pagado"),
    ENVIADO("Pedido enviado"),
    ENTREGADO("Pedido entregado"),
    CANCELADA("Pedido cancelado");

    private final String descripcion;

    EstadoPedido(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
