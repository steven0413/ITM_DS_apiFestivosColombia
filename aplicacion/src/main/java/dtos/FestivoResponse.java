import java.time.LocalDate;

// DTO para la respuesta de festivos
public class FestivoResponse {
    private String festivo;
    private LocalDate fecha;
    private String tipo;
    
    // Constructores
    public FestivoResponse() {}
    
    public FestivoResponse(String festivo, LocalDate fecha, String tipo) {
        this.festivo = festivo;
        this.fecha = fecha;
        this.tipo = tipo;
    }
    
    // Getters y Setters
    public String getFestivo() { return festivo; }
    public void setFestivo(String festivo) { this.festivo = festivo; }
    
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}