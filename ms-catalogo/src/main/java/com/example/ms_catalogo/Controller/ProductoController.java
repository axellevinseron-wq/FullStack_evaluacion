package com.example.ms_catalogo.Controller;

import com.example.ms_catalogo.Model.Producto;
import com.example.ms_catalogo.Service.CatalogoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/catalogo/productos")
public class ProductoController {
    private final CatalogoService catalogoService;

    public ProductoController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        return catalogoService.obtenerProductoPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
