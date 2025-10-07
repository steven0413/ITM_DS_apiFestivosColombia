package com.festivos.repositorio;

import com.festivos.entidades.Festivo;
import com.festivos.entidades.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IFestivoRepositorio extends JpaRepository<Festivo, Long> {
    
    // Buscar festivos por país
    List<Festivo> findByPais(Pais pais);
    
    // Buscar festivos por país ordenados por mes y día
    List<Festivo> findByPaisOrderByMesAscDiaAsc(Pais pais);
    
    // Buscar festivo por país y nombre
    Optional<Festivo> findByPaisAndNombre(Pais pais, String nombre);
    
    // Verificar si existe un festivo con el mismo nombre en el país
    boolean existsByPaisAndNombre(Pais pais, String nombre);
    
    // Buscar festivos por país y tipo
    List<Festivo> findByPaisAndTipoId(Pais pais, Long tipoId);
    
    // Buscar festivos fijos por día y mes (para validación de fechas)
    @Query("SELECT f FROM Festivo f WHERE f.pais = :pais AND f.dia = :dia AND f.mes = :mes AND f.diasPascua = 0")
    List<Festivo> findFestivosFijosByFecha(@Param("pais") Pais pais, @Param("dia") int dia, @Param("mes") int mes);
    
    // Buscar festivos por país y mes
    List<Festivo> findByPaisAndMes(Pais pais, int mes);
    
    // Contar festivos por país
    long countByPais(Pais pais);
    
    // Eliminar festivos por país
    void deleteByPais(Pais pais);
}