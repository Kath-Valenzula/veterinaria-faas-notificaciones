package com.veterinaria.notificaciones;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.*;

import java.util.Optional;
import java.util.logging.Logger;

public class EnviarNotificacionCita {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @FunctionName("EnviarNotificacionCita")
    public HttpResponseMessage run(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.POST},
                    authLevel = AuthorizationLevel.ANONYMOUS,
                    route = "EnviarNotificacionCita")
            HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        Logger logger = context.getLogger();
        String requestBody = request.getBody().orElse("{}");

        try {
            NotificacionRequest payload = OBJECT_MAPPER.readValue(requestBody, NotificacionRequest.class);

            logger.info("=== Simulacion envio notificacion ===");
            logger.info("Cliente: " + payload.getCliente());
            logger.info("Mascota: " + payload.getMascota());
            logger.info("Fecha/Hora cita: " + payload.getFechaHora());
            logger.info("Estado: " + payload.getEstado());
            logger.info("Notificacion simulada enviada por correo/SMS");

            return request.createResponseBuilder(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body("{\"mensaje\":\"Notificacion procesada correctamente\"}")
                    .build();
        } catch (Exception ex) {
            logger.warning("Error procesando notificacion: " + ex.getMessage());
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "application/json")
                    .body("{\"error\":\"Payload invalido\"}")
                    .build();
        }
    }

    public static class NotificacionRequest {
        private Long citaId;
        private String cliente;
        private String mascota;
        private String fechaHora;
        private String estado;

        public Long getCitaId() {
            return citaId;
        }

        public void setCitaId(Long citaId) {
            this.citaId = citaId;
        }

        public String getCliente() {
            return cliente;
        }

        public void setCliente(String cliente) {
            this.cliente = cliente;
        }

        public String getMascota() {
            return mascota;
        }

        public void setMascota(String mascota) {
            this.mascota = mascota;
        }

        public String getFechaHora() {
            return fechaHora;
        }

        public void setFechaHora(String fechaHora) {
            this.fechaHora = fechaHora;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }
    }
}
