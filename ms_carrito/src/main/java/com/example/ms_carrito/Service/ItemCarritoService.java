package com.example.ms_carrito.Service;

import com.example.ms_carrito.Client.ProductoFeignClient;
import com.example.ms_carrito.Dto.ProductoDTO;
import com.example.ms_carrito.Model.ItemCarrito;
import com.example.ms_carrito.Repository.ItemCarritoRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ItemCarritoService {
    @Autowired
    private ItemCarritoRepository repository;

    @Autowired
    private ProductoFeignClient productoFeignClient;

    public ItemCarrito guardar(ItemCarrito item) {
        try {
            ProductoDTO producto = productoFeignClient.obtenerProductoPorId(item.getProductoId());
            
            if (producto == null) {
                throw new IllegalArgumentException("Producto no encontrado con ID: " + item.getProductoId());
            }
            
            item.setPrecioUnitario(producto.getPrecio());
            
            return repository.save(item);
        } catch (FeignException.NotFound e) {
            throw new IllegalArgumentException("Producto no encontrado con ID: " + item.getProductoId());
        } catch (FeignException e) {
            throw new RuntimeException("Error al consultar el servicio de catálogo: " + e.getMessage());
        }
    }

    public List<ItemCarrito> listarTodo() {
        return repository.findAll();
    }
}
