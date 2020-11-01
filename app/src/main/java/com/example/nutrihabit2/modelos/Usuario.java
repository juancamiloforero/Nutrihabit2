package com.example.nutrihabit2.modelos;

public class Usuario {

    private float estatura, peso;
    private int proposito, edad;
    private String genero, nivel_actividad;

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public float getEstatura() {
        return estatura;
    }

    public void setEstatura(float estatura) {
        this.estatura = estatura;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public int getProposito() {
        return proposito;
    }

    public void setProposito(int proposito) {
        this.proposito = proposito;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNivel_actividad() {
        return nivel_actividad;
    }

    public void setNivel_actividad(String nivel_actividad) {
        this.nivel_actividad = nivel_actividad;
    }

}
