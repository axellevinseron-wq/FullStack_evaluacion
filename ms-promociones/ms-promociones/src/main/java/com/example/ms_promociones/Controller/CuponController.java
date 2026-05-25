package com.example.ms_promociones.Controller;



import com.example.ms_promociones.Model.Cupon;
import com.example.ms_promociones.Service.CuponService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/promociones")
public class CuponController {
    @Autowired
    private CuponService service;

    @PostMapping
    public Cupon crear(@Valid @RequestBody Cupon cupon) {
        return service.guardar(cupon);
    }

    @GetMapping
    public List<Cupon> obtenerTodos() {
        return service.listarTodo();
    }
}