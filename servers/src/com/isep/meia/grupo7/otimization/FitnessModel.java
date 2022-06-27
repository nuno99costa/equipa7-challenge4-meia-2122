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
    private static double overlap(Drone d0, Drone d1)
    {
        int x0 = d0.getX(), y0 = d0.getY(), r0 = d0.getR();
        int x1 = d1.getX(), y1 = d1.getY(), r1 = d1.getR();
        double rr0 = r0 * r0;
        double rr1 = r1 * r1;
        double d = Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0));

        // Circles do not overlap
        if (d > r1 + r0)
        {
            return 0;
        }

        // Circle1 is completely inside circle0
        else if (d <= Math.abs(r0 - r1) && r0 >= r1)
        {
            // Return area of circle1
            return Math.PI * rr1;
        }

        // Circle0 is completely inside circle1
        else if (d <= Math.abs(r0 - r1) && r0 < r1)
        {
            // Return area of circle0
            return Math.PI * rr0;
        }

        // Circles partially overlap
        else
        {
            double phi = (Math.acos((rr0 + (d * d) - rr1) / (2 * r0 * d))) * 2;
            double theta = (Math.acos((rr1 + (d * d) - rr0) / (2 * r1 * d))) * 2;
            double area1 = 0.5 * theta * rr1 - 0.5 * rr1 * Math.sin(theta);
            double area2 = 0.5 * phi * rr0 - 0.5 * rr0 * Math.sin(phi);

            // Return area of intersection
            return area1 + area2;
        }
    }

    private double areaOutside(Drone drone)
    {
        int xleft = 0, xright = w, ytop = h, ybottom = 0;
        int x = drone.getX(), y = drone.getY(), r = drone.getR();

        double[] d = {0,0,0,0};
        d[0] = (x-xleft)/r;
        d[1] = (ytop-y)/r;
        d[2] = (xright-x)/r;
        d[3] = (y-ybottom)/r;

        double area = 0;

        boolean f = true;
        for (double di:d) {
            if(di<1) {
                f = false;
                break;
            }
        }
        if(f)
            return area;

        double[] a = {0,0,0,0};
        for (int i = 0; i < 4; i++){
            if(d[i] < 1){
                a[i] = Math.abs(Math.asin(d[i]));
                double v = Math.PI - 2 * a[i] - Math.sin(2 * a[i]);
                area += r * r / 2 * v;
            }
        }

        for (int i = 0; i < 4; i++){
            int adj = (i+1) % 4;
            if(d[i] < 1 && d[adj] < 1 && d[i]*d[i] + d[adj] *d[adj] < 1){
                area -= r * r / 4 * (Math.PI - 2 * a[i] - 2 * a[adj] - Math.sin(2*a[i]) - Math.sin(2*a[adj]) + 4 * Math.sin(a[i]) * Math.sin(a[adj]));
            }
        }
        return area;
    }

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
