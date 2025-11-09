package com.festivos.core.interfaces.servicios;

import java.time.LocalDate;

/**
 * Interfaz core para el servicio de cálculo de Pascua
 * Define el algoritmo exacto proporcionado en el examen
 */
public interface ICalculadoraPascuaServicio {
    
    /**
     * Calcula la fecha del domingo de Pascua para un año dado
     * SIGUE EXACTAMENTE el algoritmo proporcionado en el examen
     * 
     * @param anio Año para calcular la Pascua
     * @return Fecha del domingo de Pascua
     */
    LocalDate calcularDomingoPascua(int anio);
    
    /**
     * Valida si un año es válido para cálculo
     * 
     * @param anio Año a validar
     * @return true si el año es válido
     */
    boolean validarAnio(int anio);
}