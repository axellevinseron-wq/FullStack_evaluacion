package com.example.ms_usuarios.Controller;

import com.example.ms_usuarios.Dto.CuentaDTO;
import com.example.ms_usuarios.Mapper.CuentaMapper;
import com.example.ms_usuarios.Model.Cuenta;
import com.example.ms_usuarios.Service.CuentaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class CuentaController {

    private final CuentaService service;
    private final CuentaMapper mapper;

    public CuentaController(CuentaService service, CuentaMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<CuentaDTO> crear(@Valid @RequestBody CuentaDTO dto) {
        Cuenta cuenta = mapper.toEntity(dto);
        Cuenta cuentaGuardada = service.guardar(cuenta);
        URI location = URI.create("/api/usuarios/" + cuentaGuardada.getId());

        return ResponseEntity.created(location).body(mapper.toDTO(cuentaGuardada));
    }

    @GetMapping
    public ResponseEntity<List<CuentaDTO>> obtenerTodos() {
        List<Cuenta> cuentas = service.listarTodo();
        List<CuentaDTO> dtos = cuentas.stream()
                .map(mapper::toDTO)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaDTO> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map((Cuenta cuenta) -> ResponseEntity.ok(mapper.toDTO(cuenta)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody CuentaDTO dto) {
        return service.actualizar(id, mapper.toEntity(dto))
                .map((Cuenta cuenta) -> ResponseEntity.ok(mapper.toDTO(cuenta)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
