package com.festivos.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "festivo")
public class Festivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)// Muchos festivos pueden pertenecer a un mismo país
    @JoinColumn(name = "idpais", nullable = false)// Clave foránea al país
    private Pais pais;
    
    @Column(name = "nombre", nullable = false, length = 100)// Nombre del festivo
    private String nombre;
    
    @Column(name = "dia", nullable = false)// 1-31
    private Integer dia;
    
    @Column(name = "mes", nullable = false)// 1-12
    private Integer mes;
    
    @Column(name = "diaspascua", nullable = false)// Días relativos a la Pascua (puede ser negativo)
    private Integer diasPascua;
    
    @ManyToOne(fetch = FetchType.LAZY) // Muchos festivos pueden ser de un mismo tipo
    @JoinColumn(name = "idtipo", nullable = false)
    private TipoFestivo tipo;
    
    // Constructores
    public Festivo() {}
    
    public Festivo(Pais pais, String nombre, Integer dia, Integer mes, Integer diasPascua, TipoFestivo tipo) {
        this.pais = pais;
        this.nombre = nombre;
        this.dia = dia;
        this.mes = mes;
        this.diasPascua = diasPascua;
        this.tipo = tipo;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Pais getPais() { return pais; }
    public void setPais(Pais pais) { this.pais = pais; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public Integer getDia() { return dia; }
    public void setDia(Integer dia) { this.dia = dia; }
    
    public Integer getMes() { return mes; }
    public void setMes(Integer mes) { this.mes = mes; }
    
    public Integer getDiasPascua() { return diasPascua; }
    public void setDiasPascua(Integer diasPascua) { this.diasPascua = diasPascua; }
    
    public TipoFestivo getTipo() { return tipo; }
    public void setTipo(TipoFestivo tipo) { this.tipo = tipo; }
}