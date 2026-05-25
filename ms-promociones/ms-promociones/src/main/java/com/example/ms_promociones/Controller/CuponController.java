package com.example.ms_promociones.Controller;



import com.example.ms_promociones.Model.Cupon;
import com.example.ms_promociones.Service.CuponService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/promociones")
public class CuponController {
    private final CuponService service;

    public CuponController(CuponService service) {
        this.service = service;
    }

    @PostMapping
    public Cupon crear(@Valid @RequestBody Cupon cupon) {
        return service.guardar(cupon);
    }

    @GetMapping
    public List<Cupon> obtenerTodos() {
        return service.listarTodo();
    }
}
