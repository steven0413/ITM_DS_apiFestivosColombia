package com.festivos.repositorio;

import com.festivos.entidades.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPaisRepositorio extends JpaRepository<Pais, Long> {
    
    // Buscar país por nombre exacto
    Optional<Pais> findByNombre(String nombre);
    
    // Buscar países que contengan el texto en el nombre
    List<Pais> findByNombreContainingIgnoreCase(String nombre);
    
    // Verificar si existe un país con el nombre
    boolean existsByNombre(String nombre);
    
    // Obtener todos los países ordenados por nombre
    List<Pais> findAllByOrderByNombreAsc();
}