package com.example.ms_usuarios.Service;

import com.example.ms_usuarios.Model.Cuenta;
import com.example.ms_usuarios.Repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {
    @Autowired
    private CuentaRepository repository;

    public Cuenta guardar(Cuenta cuenta) {
        return repository.save(cuenta);
    }

    public List<Cuenta> listarTodo() {
        return repository.findAll();
    }

    public Optional<Cuenta> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    public Optional<Cuenta> actualizar(Long id, Cuenta cuentaActualizada) {
        return repository.findById(id).map(cuenta -> {
            cuenta.setClienteId(cuentaActualizada.getClienteId());
            cuenta.setUsername(cuentaActualizada.getUsername());
            cuenta.setPassword(cuentaActualizada.getPassword());
            cuenta.setRol(cuentaActualizada.getRol());
            cuenta.setUpdatedAt(LocalDateTime.now());
            return repository.save(cuenta);
        });
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}