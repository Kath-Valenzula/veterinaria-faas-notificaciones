package com.veterinaria.citas.repository;

import com.veterinaria.citas.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaRepository extends JpaRepository<Cita, Long> {
}
