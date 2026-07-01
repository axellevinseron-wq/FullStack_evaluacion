package com.example.ms_pedidos.Controller;

import com.example.ms_pedidos.Enum.EstadoPedido;
import com.example.ms_pedidos.Model.Pedido;
import com.example.ms_pedidos.Service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Creacion, consulta y cambio de estado de pedidos de EcoMarket")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(summary = "Crear pedido", description = "Crea un nuevo pedido en estado PENDIENTE.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validacion (cliente sin ID, sin detalles, etc.)")
    })
    @PostMapping
    public Pedido crear(@Valid @RequestBody Pedido pedido) {
        return pedidoService.guardar(pedido);
    }

    @Operation(summary = "Listar pedidos", description = "Retorna todos los pedidos registrados.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public List<Pedido> obtenerTodos() {
        return pedidoService.listarTodo();
    }

    @Operation(summary = "Obtener pedido por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "400", description = "Pedido no encontrado (PEDIDO_NO_ENCONTRADO)",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(
                            value = "{\"code\":\"PEDIDO_NO_ENCONTRADO\",\"message\":\"Pedido no encontrado con ID: 99\",\"status\":400}")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(
            @Parameter(description = "ID del pedido", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.obtenerPorId(id));
    }

    @Operation(
            summary = "Cambiar estado del pedido",
            description = "Aplica una transicion de estado validando el flujo permitido: " +
                    "PENDIENTE -> PAGADA/CANCELADA, PAGADA -> ENVIADO/CANCELADA, ENVIADO -> ENTREGADO. " +
                    "ENTREGADO y CANCELADA son estados finales."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Transicion invalida (TRANSICION_INVALIDA) o pedido no encontrado")
    })
    @PutMapping("/{id}/estado")
    public ResponseEntity<Pedido> cambiarEstado(
            @Parameter(description = "ID del pedido", example = "1") @PathVariable Long id,
            @Parameter(description = "Nuevo estado destino", example = "PAGADA") @RequestParam EstadoPedido estado) {
        return ResponseEntity.ok(pedidoService.cambiarEstado(id, estado));
    }
}
