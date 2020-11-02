package com.example.nutrihabit2.modelos;

import java.util.ArrayList;
import java.util.Date;

public class ConsumoDia {
    private ArrayList<ConsumoAlimento> consumo;
    private Date timestamp;

    public ConsumoDia() { }

    public ConsumoDia(ArrayList<ConsumoAlimento> consumosDiarios, Date timestamp) {
        this.consumo = consumosDiarios;
        this.timestamp = timestamp;
    }

    public ArrayList<ConsumoAlimento> getConsumosDiarios() {
        return consumo;
    }

    public void setConsumosDiarios(ArrayList<ConsumoAlimento> consumosDiarios) {
        this.consumo = consumosDiarios;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
