package com.veterinaria.historial.controller;

import com.veterinaria.historial.model.Mascota;
import com.veterinaria.historial.service.MascotaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @GetMapping
    public List<Mascota> listar() {
        return mascotaService.listar();
    }

    @GetMapping("/{id}")
    public Mascota obtener(@PathVariable Long id) {
        return mascotaService.obtener(id);
    }

    @PostMapping
    public Mascota crear(@RequestBody Mascota mascota) {
        return mascotaService.crear(mascota);
    }

    @PutMapping("/{id}")
    public Mascota actualizar(@PathVariable Long id, @RequestBody Mascota mascota) {
        return mascotaService.actualizar(id, mascota);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mascotaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
