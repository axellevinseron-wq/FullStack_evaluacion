package com.example.ms_clientes.Service;


import com.example.ms_clientes.Model.Cliente;
import com.example.ms_clientes.Repository.ClienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public Cliente guardar(Cliente cliente) {
        return repository.save(cliente);
    }

    public List<Cliente> listarTodo() {
        return repository.findAll();
    }
}
