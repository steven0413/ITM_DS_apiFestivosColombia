package com.festivos.servicios;

import com.festivos.core.interfaces.servicios.IFestivoServicio;
import com.festivos.core.interfaces.servicios.IFestivoCalculatorServicio;
import com.festivos.entidades.Festivo;
import com.festivos.entidades.Pais;
import com.festivos.repositorio.IFestivoRepositorio;
import com.festivos.repositorio.IPaisRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FestivoService implements IFestivoServicio {

    private final IFestivoRepositorio festivoRepositorio;
    private final IPaisRepositorio paisRepositorio;
    private final IFestivoCalculatorServicio festivoCalculator;

    public FestivoService(
            IFestivoRepositorio festivoRepositorio,
            IPaisRepositorio paisRepositorio,
            IFestivoCalculatorServicio festivoCalculator) {
        this.festivoRepositorio = festivoRepositorio;
        this.paisRepositorio = paisRepositorio;
        this.festivoCalculator = festivoCalculator;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Festivo> listarTodos() {
        return festivoRepositorio.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Festivo obtenerPorId(Long id) {
        return festivoRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Festivo no encontrado con ID: " + id));
    }

    @Override
    public Festivo guardar(Festivo festivo) {
        validarFestivo(festivo);
        
        if (existeFestivoConNombreEnPais(festivo.getNombre(), festivo.getPais().getId())) {
            throw new IllegalArgumentException("Ya existe un festivo con el nombre '" + festivo.getNombre() + "' en el país especificado");
        }
        return festivoRepositorio.save(festivo);
    }

    @Override
    public Festivo actualizar(Long id, Festivo festivo) {
        if (!festivoRepositorio.existsById(id)) {
            throw new IllegalArgumentException("Festivo no encontrado con ID: " + id);
        }
        
        validarFestivo(festivo);
        festivo.setId(id);
        return festivoRepositorio.save(festivo);
    }

    @Override
    public void eliminar(Long id) {
        if (!festivoRepositorio.existsById(id)) {
            throw new IllegalArgumentException("Festivo no encontrado con ID: " + id);
        }
        festivoRepositorio.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Festivo> listarFestivosPorPaisYAnio(Long paisId, int anio) {
        Pais pais = obtenerPaisValido(paisId);
        if (!festivoCalculator instanceof ICalculadoraPascuaServicio calculadora) {
            throw new IllegalStateException("Servicio de calculadora no disponible");
        }
        
        if (!calculadora.validarAnio(anio)) {
            throw new IllegalArgumentException("Año inválido: " + anio);
        }
        
        return festivoRepositorio.findByPais(pais);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean esFechaFestiva(Long paisId, int anio, int mes, int dia) {
        if (!validarFecha(anio, mes, dia)) {
            return false;
        }

        Pais pais = obtenerPaisValido(paisId);
        List<Festivo> festivos = festivoRepositorio.findByPais(pais);
        LocalDate fechaTarget = LocalDate.of(anio, mes, dia);

        for (Festivo festivo : festivos) {
            try {
                LocalDate fechaFestivo = festivoCalculator.calcularFechaFestivo(festivo, anio);
                if (fechaFestivo.equals(fechaTarget)) {
                    return true;
                }
            } catch (IllegalArgumentException e) {
                // Continuar con siguiente festivo si hay error en cálculo
                continue;
            }
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Festivo> listarFestivosPorPais(Long paisId) {
        Pais pais = obtenerPaisValido(paisId);
        return festivoRepositorio.findByPaisOrderByMesAscDiaAsc(pais);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeFestivoConNombreEnPais(String nombre, Long paisId) {
        Pais pais = obtenerPaisValido(paisId);
        return festivoRepositorio.existsByPaisAndNombre(pais, nombre);
    }

    @Override
    public boolean validarFecha(int anio, int mes, int dia) {
        try {
            LocalDate.of(anio, mes, dia);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    // Métodos privados de validación
    private Pais obtenerPaisValido(Long paisId) {
        return paisRepositorio.findById(paisId)
                .orElseThrow(() -> new IllegalArgumentException("País no encontrado con ID: " + paisId));
    }

    private void validarFestivo(Festivo festivo) {
        if (festivo.getPais() == null) {
            throw new IllegalArgumentException("El festivo debe tener un país asociado");
        }
        
        if (festivo.getTipo() == null) {
            throw new IllegalArgumentException("El festivo debe tener un tipo asociado");
        }
        
        // Validar según el tipo
        Long tipoId = festivo.getTipo().getId();
        if (tipoId == 1 || tipoId == 2) {
            // Tipos 1 y 2 requieren día y mes específicos
            if (festivo.getDia() == 0 || festivo.getMes() == 0) {
                throw new IllegalArgumentException("Los festivos de tipo " + tipoId + " requieren día y mes específicos");
            }
        } else if (tipoId == 3 || tipoId == 4) {
            // Tipos 3 y 4 requieren días de Pascua
            if (festivo.getDiasPascua() == 0) {
                throw new IllegalArgumentException("Los festivos de tipo " + tipoId + " requieren días de Pascua");
            }
        }
    }
}