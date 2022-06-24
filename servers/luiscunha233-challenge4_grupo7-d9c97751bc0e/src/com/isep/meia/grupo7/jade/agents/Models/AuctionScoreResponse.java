package com.isep.meia.grupo7.jade.agents.Models;

import java.io.Serializable;
import java.util.Random;

public class RespostaDistancia implements Serializable {


    private String originId;
    private float result;

    public RespostaDistancia(String originId) {
        this.originId = originId;
        Random objGenerator = new Random();
        this.result = objGenerator.nextFloat();
    }


    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public float getResult() {
        return result;
    }

    public void setResult(float result) {
        this.result = result;
    }
}
