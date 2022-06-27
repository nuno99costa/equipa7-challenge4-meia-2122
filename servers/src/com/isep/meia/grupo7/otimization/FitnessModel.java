package com.isep.meia.grupo7.otimization;

import io.jenetics.Genotype;

import java.util.ArrayList;
import java.util.function.Function;

public class FitnessModel {
    private final int h;
    private final int w;
    private final int[] dronesRange;

    public FitnessModel(int h, int w, int[] dronesRange){
        this.h = h;
        this.w = w;
        this.dronesRange = dronesRange;
    }

    // Estimation of overlapping circles' areas
    public static double overlap(Drone c1, Drone c2)
    {
        int x1 = c1.getX(), y1 = c1.getY(), r1 = c1.getR();
        int x2 = c2.getX(), y2 = c2.getY(), r2 = c2.getR();

        double d = Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
        if (d < r1 + r2){
            double a = r1 * r1;
            double b = r2 * r2;

            double x = (a - b + d * d) / (2 * d);

            double z = x * x;
            double y = Math.sqrt(a - z);

            if (d <= Math.abs(r2 - r1)) {
                return Math.PI * Math.min(a, b);
            }
            return a * Math.asin(y / r1) + b * Math.asin(y / r2) - y * (x + Math.sqrt(z + b - a));
        }
        return 0; // Two circles don't overlap
    }

    private static double areaOutside(Drone c)
    {
        return 0;
    }

    // There are goes our general fitness function
    public double scalarFitness(final Genotype model)
    {
        ArrayList<Drone> drones = ModelFactory.convert2drones(model, dronesRange);
        double penalty = 0;
        for (int i=0; i< drones.size(); i++){
            penalty += areaOutside(drones.get(i));
            for (int j=i+1; j< drones.size(); j++){
                penalty += overlap(drones.get(i), drones.get(j));
            }
        }
        return penalty;
    }

    public Function<Genotype, Double> getFitness()
    {
        return this::scalarFitness;
    }
}
