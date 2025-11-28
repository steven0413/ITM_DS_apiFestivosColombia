import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.festivos.entidades.TipoFestivo;
import servicios.ITipoFestivoRepositorio;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para operaciones CRUD de tipos de festivos
 */
@RestController
@RequestMapping("/api/tipos-festivos")
public class TipoFestivoControlador {

    private final ITipoFestivoRepositorio tipoFestivoRepositorio;

    @Autowired
    public TipoFestivoControlador(ITipoFestivoRepositorio tipoFestivoRepositorio) {
        this.tipoFestivoRepositorio = tipoFestivoRepositorio;
    }

    @GetMapping
    public List<TipoFestivo> listarTodos() {
        return tipoFestivoRepositorio.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoFestivo> obtenerPorId(@PathVariable Long id) {
        Optional<TipoFestivo> tipoFestivo = tipoFestivoRepositorio.findById(id);
        return tipoFestivo.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TipoFestivo crear(@RequestBody TipoFestivo tipoFestivo) {
        return tipoFestivoRepositorio.save(tipoFestivo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoFestivo> actualizar(@PathVariable Long id, @RequestBody TipoFestivo tipoFestivo) {
        if (!tipoFestivoRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tipoFestivo.setId(id);
        return ResponseEntity.ok(tipoFestivoRepositorio.save(tipoFestivo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!tipoFestivoRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tipoFestivoRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}