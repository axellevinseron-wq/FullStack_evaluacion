package com.example.ms_catalogo.Controller;

import com.example.ms_catalogo.Model.Producto;
import com.example.ms_catalogo.Service.CatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/catalogo/productos")
public class ProductoController {
    @Autowired
    private CatalogoService catalogoService;

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        return catalogoService.obtenerProductoPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
