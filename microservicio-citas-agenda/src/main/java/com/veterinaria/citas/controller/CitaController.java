package com.veterinaria.citas.controller;

import com.veterinaria.citas.model.Cita;
import com.veterinaria.citas.service.CitaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public List<Cita> listar() {
        return citaService.listar();
    }

    @GetMapping("/{id}")
    public Cita obtener(@PathVariable Long id) {
        return citaService.obtener(id);
    }

    @PostMapping
    public Cita crear(@RequestBody Cita cita) {
        return citaService.crear(cita);
    }

    @PutMapping("/{id}/confirmar")
    public Cita confirmar(@PathVariable Long id) {
        return citaService.confirmar(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        citaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
