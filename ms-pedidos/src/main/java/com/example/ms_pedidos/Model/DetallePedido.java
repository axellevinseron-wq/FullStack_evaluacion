package com.example.ms_pedidos.Model;



import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Data
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Aquí guardaremos el ID del producto (del ms-catalogo/inventario)
    private Long productoId;
    
    private Integer cantidad;
    private Double precioSubtotal;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonBackReference
    private Pedido pedido;
}