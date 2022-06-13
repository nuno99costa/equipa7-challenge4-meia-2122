package com.isep.meia.grupo7.jade.agents.Models;

import java.io.Serializable;

public class PedidoDeDistância implements Serializable {
    private String idOrigem;
    private int x;
    private int y;


    public PedidoDeDistância(int x, int y, String idOrigem) {
        this.idOrigem=idOrigem;
        this.x = x;
        this.y = y;

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

    public String getIdOrigem(){return idOrigem;}
}
