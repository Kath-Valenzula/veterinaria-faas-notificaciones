package com.veterinaria.historial.service;

import com.veterinaria.historial.model.Diagnostico;
import com.veterinaria.historial.repository.DiagnosticoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class DiagnosticoService {

    private final DiagnosticoRepository diagnosticoRepository;

    public DiagnosticoService(DiagnosticoRepository diagnosticoRepository) {
        this.diagnosticoRepository = diagnosticoRepository;
    }

    public List<Diagnostico> listar() {
        return diagnosticoRepository.findAll();
    }

    public Diagnostico crear(Diagnostico diagnostico) {
        if (diagnostico.getFecha() == null) {
            diagnostico.setFecha(LocalDate.now());
        }
        return diagnosticoRepository.save(diagnostico);
    }

    public Map<String, Object> consultarLaboratorioExterno(Long mascotaId) {
        return Map.of(
                "mascotaId", mascotaId,
                "proveedor", "LaboratorioVet Externo",
                "resultado", "Hemograma dentro de parametros normales",
                "fecha", LocalDate.now().toString()
        );
    }
}
