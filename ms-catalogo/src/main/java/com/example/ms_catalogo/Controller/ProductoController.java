package com.example.ms_catalogo.Controller;

import com.example.ms_catalogo.Model.Producto;
import com.example.ms_catalogo.Service.CatalogoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/catalogo/productos")
@Tag(name = "Catalogo - Productos", description = "Consulta de productos individuales (consumido por ms-carrito via Feign)")
public class ProductoController {
    private final CatalogoService catalogoService;

    public ProductoController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @Operation(
            summary = "Obtener producto por ID",
            description = "Retorna un producto por su identificador. Utilizado por ms-carrito para validar precio y existencia."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(
            @Parameter(description = "ID del producto", example = "1") @PathVariable Long id) {
        return catalogoService.obtenerProductoPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
