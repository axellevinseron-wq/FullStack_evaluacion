package com.example.ms_notificaciones.Controller;



import com.example.ms_notificaciones.Model.Mensaje;
import com.example.ms_notificaciones.Service.MensajeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class MensajeController {
    private final MensajeService service;

    public MensajeController(MensajeService service) {
        this.service = service;
    }

    @PostMapping
    public Mensaje crear(@Valid @RequestBody Mensaje mensaje) {
        return service.guardar(mensaje);
    }

    @GetMapping
    public List<Mensaje> obtenerTodos() {
        return service.listarTodo();
    }
}
