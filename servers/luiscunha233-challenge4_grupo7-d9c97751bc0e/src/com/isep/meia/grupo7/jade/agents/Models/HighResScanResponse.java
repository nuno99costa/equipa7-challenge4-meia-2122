package com.isep.meia.grupo7.jade.agents.Models;

import java.util.Random;

public class HighResScanResponse extends Response {

    public HighResScanResponse(String originId) {
        super(originId);
        Random objGenerator = new Random();
        this.result = objGenerator.nextBoolean();
    }

    public boolean getResult() {
        return (boolean) result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
