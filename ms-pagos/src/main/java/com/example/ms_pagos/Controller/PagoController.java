package com.example.ms_pagos.Controller;



import com.example.ms_pagos.Model.Pago;
import com.example.ms_pagos.Service.PagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {
    @Autowired
    private PagoService service;

    @PostMapping
    public Pago crear(@Valid @RequestBody Pago pago) {
        return service.guardar(pago);
    }

    @GetMapping
    public List<Pago> obtenerTodos() {
        return service.listarTodo();
    }
}