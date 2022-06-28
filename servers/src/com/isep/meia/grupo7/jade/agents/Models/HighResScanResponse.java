package com.isep.meia.grupo7.jade.agents.Models;

import java.util.Random;

public class HighResScanResponse extends Response {
    public final int x;
    public final int y;

    public HighResScanResponse(String originId, int x, int y) {
        super(originId);
        this.x = x;
        this.y = y;
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
