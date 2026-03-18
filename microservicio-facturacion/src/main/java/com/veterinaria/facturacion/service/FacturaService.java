package com.veterinaria.facturacion.service;

import com.veterinaria.facturacion.model.Factura;
import com.veterinaria.facturacion.repository.FacturaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FacturaService {

    private final FacturaRepository facturaRepository;

    public FacturaService(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    public List<Factura> listar() {
        return facturaRepository.findAll();
    }

    public Factura obtener(Long id) {
        return facturaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));
    }

    public Factura crear(Factura factura) {
        if (factura.getFechaEmision() == null) {
            factura.setFechaEmision(LocalDateTime.now());
        }
        if (factura.getEstadoPago() == null || factura.getEstadoPago().isBlank()) {
            factura.setEstadoPago("PENDIENTE");
        }
        return facturaRepository.save(factura);
    }

    public Factura actualizar(Long id, Factura cambios) {
        Factura actual = obtener(id);
        actual.setNombreCliente(cambios.getNombreCliente());
        actual.setConcepto(cambios.getConcepto());
        actual.setMonto(cambios.getMonto());
        actual.setEstadoPago(cambios.getEstadoPago());
        return facturaRepository.save(actual);
    }

    public void eliminar(Long id) {
        facturaRepository.deleteById(id);
    }
}
