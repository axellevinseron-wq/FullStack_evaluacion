package com.example.ms_notificaciones.Controller;

import com.example.ms_notificaciones.Model.Mensaje;
import com.example.ms_notificaciones.Service.MensajeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@Tag(name = "Notificaciones", description = "Registro y consulta de mensajes (email/SMS) enviados a clientes")
public class MensajeController {
    private final MensajeService service;

    public MensajeController(MensajeService service) {
        this.service = service;
    }

    @Operation(
            summary = "Registrar un mensaje/notificacion",
            description = "Crea un nuevo mensaje con destinatario, asunto, contenido y estado (Enviado/Pendiente/Fallido)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mensaje registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validacion (email invalido, campos vacios, etc.)")
    })
    @PostMapping
    public Mensaje crear(@Valid @RequestBody Mensaje mensaje) {
        return service.guardar(mensaje);
    }

    @Operation(summary = "Listar mensajes", description = "Retorna todos los mensajes/notificaciones registrados.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public List<Mensaje> obtenerTodos() {
        return service.listarTodo();
    }
}
