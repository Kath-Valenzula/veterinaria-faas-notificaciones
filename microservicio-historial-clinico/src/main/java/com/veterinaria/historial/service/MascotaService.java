package com.veterinaria.historial.service;

import com.veterinaria.historial.model.Mascota;
import com.veterinaria.historial.repository.MascotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;

    public MascotaService(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    public List<Mascota> listar() {
        return mascotaRepository.findAll();
    }

    public Mascota obtener(Long id) {
        return mascotaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada"));
    }

    public Mascota crear(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    public Mascota actualizar(Long id, Mascota cambios) {
        Mascota actual = obtener(id);
        actual.setNombre(cambios.getNombre());
        actual.setEspecie(cambios.getEspecie());
        actual.setRaza(cambios.getRaza());
        actual.setEdad(cambios.getEdad());
        actual.setNombreDueno(cambios.getNombreDueno());
        return mascotaRepository.save(actual);
    }

    public void eliminar(Long id) {
        mascotaRepository.deleteById(id);
    }
}
