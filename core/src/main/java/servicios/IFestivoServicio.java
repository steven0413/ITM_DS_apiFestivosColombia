package com.festivos.core.interfaces.servicios;

import com.festivos.entidades.Festivo;
import com.festivos.entidades.Pais;

import java.time.LocalDate;
import java.util.List;

/**
 Interfaz core para el servicio de gestión de festivos
 */
public interface IFestivoServicio {
    
    // Operaciones CRUD 
    List<Festivo> listarTodos();
    Festivo obtenerPorId(Long id);
    Festivo guardar(Festivo festivo);
    Festivo actualizar(Long id, Festivo festivo);
    void eliminar(Long id);
    
    // Operaciones específicas de festivos
    List<Festivo> listarFestivosPorPaisYAnio(Long paisId, int anio);
    boolean esFechaFestiva(Long paisId, int anio, int mes, int dia);
    List<Festivo> listarFestivosPorPais(Long paisId);
    
    // Operaciones de validación
    boolean existeFestivoConNombreEnPais(String nombre, Long paisId);
    boolean validarFecha(int anio, int mes, int dia);
}