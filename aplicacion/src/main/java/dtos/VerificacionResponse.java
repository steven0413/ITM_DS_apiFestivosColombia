/**
 * DTO para respuesta de verificación de fecha festiva
 */
public class VerificacionResponse {
    private boolean esFestivo;
    private String festivo;
    private String mensaje;
    
    // Constructores
    public VerificacionResponse() {}
    
    public VerificacionResponse(boolean esFestivo, String festivo) {
        this.esFestivo = esFestivo;
        this.festivo = festivo;
        this.mensaje = esFestivo ? "Es festivo" : "No es festivo";
    }
    
    public VerificacionResponse(boolean esFestivo, String festivo, String mensaje) {
        this.esFestivo = esFestivo;
        this.festivo = festivo;
        this.mensaje = mensaje;
    }
    
    // Getters y Setters
    public boolean isEsFestivo() { return esFestivo; }
    public void setEsFestivo(boolean esFestivo) { this.esFestivo = esFestivo; }
    
    public String getFestivo() { return festivo; }
    public void setFestivo(String festivo) { this.festivo = festivo; }
    
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}