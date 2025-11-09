package com.festivos.core.interfaces.servicios;

import com.festivos.entidades.Festivo;

import java.time.LocalDate;

/**
 * Interfaz core para el servicio de cálculo de fechas festivas
 */
public interface IFestivoCalculatorServicio {
    
    /**
     * Calcula la fecha real de un festivo para un año específico
     * Aplica las reglas según el tipo de festivo
     * 
     * @param festivo Festivo a calcular
     * @param anio Año objetivo
     * @return Fecha calculada del festivo
     */
    LocalDate calcularFechaFestivo(Festivo festivo, int anio);
    
    /**
     * Ajusta una fecha al siguiente lunes (Ley de Puente Festivo)
     * 
     * @param fecha Fecha original
     * @return Fecha ajustada al siguiente lunes
     */
    LocalDate ajustarAlSiguienteLunes(LocalDate fecha);
    
    /**
     * Calcula fecha basada en Pascua con días de desplazamiento
     * 
     * @param domingoPascua Fecha del domingo de Pascua
     * @param diasDesplazamiento Días a sumar/restar
     * @return Fecha calculada
     */
    LocalDate calcularBasadoEnPascua(LocalDate domingoPascua, int diasDesplazamiento);
}