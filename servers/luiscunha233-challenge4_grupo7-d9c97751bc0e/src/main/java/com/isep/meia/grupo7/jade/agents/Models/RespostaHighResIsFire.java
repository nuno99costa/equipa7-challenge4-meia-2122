package com.isep.meia.grupo7.jade.agents.Models;

import java.io.Serializable;
import java.util.Random;

public class RespostaHighResIsFire implements Serializable {


    private String idOrigem;
    private boolean resultado;

    public RespostaHighResIsFire(String idOrigem) {
        this.idOrigem = idOrigem;
        Random objGenerator = new Random();
        this.resultado = objGenerator.nextBoolean();
    }


    public String getIdOrigem() {
        return idOrigem;
    }

    public void setIdOrigem(String idOrigem) {
        this.idOrigem = idOrigem;
    }

    public boolean getResultado() {
        return resultado;
    }

    public void setResultado(boolean resultado) {
        this.resultado = resultado;
    }
}
