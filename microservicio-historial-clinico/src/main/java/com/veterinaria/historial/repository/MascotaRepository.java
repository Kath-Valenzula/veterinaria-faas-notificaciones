package com.veterinaria.historial.repository;

import com.veterinaria.historial.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
}
