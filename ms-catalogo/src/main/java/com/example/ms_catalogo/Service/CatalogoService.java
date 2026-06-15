package com.example.ms_catalogo.Service;

import com.example.ms_catalogo.Model.Categoria;
import com.example.ms_catalogo.Model.Producto;
import com.example.ms_catalogo.Repository.CategoriaRepository;
import com.example.ms_catalogo.Repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatalogoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public CatalogoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listarTodo() {
        return categoriaRepository.findAll();
    }

    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }
}
