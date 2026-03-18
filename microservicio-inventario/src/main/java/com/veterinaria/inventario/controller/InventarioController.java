package com.veterinaria.inventario.controller;

import com.veterinaria.inventario.model.ItemInventario;
import com.veterinaria.inventario.service.ItemInventarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    private final ItemInventarioService itemService;

    public InventarioController(ItemInventarioService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<ItemInventario> listar() {
        return itemService.listar();
    }

    @GetMapping("/{id}")
    public ItemInventario obtener(@PathVariable Long id) {
        return itemService.obtener(id);
    }

    @PostMapping
    public ItemInventario crear(@RequestBody ItemInventario item) {
        return itemService.crear(item);
    }

    @PutMapping("/{id}")
    public ItemInventario actualizar(@PathVariable Long id, @RequestBody ItemInventario item) {
        return itemService.actualizar(id, item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        itemService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
