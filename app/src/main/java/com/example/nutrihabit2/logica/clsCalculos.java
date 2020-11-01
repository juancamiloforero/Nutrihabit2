package com.example.nutrihabit2.logica;

public class clsCalculos {

    public String calcularIMC(float estatura, float peso) {
        estatura = estatura / 100;
        float imc = peso / (estatura * estatura);
        return String.format("%.1f", imc);
    }

    public float calcularIMC2(float estatura, float peso) {
        estatura = estatura / 100;
        float imc = peso / (estatura * estatura);
        return imc;
    }

    public String clasificarIMC(float parIMC) {
        String clasificacion = "Vacio";
        if (parIMC < 18.5) {
            clasificacion = "Peso inferior al normal";
        } else if (parIMC >= 18.5 && parIMC < 25) {
            clasificacion = "Peso Normal";
        } else if (parIMC >= 25 && parIMC < 30) {
            clasificacion = "Peso superior al normal";
        } else if (parIMC > 30) {
            clasificacion = "Obesidad";
        }
        return clasificacion;
    }

    private int clasificarIMC2(float parIMC) {
        int clasificacion = 0;
        if (parIMC < 18.5) {
            clasificacion = 1;
        } else if (parIMC >= 18.5 && parIMC < 25) {
            clasificacion = 2;
        } else if (parIMC >= 25 && parIMC < 30) {
            clasificacion = 3;
        } else if (parIMC > 30) {
            clasificacion = 4;
        }
        return clasificacion;
    }


    public double calcularCalorias(String genero, float estatura, float peso, float edad, String nivelActividad, int objetivo) {
        double mb;
        if (genero.equals("Hombre")) {
            mb = (10 * peso) + (6.25 * estatura) - (5 * edad) + 5;
        } else {
            mb = (10 * peso) + (6.25 * estatura) - (5 * edad) - 161;
        }
        switch (nivelActividad) {
            case "Bajo":
                mb = mb * 1.2;
                break;
            case "Moderado":
                mb = mb * 1.375;
                break;
            case "Alto":
                mb = mb * 1.55;
                break;
            case "Muy Alto":
                mb = mb * 1.725;
                break;
        }
        if (objetivo == 1) {
            mb = mb * 1.20;
        } else if (objetivo == 2) {
            mb = mb * 0.80;
        }
        return mb;
    }

}
