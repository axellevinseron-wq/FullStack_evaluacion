package com.example.ms_envios.Service;



import com.example.ms_envios.Model.Envio;
import com.example.ms_envios.Repository.EnvioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class EnvioService {
    private final EnvioRepository repository;

    public EnvioService(EnvioRepository repository) {
        this.repository = repository;
    }

    public Envio guardar(Envio envio) {
        // Generamos un código de seguimiento automático de ejemplo
        if (envio.getCodigoSeguimiento() == null) {
            envio.setCodigoSeguimiento("TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        return repository.save(envio);
    }

    public List<Envio> listarTodo() {
        return repository.findAll();
    }
}
