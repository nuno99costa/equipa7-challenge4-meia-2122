package com.isep.meia.grupo7.jade.agents.Models;

public class Request {

    protected String originId;
    protected int x;
    protected int y;

    public Request(int x, int y, String originId) {
        this.x = x;
        this.y = y;
        this.originId = originId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getOriginId(){return originId;}
}
