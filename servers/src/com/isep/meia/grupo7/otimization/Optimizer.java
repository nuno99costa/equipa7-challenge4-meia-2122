package com.isep.meia.grupo7.otimization;

import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.util.Factory;

import javax.swing.*;

import java.util.ArrayList;

import static io.jenetics.engine.EvolutionResult.toBestPhenotype;
import static io.jenetics.engine.Limits.bySteadyFitness;

public class Optimizer {

    public static ArrayList<Drone> run(int width, int height, int[] dronesRange)
    {
        int nDrones = dronesRange.length;
        String[] droneNames = new String[nDrones];
        for (int i = 0; i < nDrones; i++) {
            droneNames[i] = String.format("#%d", i+1);
        }

        FitnessModel fm = new FitnessModel(width, height, dronesRange);
        Factory<Genotype<IntegerGene>> model = ModelFactory.of(width, height, nDrones);

        // Creation of genetic evolution engine
        Engine<IntegerGene, Double> engine = Engine.builder(fm.getFitness(), model)
                .populationSize(40)
                .optimize(Optimize.MINIMUM)
                .alterers(
                        new Mutator<>(.1),
                        new MeanAlterer<>(.5)
                )
                .build();
        // Gather some stats about evolution
        final EvolutionStatistics<Double, ?> statistics = EvolutionStatistics.ofNumber();
        final Phenotype<IntegerGene, Double> best = engine.stream()
                .limit(bySteadyFitness(100))
                .peek(statistics)
                .collect(toBestPhenotype());

        ArrayList<Drone> drones = ModelFactory.convert2drones(best.genotype(), dronesRange);

        // Visualization - NOT NEEDED
            int border = 50;
            System.out.println(statistics);
            System.out.println(best);

            JFrame f = new JFrame("Drone plan");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.getContentPane().add(new FPViewer(drones, droneNames, width, height, border));
            f.setSize(width+border*2,height+border*2);
            f.setLocation((width+border*2)/2,(height+border*2)/2);
            f.setLocationByPlatform(true);
            f.setVisible(true);

        return drones;
    }
}
