package com.example.ms_pedidos.Service;

import com.example.ms_pedidos.Enum.EstadoPedido;
import com.example.ms_pedidos.Exception.BusinessException;
import com.example.ms_pedidos.Model.Pedido;
import com.example.ms_pedidos.Repository.PedidoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PedidoService {
    @Autowired
    private PedidoRepository repository;

    public Pedido guardar(Pedido pedido) {
        log.info("Guardando nuevo pedido para cliente: {}", pedido.getClienteId());
        Pedido saved = repository.save(pedido);
        log.info("Pedido creado con ID: {}, Estado: {}", saved.getId(), saved.getEstado());
        return saved;
    }

    public List<Pedido> listarTodo() {
        log.debug("Listando todos los pedidos");
        return repository.findAll();
    }

    public Pedido obtenerPorId(Long id) {
        log.debug("Obteniendo pedido con ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Pedido no encontrado con ID: {}", id);
                    return new BusinessException("PEDIDO_NO_ENCONTRADO", "Pedido no encontrado con ID: " + id);
                });
    }

    public Pedido cambiarEstado(Long pedidoId, EstadoPedido nuevoEstado) {
        Pedido pedido = obtenerPorId(pedidoId);
        EstadoPedido estadoAnterior = pedido.getEstado();

        log.info("Cambio de estado del pedido {} - De: {} -> A: {}", pedidoId, estadoAnterior, nuevoEstado);

        // Validar transiciones permitidas
        if (!esTransicionValida(estadoAnterior, nuevoEstado)) {
            String mensaje = String.format("Transición inválida de %s a %s", estadoAnterior, nuevoEstado);
            log.warn("Transición no permitida para pedido {}: {}", pedidoId, mensaje);
            throw new BusinessException("TRANSICION_INVALIDA", mensaje);
        }

        pedido.setEstado(nuevoEstado);
        Pedido actualizado = repository.save(pedido);

        log.info("Estado del pedido {} actualizado exitosamente a: {}", pedidoId, nuevoEstado);
        registrarCambioEstado(pedidoId, estadoAnterior, nuevoEstado);

        return actualizado;
    }

    private boolean esTransicionValida(EstadoPedido estadoActual, EstadoPedido estadoNuevo) {
        return switch (estadoActual) {
            case PENDIENTE -> estadoNuevo == EstadoPedido.PAGADA || estadoNuevo == EstadoPedido.CANCELADA;
            case PAGADA -> estadoNuevo == EstadoPedido.ENVIADO || estadoNuevo == EstadoPedido.CANCELADA;
            case ENVIADO -> estadoNuevo == EstadoPedido.ENTREGADO;
            case ENTREGADO, CANCELADA -> false;
        };
    }

    private void registrarCambioEstado(Long pedidoId, EstadoPedido estadoAnterior, EstadoPedido estadoNuevo) {
        String mensaje = String.format("AUDITORIA - Pedido %d: %s -> %s", pedidoId, estadoAnterior.getDescripcion(), estadoNuevo.getDescripcion());
        
        if (estadoNuevo == EstadoPedido.PAGADA) {
            log.info("✓ PEDIDO PAGADO: {}", mensaje);
        } else if (estadoNuevo == EstadoPedido.CANCELADA) {
            log.warn("✗ PEDIDO CANCELADO: {}", mensaje);
        } else {
            log.info("→ {}", mensaje);
        }
    }
}
