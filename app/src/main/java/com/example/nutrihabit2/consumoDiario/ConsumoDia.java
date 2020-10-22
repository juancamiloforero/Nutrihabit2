package com.example.nutrihabit2.consumoDiario;

import java.util.ArrayList;
import java.util.Date;

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
