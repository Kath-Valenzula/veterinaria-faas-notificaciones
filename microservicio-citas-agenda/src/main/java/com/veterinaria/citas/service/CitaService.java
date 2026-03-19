package com.veterinaria.citas.service;

import com.veterinaria.citas.model.Cita;
import com.veterinaria.citas.repository.CitaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CitaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CitaService.class);

    private final CitaRepository citaRepository;
    private final RestTemplate restTemplate;

    @Value("${faas.notificaciones.url}")
    private String faasUrl;

    public CitaService(CitaRepository citaRepository, RestTemplate restTemplate) {
        this.citaRepository = citaRepository;
        this.restTemplate = restTemplate;
    }

    public List<Cita> listar() {
        return citaRepository.findAll();
    }

    public Cita obtener(Long id) {
        return citaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));
    }

    public Cita crear(Cita cita) {
        if (cita.getEstado() == null || cita.getEstado().isBlank()) {
            cita.setEstado("PENDIENTE");
        }
        if (cita.getFechaHora() == null) {
            cita.setFechaHora(LocalDateTime.now().plusDays(1));
        }
        return citaRepository.save(cita);
    }

    public Cita confirmar(Long id) {
        Cita cita = obtener(id);
        cita.setEstado("CONFIRMADA");
        Cita guardada = citaRepository.save(cita);

        Map<String, Object> payload = Map.of(
                "citaId", guardada.getId(),
                "cliente", guardada.getNombreCliente(),
                "mascota", guardada.getNombreMascota(),
                "fechaHora", guardada.getFechaHora().toString(),
                "estado", guardada.getEstado()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(faasUrl, request, String.class);
            LOGGER.info("Notificacion enviada a FaaS. Status: {}", response.getStatusCode());
        } catch (Exception ex) {
            LOGGER.warn("No se pudo enviar notificacion a FaaS (cita confirmada de todos modos): {}", ex.getMessage());
        }
        return guardada;
    }

    public void eliminar(Long id) {
        citaRepository.deleteById(id);
    }
}
