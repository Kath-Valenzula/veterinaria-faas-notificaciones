package com.veterinaria.facturacion.controller;

import com.veterinaria.facturacion.model.Factura;
import com.veterinaria.facturacion.service.FacturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @GetMapping
    public List<Factura> listar() {
        return facturaService.listar();
    }

    @GetMapping("/{id}")
    public Factura obtener(@PathVariable Long id) {
        return facturaService.obtener(id);
    }

    @PostMapping
    public Factura crear(@RequestBody Factura factura) {
        return facturaService.crear(factura);
    }

    @PutMapping("/{id}")
    public Factura actualizar(@PathVariable Long id, @RequestBody Factura factura) {
        return facturaService.actualizar(id, factura);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        facturaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
