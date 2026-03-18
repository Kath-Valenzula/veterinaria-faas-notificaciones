package com.veterinaria.facturacion.repository;

import com.veterinaria.facturacion.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
}
