package com.example.ms_catalogo.Controller;



import com.example.ms_catalogo.Model.Categoria;
import com.example.ms_catalogo.Service.CatalogoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/catalogo")
public class CatalogoController {
    @Autowired
    private CatalogoService catalogoService;

    @PostMapping
    public Categoria crear(@Valid @RequestBody Categoria categoria) {
        // Importante: Debemos asignar la categoría a cada producto manualmente antes de guardar
        if (categoria.getProductos() != null) {
            categoria.getProductos().forEach(p -> p.setCategoria(categoria));
        }
        return catalogoService.guardarCategoria(categoria);
    }

    @GetMapping
    public List<Categoria> obtenerTodo() {
        return catalogoService.listarTodo();
    }
}