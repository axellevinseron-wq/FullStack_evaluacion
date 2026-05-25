package com.example.ms_promociones.Service;

import com.example.ms_promociones.Model.Cupon;
import com.example.ms_promociones.Repository.CuponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CuponService {
    @Autowired
    private CuponRepository repository;

    public Cupon guardar(Cupon cupon) {
        return repository.save(cupon);
    }

    public List<Cupon> listarTodo() {
        return repository.findAll();
    }
}