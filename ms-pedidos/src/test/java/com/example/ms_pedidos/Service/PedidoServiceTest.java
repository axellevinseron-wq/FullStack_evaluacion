package com.example.ms_pedidos.Service;

import com.example.ms_pedidos.Enum.EstadoPedido;
import com.example.ms_pedidos.Exception.BusinessException;
import com.example.ms_pedidos.Model.Pedido;
import com.example.ms_pedidos.Repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Pruebas unitarias de PedidoService.
 * Se simula (mock) PedidoRepository: no se toca la base de datos real.
 * Cubre: creacion, consulta, y la maquina de estados completa de cambiarEstado().
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("PedidoService - pruebas unitarias")
class PedidoServiceTest {

    @Mock
    private PedidoRepository repository;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedidoPendiente;

    @BeforeEach
    void setUp() {
        pedidoPendiente = new Pedido();
        pedidoPendiente.setId(1L);
        pedidoPendiente.setClienteId(100L);
        pedidoPendiente.setEstado(EstadoPedido.PENDIENTE);
    }

    @Nested
    @DisplayName("guardar()")
    class Guardar {

        @Test
        @DisplayName("Given un pedido nuevo, When se guarda, Then se persiste via repositorio y se retorna")
        void guardar_pedidoValido_persisteYRetorna() {
            // Given
            given(repository.save(pedidoPendiente)).willReturn(pedidoPendiente);

            // When
            Pedido resultado = pedidoService.guardar(pedidoPendiente);

            // Then
            assertThat(resultado.getId()).isEqualTo(1L);
            assertThat(resultado.getEstado()).isEqualTo(EstadoPedido.PENDIENTE);
            verify(repository, times(1)).save(pedidoPendiente);
        }
    }

    @Nested
    @DisplayName("listarTodo()")
    class ListarTodo {

        @Test
        @DisplayName("Given pedidos registrados, When se listan, Then retorna todos los pedidos")
        void listarTodo_conRegistros_retornaListaCompleta() {
            // Given
            Pedido otro = new Pedido();
            otro.setId(2L);
            otro.setClienteId(200L);
            otro.setEstado(EstadoPedido.PAGADA);
            given(repository.findAll()).willReturn(Arrays.asList(pedidoPendiente, otro));

            // When
            List<Pedido> resultado = pedidoService.listarTodo();

            // Then
            assertThat(resultado).hasSize(2);
        }

        @Test
        @DisplayName("Given no hay pedidos, When se listan, Then retorna lista vacia")
        void listarTodo_sinRegistros_retornaListaVacia() {
            // Given
            given(repository.findAll()).willReturn(Collections.emptyList());

            // When
            List<Pedido> resultado = pedidoService.listarTodo();

            // Then
            assertThat(resultado).isEmpty();
        }
    }

    @Nested
    @DisplayName("obtenerPorId()")
    class ObtenerPorId {

        @Test
        @DisplayName("Given un ID existente, When se busca, Then retorna el pedido")
        void obtenerPorId_existente_retornaPedido() {
            // Given
            given(repository.findById(1L)).willReturn(Optional.of(pedidoPendiente));

            // When
            Pedido resultado = pedidoService.obtenerPorId(1L);

            // Then
            assertThat(resultado).isEqualTo(pedidoPendiente);
        }

        @Test
        @DisplayName("Given un ID inexistente, When se busca, Then lanza BusinessException PEDIDO_NO_ENCONTRADO")
        void obtenerPorId_inexistente_lanzaBusinessException() {
            // Given
            given(repository.findById(999L)).willReturn(Optional.empty());

            // When
            Throwable excepcion = catchThrowable(() -> pedidoService.obtenerPorId(999L));

            // Then
            assertThat(excepcion).isInstanceOf(BusinessException.class)
                    .hasMessageContaining("Pedido no encontrado con ID: 999");
            assertThat(((BusinessException) excepcion).getCode()).isEqualTo("PEDIDO_NO_ENCONTRADO");
        }
    }

    @Nested
    @DisplayName("cambiarEstado() - transiciones validas")
    class CambiarEstadoValido {

        @ParameterizedTest(name = "Given pedido en {0}, When cambia a {1}, Then la transicion se aplica")
        @CsvSource({
                "PENDIENTE, PAGADA",
                "PENDIENTE, CANCELADA",
                "PAGADA, ENVIADO",
                "PAGADA, CANCELADA",
                "ENVIADO, ENTREGADO"
        })
        void cambiarEstado_transicionValida_actualizaYPersiste(EstadoPedido estadoActual, EstadoPedido estadoNuevo) {
            // Given
            pedidoPendiente.setEstado(estadoActual);
            given(repository.findById(1L)).willReturn(Optional.of(pedidoPendiente));
            given(repository.save(any(Pedido.class))).willAnswer(invocation -> invocation.getArgument(0));

            // When
            Pedido resultado = pedidoService.cambiarEstado(1L, estadoNuevo);

            // Then
            assertThat(resultado.getEstado()).isEqualTo(estadoNuevo);

            ArgumentCaptor<Pedido> captor = ArgumentCaptor.forClass(Pedido.class);
            verify(repository).save(captor.capture());
            assertThat(captor.getValue().getEstado()).isEqualTo(estadoNuevo);
        }
    }

    @Nested
    @DisplayName("cambiarEstado() - transiciones invalidas")
    class CambiarEstadoInvalido {

        @ParameterizedTest(name = "Given pedido en {0}, When intenta cambiar a {1}, Then rechaza la transicion")
        @CsvSource({
                "PENDIENTE, ENVIADO",
                "PENDIENTE, ENTREGADO",
                "PAGADA, ENTREGADO",
                "PAGADA, PENDIENTE",
                "ENVIADO, CANCELADA",
                "ENVIADO, PENDIENTE",
                "ENTREGADO, PAGADA",
                "CANCELADA, PENDIENTE"
        })
        void cambiarEstado_transicionInvalida_lanzaBusinessExceptionYNoGuarda(EstadoPedido estadoActual, EstadoPedido estadoNuevo) {
            // Given
            pedidoPendiente.setEstado(estadoActual);
            given(repository.findById(1L)).willReturn(Optional.of(pedidoPendiente));

            // When / Then
            assertThatThrownBy(() -> pedidoService.cambiarEstado(1L, estadoNuevo))
                    .isInstanceOf(BusinessException.class)
                    .hasMessageContaining("Transición inválida");

            verify(repository, never()).save(any(Pedido.class));
        }

        @Test
        @DisplayName("Given un pedido inexistente, When se intenta cambiar estado, Then propaga PEDIDO_NO_ENCONTRADO sin guardar")
        void cambiarEstado_pedidoInexistente_propagaExcepcionSinGuardar() {
            // Given
            given(repository.findById(999L)).willReturn(Optional.empty());

            // When / Then
            assertThatThrownBy(() -> pedidoService.cambiarEstado(999L, EstadoPedido.PAGADA))
                    .isInstanceOf(BusinessException.class);

            verify(repository, never()).save(any(Pedido.class));
        }
    }
}
