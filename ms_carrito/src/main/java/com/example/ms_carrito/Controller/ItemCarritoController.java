package com.example.ms_carrito.Controller;



import com.example.ms_carrito.Model.ItemCarrito;
import com.example.ms_carrito.Service.ItemCarritoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/carrito")
public class ItemCarritoController {
    @Autowired
    private ItemCarritoService service;

    @PostMapping
    public ItemCarrito crear(@Valid @RequestBody ItemCarrito item) {
        return service.guardar(item);
    }

    @GetMapping
    public List<ItemCarrito> obtenerTodos() {
        return service.listarTodo();
    }
}