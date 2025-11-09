package com.festivos.servicios;

import com.festivos.core.interfaces.servicios.ICalculadoraPascuaServicio;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CalculadoraPascuaService implements ICalculadoraPascuaServicio {

    @Override
    public LocalDate calcularDomingoPascua(int anio) {
        int a = anio % 19;
        int b = anio % 4;
        int c = anio % 7;
        int d = (19 * a + 24) % 30;
        int dias = d + (2 * b + 4 * c + 6 * d + 5) % 7;
        
        // Domingo de Ramos: 15 de marzo más días calculados
        LocalDate domingoRamos = LocalDate.of(anio, 3, 15).plusDays(dias);
        
        // Domingo de Pascua: 7 días después del Domingo de Ramos
        return domingoRamos.plusDays(7);
    }

    @Override
    public boolean validarAnio(int anio) {
        return anio >= 1900 && anio <= 2100;
    }
}