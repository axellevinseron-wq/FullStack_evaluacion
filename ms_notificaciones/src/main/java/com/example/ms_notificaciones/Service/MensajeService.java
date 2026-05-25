package com.example.ms_notificaciones.Service;



import com.example.ms_notificaciones.Model.Mensaje;
import com.example.ms_notificaciones.Repository.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MensajeService {
    @Autowired
    private MensajeRepository repository;

    public Mensaje guardar(Mensaje mensaje) {
        return repository.save(mensaje);
    }

    public List<Mensaje> listarTodo() {
        return repository.findAll();
    }
}