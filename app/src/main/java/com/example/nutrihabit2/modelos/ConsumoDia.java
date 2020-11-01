package com.example.nutrihabit2.modelos;

import java.util.ArrayList;

public class ConsumoDia {
    private ArrayList<ConsumoAlimento> consumosDiarios;
    private String dia;

    public ConsumoDia(ArrayList<ConsumoAlimento> consumosDiarios, String dia) {
        this.consumosDiarios = consumosDiarios;
        this.dia = dia;
    }

    public ArrayList<ConsumoAlimento> getConsumosDiarios() {
        return consumosDiarios;
    }

    public void setConsumosDiarios(ArrayList<ConsumoAlimento> consumosDiarios) {
        this.consumosDiarios = consumosDiarios;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }
}
