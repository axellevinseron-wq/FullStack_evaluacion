package com.example.ms_carrito.Client;

import com.example.ms_carrito.Dto.ProductoDTO;
import com.example.ms_carrito.Exception.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class ProductoFeignClientFallback implements ProductoFeignClient {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProductoFeignClientFallback.class);

    @Override
    public ProductoDTO obtenerProductoPorId(Long id) {
        log.error("Circuit breaker activado para ms-catalogo");
        throw new BusinessException("CATALOGO_TIMEOUT", "Catálogo no disponible");
    }
}
