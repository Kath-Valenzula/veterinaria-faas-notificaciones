package com.veterinaria.historial.repository;

import com.veterinaria.historial.model.Diagnostico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosticoRepository extends JpaRepository<Diagnostico, Long> {
}
