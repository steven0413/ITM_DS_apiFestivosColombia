import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import servicios.IFestivoServicio;
import servicios.IFestivoCalculatorServicio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador REST para gestión de festivos
 * Implementa los endpoints específicos del examen
 */
@RestController
@RequestMapping("/api/calendario")
public class FestivoControlador {

    private final IFestivoServicio festivoServicio;
    private final IFestivoCalculatorServicio festivoCalculator;

    @Autowired
    public FestivoControlador(IFestivoServicio festivoServicio, IFestivoCalculatorServicio festivoCalculator) {
        this.festivoServicio = festivoServicio;
        this.festivoCalculator = festivoCalculator;
    }

    /**
     * Endpoint para verificar si una fecha es festiva
     * GET /api/calendario/verificar/{paisId}/{año}/{mes}/{dia}
     */
    @GetMapping("/verificar/{paisId}/{año}/{mes}/{dia}")
    public ResponseEntity<VerificacionResponse> verificarFecha(
            @PathVariable Long paisId,
            @PathVariable int año,
            @PathVariable int mes,
            @PathVariable int dia) {
        
        // Validar parámetros básicos
        if (paisId == null || paisId <= 0) {
            return ResponseEntity.badRequest()
                    .body(new VerificacionResponse(false, null, "ID de país inválido"));
        }
        
        if (!festivoServicio.validarFecha(año, mes, dia)) {
            return ResponseEntity.badRequest()
                    .body(new VerificacionResponse(false, null, "Fecha inválida"));
        }
        
        try {
            boolean esFestivo = festivoServicio.esFechaFestiva(paisId, año, mes, dia);
            String nombreFestivo = esFestivo ? obtenerNombreFestivo(paisId, año, mes, dia) : null;
            
            VerificacionResponse response = new VerificacionResponse(esFestivo, nombreFestivo);
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new VerificacionResponse(false, null, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new VerificacionResponse(false, null, "Error interno del servidor"));
        }
    }

    /**
     * Endpoint para listar festivos de un país durante un año
     * GET /api/calendario/festivos/{paisId}/{año}
     */
    @GetMapping("/festivos/{paisId}/{año}")
    public ResponseEntity<List<FestivoResponse>> listarFestivos(
            @PathVariable Long paisId,
            @PathVariable int año) {
        
        if (paisId == null || paisId <= 0) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }
        
        try {
            List<FestivoResponse> festivos = new ArrayList<>();
            var festivosDominio = festivoServicio.listarFestivosPorPaisYAnio(paisId, año);
            
            for (var festivo : festivosDominio) {
                try {
                    LocalDate fechaCalculada = festivoCalculator.calcularFechaFestivo(festivo, año);
                    FestivoResponse response = new FestivoResponse(
                        festivo.getNombre(),
                        fechaCalculada,
                        festivo.getTipo().getTipo()
                    );
                    festivos.add(response);
                } catch (Exception e) {
                    // Continuar con siguiente festivo si hay error en cálculo
                    continue;
                }
            }
            
            return ResponseEntity.ok(festivos);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ArrayList<>());
        }
    }

    /**
     * Método auxiliar para obtener el nombre del festivo en una fecha específica
     */
    private String obtenerNombreFestivo(Long paisId, int año, int mes, int dia) {
        try {
            LocalDate fechaTarget = LocalDate.of(año, mes, dia);
            var festivos = festivoServicio.listarFestivosPorPaisYAnio(paisId, año);
            
            for (var festivo : festivos) {
                try {
                    LocalDate fechaFestivo = festivoCalculator.calcularFechaFestivo(festivo, año);
                    if (fechaFestivo.equals(fechaTarget)) {
                        return festivo.getNombre();
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            return "Festivo";
        } catch (Exception e) {
            return "Festivo";
        }
    }
}