package com.example.nutrihabit2.modelos;

import com.example.nutrihabit2.modelos.Alimento;

public class ConsumoAlimento {
    private Alimento alimento;
    private double cantidad;

    public ConsumoAlimento(Alimento alimento, double cantidadConsumida) {
        this.alimento = alimento;
        this.cantidad = cantidadConsumida;
    }

    public Alimento getAlimento() {
        return this.alimento;
    }

    public void setAlimento(Alimento alimento) {
        this.alimento = alimento;
    }

    public double getCantidadConsumida() {
        return this.cantidad;
    }

    public void setCantidadConsumida(double cantidadConsumida) {
        this.cantidad = cantidadConsumida;
    }
}
