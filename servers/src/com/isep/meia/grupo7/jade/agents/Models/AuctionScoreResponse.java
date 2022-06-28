package com.isep.meia.grupo7.jade.agents.Models;

import java.util.Random;

public class AuctionScoreResponse extends Response {
    public int x ;
    public int y;
    public int battery;
    public double result;
    //Should this response include original information on localization?
    public AuctionScoreResponse(String originId,int x ,int y) {
        super(originId);
        Random objGenerator = new Random();
        float max=600;
        float min=0;
        this.x=x;
        this.y=y;
        float actualX=objGenerator.nextFloat()*(max-min)+min;
        float actualY=objGenerator.nextFloat()*(max-min)+min;

        this.result = Math.sqrt((actualX-x)*(actualX-x)+(actualY-y)*(actualY-y));
        this.battery= objGenerator.nextInt(100);
    }

    public double getResult() {
        return result;
    }

    public void setResult(float result) {
        this.result = result;
    }
}
