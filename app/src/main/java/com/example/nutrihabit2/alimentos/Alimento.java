package com.example.nutrihabit2.alimentos;

import java.io.Serializable;

public class Alimento implements Serializable {

    private String id;
    private String nombre;
    private String descripcion;
    private String tipoAlimento;
    private String unidadMedida;
    private double porcion;
    private double calorias;
    private double grasas;
    private double carbohidratos;
    private double proteinas;

    public Alimento() { }

    public Alimento(String nombre, String descripcion, String tipoAlimento, String unidadMedida, double porcion, double calorias, double grasas, double carbohidratos, double proteinas) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoAlimento = tipoAlimento;
        this.unidadMedida = unidadMedida;
        this.porcion = porcion;
        this.calorias = calorias;
        this.grasas = grasas;
        this.carbohidratos = carbohidratos;
        this.proteinas = proteinas;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoAlimento() {
        return tipoAlimento;
    }

    public void setTipoAlimento(String tipoAlimento) {
        this.tipoAlimento = tipoAlimento;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public double getPorcion() {
        return porcion;
    }

    public void setPorcion(double porcion) {
        this.porcion = porcion;
    }

    public double getCalorias() {
        return calorias;
    }

    public void setCalorias(double calorias) {
        this.calorias = calorias;
    }

    public double getGrasas() {
        return grasas;
    }

    public void setGrasas(double grasas) {
        this.grasas = grasas;
    }

    public double getCarbohidratos() {
        return carbohidratos;
    }

    public void setCarbohidratos(double carbohidratos) {
        this.carbohidratos = carbohidratos;
    }

    public double getProteinas() {
        return proteinas;
    }

    public void setProteinas(double proteinas) {
        this.proteinas = proteinas;
    }
}
