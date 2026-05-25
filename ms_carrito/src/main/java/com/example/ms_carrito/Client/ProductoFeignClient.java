package com.example.ms_carrito.Client;

import com.example.ms_carrito.Dto.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "ms-catalogo", 
    url = "http://localhost:8080",
    fallback = ProductoFeignClientFallback.class
)
public interface ProductoFeignClient {
    
    @GetMapping("/api/catalogo/productos/{id}")
    ProductoDTO obtenerProductoPorId(@PathVariable("id") Long id);
}
