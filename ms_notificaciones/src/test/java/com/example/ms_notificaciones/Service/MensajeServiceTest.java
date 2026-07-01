package com.example.ms_notificaciones.Service;

import com.example.ms_notificaciones.Model.Mensaje;
import com.example.ms_notificaciones.Repository.MensajeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Pruebas unitarias de MensajeService.
 * Se simula (mock) MensajeRepository: no se toca la base de datos real.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("MensajeService - pruebas unitarias")
class MensajeServiceTest {

    @Mock
    private MensajeRepository repository;

    @InjectMocks
    private MensajeService mensajeService;

    private Mensaje mensajeValido;

    @BeforeEach
    void setUp() {
        mensajeValido = new Mensaje();
        mensajeValido.setId(1L);
        mensajeValido.setDestinatarioEmail("cliente@ecomarket.cl");
        mensajeValido.setAsunto("Confirmacion de pedido");
        mensajeValido.setContenido("Tu pedido #1 fue confirmado.");
        mensajeValido.setEstado("Pendiente");
        mensajeValido.setFechaEnvio(LocalDateTime.of(2026, 6, 30, 10, 0));
    }

    @Nested
    @DisplayName("guardar()")
    class Guardar {

        @Test
        @DisplayName("Given un mensaje valido, When se guarda, Then se persiste via repositorio y se retorna")
        void guardar_mensajeValido_persisteYRetorna() {
            // Given
            given(repository.save(mensajeValido)).willReturn(mensajeValido);

            // When
            Mensaje resultado = mensajeService.guardar(mensajeValido);

            // Then
            assertThat(resultado.getDestinatarioEmail()).isEqualTo("cliente@ecomarket.cl");
            assertThat(resultado.getEstado()).isEqualTo("Pendiente");
            verify(repository, times(1)).save(mensajeValido);
        }

        @Test
        @DisplayName("Given un mensaje con estado 'Fallido', When se guarda, Then conserva ese estado tras persistir")
        void guardar_mensajeFallido_conservaEstado() {
            // Given
            mensajeValido.setEstado("Fallido");
            given(repository.save(mensajeValido)).willReturn(mensajeValido);

            // When
            Mensaje resultado = mensajeService.guardar(mensajeValido);

            // Then
            assertThat(resultado.getEstado()).isEqualTo("Fallido");
        }
    }

    @Nested
    @DisplayName("listarTodo()")
    class ListarTodo {

        @Test
        @DisplayName("Given mensajes registrados, When se listan, Then retorna todos los mensajes")
        void listarTodo_conRegistros_retornaListaCompleta() {
            // Given
            Mensaje otro = new Mensaje();
            otro.setId(2L);
            otro.setDestinatarioEmail("otro@ecomarket.cl");
            otro.setEstado("Enviado");
            given(repository.findAll()).willReturn(Arrays.asList(mensajeValido, otro));

            // When
            List<Mensaje> resultado = mensajeService.listarTodo();

            // Then
            assertThat(resultado).hasSize(2);
            assertThat(resultado).extracting(Mensaje::getEstado)
                    .containsExactly("Pendiente", "Enviado");
        }

        @Test
        @DisplayName("Given no hay mensajes, When se listan, Then retorna lista vacia")
        void listarTodo_sinRegistros_retornaListaVacia() {
            // Given
            given(repository.findAll()).willReturn(Collections.emptyList());

            // When
            List<Mensaje> resultado = mensajeService.listarTodo();

            // Then
            assertThat(resultado).isEmpty();
        }
    }
}
