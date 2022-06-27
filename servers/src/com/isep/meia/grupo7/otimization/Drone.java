package com.isep.meia.grupo7.otimization;

public class Drone {
    private final int x;
    private final int y;
    private final int r;

    public Drone(int x, int y, int r)
    {
        this.x = x;
        this.y = y;
        this.r = r;
    }
    public double getArea(){return Math.PI * this.r * this.r;}
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public int getR() {
        return r;
    }

    @Override
    public String toString() {
        return "Drone{" +
                "x=" + x +
                ", y=" + y +
                ", r=" + r +
                '}';
    }
}
