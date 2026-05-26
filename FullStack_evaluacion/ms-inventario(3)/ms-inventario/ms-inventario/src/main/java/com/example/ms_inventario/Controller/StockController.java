package com.example.ms_inventario.Controller;

import com.example.ms_inventario.Dto.StockDTO;
import com.example.ms_inventario.Model.Stock;
import com.example.ms_inventario.Service.StockService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public List<Stock> listarTodo() {
        return stockService.listarTodo();
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<Stock> obtenerPorProducto(@PathVariable Long productoId) {
        return stockService.obtenerPorProductoId(productoId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Stock guardar(@Valid @RequestBody Stock stock) {
        return stockService.guardar(stock);
    }

    @PostMapping("/descontar")
    public ResponseEntity<Stock> descontarStock(@RequestParam Long productoId, @RequestParam Double cantidad) {
        return ResponseEntity.ok(stockService.descontarStock(productoId, cantidad));
    }

    @PostMapping("/agregar")
    public ResponseEntity<Stock> agregarStock(@RequestParam Long productoId, @RequestParam Double cantidad) {
        return ResponseEntity.ok(stockService.agregarStock(productoId, cantidad));
    }

    @PostMapping("/reservar")
    public ResponseEntity<Void> reservar(@Valid @RequestBody StockDTO stockDTO) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        stockService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
