package nsu.fit.ezaitseva.snakegame.genetic.neural;

import org.ejml.simple.SimpleMatrix;

public class Layer {
    Neuron[] neurons;

    public SimpleMatrix output(SimpleMatrix inputMatrix) {
        SimpleMatrix output = SimpleMatrix.random_DDRM(neurons.length, 1);
        output.elementOp((row, col, value) -> {
            return neurons[row].activate(inputMatrix);
        });

        return output;
    }
}