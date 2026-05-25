package com.example.ms_carrito.Client;

import com.example.ms_carrito.Dto.ProductoDTO;
import com.example.ms_carrito.Exception.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class ProductoFeignClientFallback implements ProductoFeignClient {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProductoFeignClientFallback.class);

    @Override
    public ProductoDTO obtenerProductoPorId(Long id) {
        log.error("🔥 FALLBACK ACTIVADO - Servicio ms-catalogo NO DISPONIBLE. ProductoID: {}", id);
        log.error("Reintentando en 30 segundos. Verifique que ms-catalogo esté ejecutándose en http://localhost:8081");
        
        throw new BusinessException("CATALOGO_UNAVAILABLE", 
            "Servicio de catálogo temporalmente no disponible. Por favor, intente nuevamente en 30 segundos.");
    }
}
