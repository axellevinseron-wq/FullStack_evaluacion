package com.example.ms_catalogo.Service;

import com.example.ms_catalogo.Model.Categoria;
import com.example.ms_catalogo.Model.Producto;
import com.example.ms_catalogo.Repository.CategoriaRepository;
import com.example.ms_catalogo.Repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

/**
 * Pruebas unitarias de CatalogoService.
 * Se simulan (mock) ProductoRepository y CategoriaRepository: no se toca la base de datos real.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("CatalogoService - pruebas unitarias")
class CatalogoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CatalogoService catalogoService;

    private Categoria categoriaValida;
    private Producto productoValido;

    @BeforeEach
    void setUp() {
        productoValido = new Producto();
        productoValido.setId(1L);
        productoValido.setNombre("Manzana");
        productoValido.setPrecio(990.0);

        categoriaValida = new Categoria();
        categoriaValida.setId(1L);
        categoriaValida.setNombre("Frutas y Verduras");
        categoriaValida.setProductos(new ArrayList<>(List.of(productoValido)));
    }

    @Nested
    @DisplayName("guardarCategoria()")
    class GuardarCategoria {

        @Test
        @DisplayName("Given una categoria valida, When se guarda, Then delega en el repositorio y retorna la entidad persistida")
        void guardarCategoria_categoriaValida_persisteYRetorna() {
            // Given
            given(categoriaRepository.save(categoriaValida)).willReturn(categoriaValida);

            // When
            Categoria resultado = catalogoService.guardarCategoria(categoriaValida);

            // Then
            assertThat(resultado).isNotNull();
            assertThat(resultado.getNombre()).isEqualTo("Frutas y Verduras");
            assertThat(resultado.getProductos()).hasSize(1);
            verify(categoriaRepository, times(1)).save(categoriaValida);
            verifyNoInteractions(productoRepository);
        }

        @Test
        @DisplayName("Given una categoria sin productos, When se guarda, Then no lanza error y se persiste igual")
        void guardarCategoria_sinProductos_persisteSinError() {
            // Given
            Categoria sinProductos = new Categoria();
            sinProductos.setId(2L);
            sinProductos.setNombre("Lacteos");
            sinProductos.setProductos(Collections.emptyList());
            given(categoriaRepository.save(sinProductos)).willReturn(sinProductos);

            // When
            Categoria resultado = catalogoService.guardarCategoria(sinProductos);

            // Then
            assertThat(resultado.getProductos()).isEmpty();
            verify(categoriaRepository).save(sinProductos);
        }
    }

    @Nested
    @DisplayName("listarTodo()")
    class ListarTodo {

        @Test
        @DisplayName("Given categorias registradas, When se listan, Then retorna la lista completa desde el repositorio")
        void listarTodo_conRegistros_retornaListaCompleta() {
            // Given
            Categoria otra = new Categoria();
            otra.setId(2L);
            otra.setNombre("Lacteos");
            given(categoriaRepository.findAll()).willReturn(Arrays.asList(categoriaValida, otra));

            // When
            List<Categoria> resultado = catalogoService.listarTodo();

            // Then
            assertThat(resultado).hasSize(2);
            assertThat(resultado).extracting(Categoria::getNombre)
                    .containsExactly("Frutas y Verduras", "Lacteos");
            verify(categoriaRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("Given no hay categorias, When se listan, Then retorna una lista vacia")
        void listarTodo_sinRegistros_retornaListaVacia() {
            // Given
            given(categoriaRepository.findAll()).willReturn(Collections.emptyList());

            // When
            List<Categoria> resultado = catalogoService.listarTodo();

            // Then
            assertThat(resultado).isEmpty();
        }
    }

    @Nested
    @DisplayName("obtenerProductoPorId()")
    class ObtenerProductoPorId {

        @Test
        @DisplayName("Given un ID existente, When se busca el producto, Then retorna un Optional con el producto")
        void obtenerProductoPorId_existente_retornaOptionalConProducto() {
            // Given
            given(productoRepository.findById(1L)).willReturn(Optional.of(productoValido));

            // When
            Optional<Producto> resultado = catalogoService.obtenerProductoPorId(1L);

            // Then
            assertThat(resultado).isPresent();
            assertThat(resultado.get().getNombre()).isEqualTo("Manzana");
            verify(productoRepository).findById(1L);
        }

        @Test
        @DisplayName("Given un ID inexistente, When se busca el producto, Then retorna Optional vacio")
        void obtenerProductoPorId_inexistente_retornaOptionalVacio() {
            // Given
            given(productoRepository.findById(eq(999L))).willReturn(Optional.empty());

            // When
            Optional<Producto> resultado = catalogoService.obtenerProductoPorId(999L);

            // Then
            assertThat(resultado).isEmpty();
            verify(productoRepository).findById(999L);
        }
    }
}

