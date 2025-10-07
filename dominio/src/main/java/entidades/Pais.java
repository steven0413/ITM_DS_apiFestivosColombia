package com.festivos.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "pais")// Tabla para almacenar países
public class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// Identificador único del país
    private Long id;

    // Nombre del país
    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;
    
    // Constructores
    public Pais() {}
    
    // Constructor con parámetros
    public Pais(String nombre) {
        this.nombre = nombre;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}