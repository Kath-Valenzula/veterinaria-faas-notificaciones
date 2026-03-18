package com.veterinaria.historial.controller;

import com.veterinaria.historial.model.Diagnostico;
import com.veterinaria.historial.service.DiagnosticoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class DiagnosticoController {

    private final DiagnosticoService diagnosticoService;

    public DiagnosticoController(DiagnosticoService diagnosticoService) {
        this.diagnosticoService = diagnosticoService;
    }

    @GetMapping("/api/diagnosticos")
    public List<Diagnostico> listarDiagnosticos() {
        return diagnosticoService.listar();
    }

    @PostMapping("/api/diagnosticos")
    public Diagnostico crearDiagnostico(@RequestBody Diagnostico diagnostico) {
        return diagnosticoService.crear(diagnostico);
    }

    @GetMapping("/api/laboratorios-externos/resultados/{mascotaId}")
    public Map<String, Object> consultarLaboratorio(@PathVariable Long mascotaId) {
        return diagnosticoService.consultarLaboratorioExterno(mascotaId);
    }
}
