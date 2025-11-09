package com.festivos.servicios;

import com.festivos.core.interfaces.servicios.IFestivoCalculatorServicio;
import com.festivos.core.interfaces.servicios.ICalculadoraPascuaServicio;
import com.festivos.entidades.Festivo;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
public class FestivoCalculatorService implements IFestivoCalculatorServicio {

    private final ICalculadoraPascuaServicio calculadoraPascua;

    public FestivoCalculatorService(ICalculadoraPascuaServicio calculadoraPascua) {
        this.calculadoraPascua = calculadoraPascua;
    }

    @Override
    public LocalDate calcularFechaFestivo(Festivo festivo, int anio) {
        if (!calculadoraPascua.validarAnio(anio)) {
            throw new IllegalArgumentException("Año inválido: " + anio);
        }

        return switch (festivo.getTipo().getId().intValue()) {
            case 1 -> calcularFestivoFijo(festivo, anio);
            case 2 -> calcularFestivoPuente(festivo, anio);
            case 3 -> calcularFestivoBasadoPascua(festivo, anio);
            case 4 -> calcularFestivoPascuaPuente(festivo, anio);
            default -> throw new IllegalArgumentException("Tipo de festivo no soportado: " + festivo.getTipo().getId());
        };
    }

    @Override
    public LocalDate ajustarAlSiguienteLunes(LocalDate fecha) {
        DayOfWeek diaSemana = fecha.getDayOfWeek();
        
        return switch (diaSemana) {
            case MONDAY -> fecha;
            case SUNDAY -> fecha.plusDays(1);
            default -> {
                int diasHastaLunes = 8 - diaSemana.getValue();
                yield fecha.plusDays(diasHastaLunes);
            }
        };
    }

    @Override
    public LocalDate calcularBasadoEnPascua(LocalDate domingoPascua, int diasDesplazamiento) {
        return domingoPascua.plusDays(diasDesplazamiento);
    }

    private LocalDate calcularFestivoFijo(Festivo festivo, int anio) {
        return LocalDate.of(anio, festivo.getMes(), festivo.getDia());
    }

    private LocalDate calcularFestivoPuente(Festivo festivo, int anio) {
        LocalDate fechaBase = LocalDate.of(anio, festivo.getMes(), festivo.getDia());
        return ajustarAlSiguienteLunes(fechaBase);
    }

    private LocalDate calcularFestivoBasadoPascua(Festivo festivo, int anio) {
        LocalDate domingoPascua = calculadoraPascua.calcularDomingoPascua(anio);
        return calcularBasadoEnPascua(domingoPascua, festivo.getDiasPascua());
    }

    private LocalDate calcularFestivoPascuaPuente(Festivo festivo, int anio) {
        LocalDate fechaPascua = calcularFestivoBasadoPascua(festivo, anio);
        return ajustarAlSiguienteLunes(fechaPascua);
    }
}