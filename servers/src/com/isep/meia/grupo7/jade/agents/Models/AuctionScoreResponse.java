package com.isep.meia.grupo7.jade.agents.Models;

import java.util.Random;

public class AuctionScoreResponse extends Response {

    public AuctionScoreResponse(String originId) {
        super(originId);
        Random objGenerator = new Random();
        this.result = objGenerator.nextFloat();
    }

    public float getResult() {
        return (float) result;
    }

    public void setResult(float result) {
        this.result = result;
    }
}
