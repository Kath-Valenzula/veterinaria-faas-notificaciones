package com.veterinaria.inventario.repository;

import com.veterinaria.inventario.model.ItemInventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemInventarioRepository extends JpaRepository<ItemInventario, Long> {
}
