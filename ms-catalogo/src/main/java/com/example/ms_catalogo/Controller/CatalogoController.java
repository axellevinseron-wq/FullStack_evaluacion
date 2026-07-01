package com.example.ms_catalogo.Controller;

import com.example.ms_catalogo.Model.Categoria;
import com.example.ms_catalogo.Service.CatalogoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogo")
@Tag(name = "Catalogo - Categorias", description = "Alta y consulta de categorias con sus productos asociados")
public class CatalogoController {
    private final CatalogoService catalogoService;

    public CatalogoController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @Operation(
            summary = "Crear una categoria con sus productos",
            description = "Crea una nueva categoria y, si vienen incluidos, asocia cada producto de la lista a esa categoria."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria creada correctamente",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(
                            value = "{\"id\":1,\"nombre\":\"Frutas y Verduras\",\"productos\":[{\"id\":1,\"nombre\":\"Manzana\",\"precio\":990.0}]}"))),
            @ApiResponse(responseCode = "400", description = "Error de validacion (nombre vacio, precio negativo, etc.)")
    })
    @PostMapping
    public Categoria crear(@Valid @RequestBody Categoria categoria) {
        // Importante: Debemos asignar la categoría a cada producto manualmente antes de guardar
        if (categoria.getProductos() != null) {
            categoria.getProductos().forEach(p -> p.setCategoria(categoria));
        }
        return catalogoService.guardarCategoria(categoria);
    }

    @Operation(summary = "Listar categorias", description = "Retorna todas las categorias registradas junto a sus productos.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public List<Categoria> obtenerTodo() {
        return catalogoService.listarTodo();
    }
}
