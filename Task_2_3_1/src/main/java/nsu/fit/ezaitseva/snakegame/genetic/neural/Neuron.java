package nsu.fit.ezaitseva.snakegame.genetic.neural;


import org.ejml.simple.SimpleMatrix;

import java.util.function.Function;

public class Neuron {
    SimpleMatrix inputWeights;
    Function<Double, Double> activationFunction;

    public double activate(SimpleMatrix inputs) {

        return activationFunction.apply(inputs.dot(inputWeights.transpose()));
    }

}