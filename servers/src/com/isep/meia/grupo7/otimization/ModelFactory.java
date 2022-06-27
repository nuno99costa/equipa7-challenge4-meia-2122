package com.isep.meia.grupo7.otimization;

import io.jenetics.Genotype;
import io.jenetics.IntegerChromosome;
import io.jenetics.IntegerGene;
import io.jenetics.util.Factory;

import java.util.ArrayList;
import java.util.List;

public class ModelFactory {
    public static Factory<Genotype<IntegerGene>> of (int h, int w, int nDrones)
    {
        List<IntegerChromosome> arr = new ArrayList<>();
        for(int i=0; i<nDrones*2; i+=2)
        {
            arr.add(IntegerChromosome.of(0, h));
            arr.add(IntegerChromosome.of(0, w));
        }
        return Genotype.of(arr);
    }

    public static ArrayList<Drone> convert2drones(Genotype<IntegerGene> solution, int[] dronesRange)
    {
        ArrayList<Drone> circles = new ArrayList<Drone>();
        int droneIndx = 0;
        for(int i=0; i < solution.length(); i+= 2)
        {
            circles.add(new Drone(
                    solution.get(i).gene().allele(), // X0
                    solution.get(i+1).gene().allele(),// y0
                    dronesRange[droneIndx]
            ));
            droneIndx++;
        }
        return circles;
    }

}
