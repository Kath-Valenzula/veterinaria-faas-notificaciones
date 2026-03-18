package com.veterinaria.bff.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/bff")
public class BffController {

    private final RestTemplate restTemplate;

    @Value("${services.historial.url}")
    private String historialUrl;

    @Value("${services.citas.url}")
    private String citasUrl;

    @Value("${services.inventario.url}")
    private String inventarioUrl;

    @Value("${services.facturacion.url}")
    private String facturacionUrl;

    public BffController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("BFF activo");
    }

    @PostMapping("/mascotas")
    public ResponseEntity<String> crearMascota(@RequestBody String payload) {
        return forward(historialUrl + "/api/mascotas", HttpMethod.POST, payload);
    }

    @GetMapping("/mascotas")
    public ResponseEntity<String> listarMascotas() {
        return forward(historialUrl + "/api/mascotas", HttpMethod.GET, null);
    }

    @PostMapping("/diagnosticos")
    public ResponseEntity<String> crearDiagnostico(@RequestBody String payload) {
        return forward(historialUrl + "/api/diagnosticos", HttpMethod.POST, payload);
    }

    @GetMapping("/laboratorios/{mascotaId}")
    public ResponseEntity<String> consultarLaboratorio(@PathVariable Long mascotaId) {
        return forward(historialUrl + "/api/laboratorios-externos/resultados/" + mascotaId, HttpMethod.GET, null);
    }

    @PostMapping("/citas")
    public ResponseEntity<String> crearCita(@RequestBody String payload) {
        return forward(citasUrl + "/api/citas", HttpMethod.POST, payload);
    }

    @PutMapping("/citas/{id}/confirmar")
    public ResponseEntity<String> confirmarCita(@PathVariable Long id) {
        return forward(citasUrl + "/api/citas/" + id + "/confirmar", HttpMethod.PUT, null);
    }

    @GetMapping("/citas")
    public ResponseEntity<String> listarCitas() {
        return forward(citasUrl + "/api/citas", HttpMethod.GET, null);
    }

    @PostMapping("/inventario")
    public ResponseEntity<String> crearItemInventario(@RequestBody String payload) {
        return forward(inventarioUrl + "/api/inventario", HttpMethod.POST, payload);
    }

    @GetMapping("/inventario")
    public ResponseEntity<String> listarInventario() {
        return forward(inventarioUrl + "/api/inventario", HttpMethod.GET, null);
    }

    @PostMapping("/facturas")
    public ResponseEntity<String> crearFactura(@RequestBody String payload) {
        return forward(facturacionUrl + "/api/facturas", HttpMethod.POST, payload);
    }

    @GetMapping("/facturas")
    public ResponseEntity<String> listarFacturas() {
        return forward(facturacionUrl + "/api/facturas", HttpMethod.GET, null);
    }

    private ResponseEntity<String> forward(String url, HttpMethod method, String payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(payload, headers);
        return restTemplate.exchange(url, method, requestEntity, String.class);
    }
}
