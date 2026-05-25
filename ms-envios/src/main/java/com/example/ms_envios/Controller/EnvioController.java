package com.example.ms_envios.Controller;



import com.example.ms_envios.Model.Envio;
import com.example.ms_envios.Service.EnvioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {
    @Autowired
    private EnvioService service;

    @PostMapping
    public Envio crear(@Valid @RequestBody Envio envio) {
        return service.guardar(envio);
    }

    @GetMapping
    public List<Envio> obtenerTodos() {
        return service.listarTodo();
    }
}