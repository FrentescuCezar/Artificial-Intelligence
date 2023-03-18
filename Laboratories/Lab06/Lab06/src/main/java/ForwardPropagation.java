import org.javatuples.Pair;

import java.util.List;

public class ForwardPropagation {
    public static Double sigmoidFunction(Double hiddenValue) {
        return 1 / (1 + Math.exp(-hiddenValue));
    }

    public static void generateHiddenValues(List<Double> inputValues) {
        for (int i = 0; i < inputValues.size(); i++) {
            Main.inputMatrix.set(i, 0, inputValues.get(i));
        }
        for (int i = 0; i < Main.bias1.size(); i++) {
            Main.biasHiddenMatrix.set(i, 0, Main.bias1.get(i));
        }

        int col = 0;
        int row = 0;
        for (int i = 0; i < Main.weight1.size(); i++) {
            if (i % Main.hiddenSize == 0 && i != 0) {
                row = 0;
                col++;
            }
            Main.weightsHiddenMatrix.set(row, col, Main.weight1.get(i));
            row++;
        }

        Main.hiddenMatrix = Main.weightsHiddenMatrix.mult(Main.inputMatrix).plus(Main.biasHiddenMatrix);

        for (int i = 0; i < Main.hiddenMatrix.numCols(); i++) {
            for (int j = 0; j < Main.hiddenMatrix.numRows(); j++) {
                Double valueOfHiddenNode = Main.hiddenMatrix.get(j, i);
                Main.hiddenMatrix.set(j, i, sigmoidFunction(valueOfHiddenNode));
            }
        }
    }

    public static void generateOutputValues() {
        for (int i = 0; i < Main.bias2.size(); i++) {
            Main.biasOutputMatrix.set(i, 0, Main.bias2.get(i));
        }

        int col = 0;
        int row = 0;
        for (int i = 0; i < Main.weight2.size(); i++) {
            if (i % Main.outputSize == 0 && i != 0) {
                row = 0;
                col++;
            }
            Main.weightsOutputMatrix.set(row, col, Main.weight2.get(i));
            row++;
        }

        Main.outputMatrix = Main.weightsOutputMatrix.mult(Main.hiddenMatrix).plus(Main.biasOutputMatrix);

        for (int i = 0; i < Main.outputMatrix.numCols(); i++) {
            for (int j = 0; j < Main.outputMatrix.numRows(); j++) {
                Double valueOfOutputNode = Main.outputMatrix.get(j, i);
                Main.outputMatrix.set(j, i, sigmoidFunction(valueOfOutputNode));
            }
        }
    }

    public static void iterateHiddenAndOutput() {
        for (Pair<List<Double>, List<Integer>> pair : Main.trainData) {

            List<Double> inputValues = pair.getValue0();
            List<Integer> outputPositions = pair.getValue1();
            for (int flowerPos = 0; flowerPos < outputPositions.size(); flowerPos++) {
                Main.flowerPositionsMatrix.set(flowerPos, 0, outputPositions.get(flowerPos));
            }

            generateHiddenValues(inputValues);
            generateOutputValues();


            double errorOfIteration = 0.0;

            for (int row = 0; row < Main.outputMatrix.numRows(); row++) {
                double subtraction = Main.outputMatrix.get(row, 0) - Main.flowerPositionsMatrix.get(row, 0);
                errorOfIteration = errorOfIteration + Math.pow(subtraction, 2);
            }

            Main.squaredErrorsSum += errorOfIteration;
        }
    }
}
