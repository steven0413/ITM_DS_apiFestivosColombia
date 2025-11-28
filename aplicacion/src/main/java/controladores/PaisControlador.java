import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.festivos.entidades.Pais;
import servicios.IPaisRepositorio;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para operaciones CRUD de países
 */
@RestController
@RequestMapping("/api/paises")
public class PaisControlador {

    private final IPaisRepositorio paisRepositorio;

    @Autowired
    public PaisControlador(IPaisRepositorio paisRepositorio) {
        this.paisRepositorio = paisRepositorio;
    }

    @GetMapping
    public List<Pais> listarTodos() {
        return paisRepositorio.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pais> obtenerPorId(@PathVariable Long id) {
        Optional<Pais> pais = paisRepositorio.findById(id);
        return pais.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pais crear(@RequestBody Pais pais) {
        return paisRepositorio.save(pais);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pais> actualizar(@PathVariable Long id, @RequestBody Pais pais) {
        if (!paisRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pais.setId(id);
        return ResponseEntity.ok(paisRepositorio.save(pais));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!paisRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        paisRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}