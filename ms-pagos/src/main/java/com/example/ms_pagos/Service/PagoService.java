package com.example.ms_pagos.Service;



import com.example.ms_pagos.Model.Pago;
import com.example.ms_pagos.Repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PagoService {
    @Autowired
    private PagoRepository repository;

    public Pago guardar(Pago pago) {
        return repository.save(pago);
    }

    public List<Pago> listarTodo() {
        return repository.findAll();
    }
}