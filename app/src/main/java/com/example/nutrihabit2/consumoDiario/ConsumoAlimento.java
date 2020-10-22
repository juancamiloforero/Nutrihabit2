package com.example.nutrihabit2.consumoDiario;

import com.example.nutrihabit2.alimentos.Alimento;

public class ConsumoAlimento {
    private Alimento alimento;
    private double cantidadConsumida;

    public ConsumoAlimento(Alimento alimento, double cantidadConsumida) {
        this.alimento = alimento;
        this.cantidadConsumida = cantidadConsumida;
    }

    public Alimento getAlimento() {
        return this.alimento;
    }

    public void setAlimento(Alimento alimento) {
        this.alimento = alimento;
    }

    public double getCantidadConsumida() {
        return this.cantidadConsumida;
    }

    public void setCantidadConsumida(double cantidadConsumida) {
        this.cantidadConsumida = cantidadConsumida;
    }
}
