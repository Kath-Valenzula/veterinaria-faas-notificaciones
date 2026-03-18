package com.veterinaria.inventario.service;

import com.veterinaria.inventario.model.ItemInventario;
import com.veterinaria.inventario.repository.ItemInventarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemInventarioService {

    private final ItemInventarioRepository repository;

    public ItemInventarioService(ItemInventarioRepository repository) {
        this.repository = repository;
    }

    public List<ItemInventario> listar() {
        return repository.findAll();
    }

    public ItemInventario obtener(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item no encontrado"));
    }

    public ItemInventario crear(ItemInventario item) {
        return repository.save(item);
    }

    public ItemInventario actualizar(Long id, ItemInventario cambios) {
        ItemInventario actual = obtener(id);
        actual.setNombre(cambios.getNombre());
        actual.setTipo(cambios.getTipo());
        actual.setStock(cambios.getStock());
        actual.setPrecioUnitario(cambios.getPrecioUnitario());
        return repository.save(actual);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
