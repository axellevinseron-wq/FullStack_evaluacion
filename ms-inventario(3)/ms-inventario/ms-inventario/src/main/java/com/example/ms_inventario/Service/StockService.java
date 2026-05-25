package com.example.ms_inventario.Service;

import com.example.ms_inventario.Exception.BusinessException;
import com.example.ms_inventario.Model.Stock;
import com.example.ms_inventario.Repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public List<Stock> listarTodo() {
        log.debug("Listando todo el inventario");
        return stockRepository.findAll();
    }

    public Optional<Stock> obtenerPorProductoId(Long productoId) {
        log.debug("Consultando stock para ProductoID: {}", productoId);
        return stockRepository.findByProductoId(productoId);
    }

    public Stock guardar(Stock stock) {
        log.info("Guardando stock - ProductoID: {}, Cantidad: {}", stock.getProductoId(), stock.getKilos());
        return stockRepository.save(stock);
    }

    public void eliminar(Long id) {
        log.info("Eliminando registro de stock ID: {}", id);
        stockRepository.deleteById(id);
    }

    public Stock descontarStock(Long productoId, Double cantidad) {
        log.info("Intento de descuento - ProductoID: {}, Cantidad solicitada: {}", productoId, cantidad);

        Stock stock = stockRepository.findByProductoId(productoId)
                .orElseThrow(() -> {
                    log.error("❌ PRODUCTO NO EXISTE EN INVENTARIO - ProductoID: {}", productoId);
                    return new BusinessException("PRODUCTO_SIN_STOCK", 
                        "Producto no encontrado en inventario");
                });

        if (stock.getKilos() < cantidad) {
            log.warn("⚠️ STOCK INSUFICIENTE - ProductoID: {}, Solicitado: {}, Disponible: {}", 
                productoId, cantidad, stock.getKilos());
            throw new BusinessException("STOCK_INSUFICIENTE", 
                String.format("Stock insuficiente. Disponible: %.2f kg", stock.getKilos()));
        }

        Double nuevoStock = stock.getKilos() - cantidad;
        stock.setKilos(nuevoStock);
        Stock actualizado = stockRepository.save(stock);

        log.info("📦 Stock descontado exitosamente - ProductoID: {}, Nuevo_Stock: {}", 
            productoId, nuevoStock);

        return actualizado;
    }

    public Stock agregarStock(Long productoId, Double cantidad) {
        log.info("Adición de stock - ProductoID: {}, Cantidad: {}", productoId, cantidad);

        Stock stock = stockRepository.findByProductoId(productoId)
                .orElseThrow(() -> new BusinessException("PRODUCTO_NO_EXISTE", 
                    "Producto no encontrado en inventario"));

        Double nuevoStock = stock.getKilos() + cantidad;
        stock.setKilos(nuevoStock);
        Stock actualizado = stockRepository.save(stock);

        log.info("✅ Stock incrementado - ProductoID: {}, Nuevo_Stock: {}", productoId, nuevoStock);

        return actualizado;
    }
}
