package com.festivos.repositorio;

import com.festivos.entidades.TipoFestivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITipoFestivoRepositorio extends JpaRepository<TipoFestivo, Long> {
    
    // Buscar tipo por nombre exacto
    Optional<TipoFestivo> findByTipo(String tipo);
    
    // Verificar si existe un tipo con el nombre
    boolean existsByTipo(String tipo);
    
    // Obtener todos los tipos ordenados por ID
    List<TipoFestivo> findAllByOrderByIdAsc();
    
    // Buscar tipos que contengan el texto en el nombre
    List<TipoFestivo> findByTipoContainingIgnoreCase(String tipo);
}