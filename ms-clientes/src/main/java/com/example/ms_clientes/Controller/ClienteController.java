package com.example.ms_clientes.Controller;



import com.example.ms_clientes.Model.Cliente;
import com.example.ms_clientes.Service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @PostMapping
    public Cliente crear(@Valid @RequestBody Cliente cliente) {
        if (cliente.getDirecciones() != null) {
            cliente.getDirecciones().forEach(d -> d.setCliente(cliente));
        }
        return service.guardar(cliente);
    }

    @GetMapping
    public List<Cliente> obtenerTodos() {
        return service.listarTodo();
    }
}
