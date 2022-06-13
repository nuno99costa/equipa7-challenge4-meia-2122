package com.isep.meia.grupo7.jade.agents.Models;

import java.io.Serializable;
import java.util.Random;

public class RespostaDistancia implements Serializable {


    private String idOrigem;
    private float resultado;

    public RespostaDistancia(String idOrigem) {
        this.idOrigem = idOrigem;
        Random objGenerator = new Random();
        this.resultado = objGenerator.nextFloat();
    }


    public String getIdOrigem() {
        return idOrigem;
    }

    public void setIdOrigem(String idOrigem) {
        this.idOrigem = idOrigem;
    }

    public float getResultado() {
        return resultado;
    }

    public void setResultado(float resultado) {
        this.resultado = resultado;
    }
}
