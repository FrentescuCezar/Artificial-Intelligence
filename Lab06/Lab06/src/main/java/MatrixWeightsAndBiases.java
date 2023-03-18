import java.util.ArrayList;
import java.util.List;

public class MatrixWeightsAndBiases {

    static List<Double>[][] matrixGradientsWeightsOutput = new ArrayList[Main.weightsOutputMatrix.numRows()][Main.weightsOutputMatrix.numCols()];
    static List<Double> matrixGradientsBiasesOutput = new ArrayList(Main.outputSize);

    static List<Double>[][] matrixGradientsWeightsHidden = new ArrayList[Main.weightsHiddenMatrix.numRows()][Main.weightsHiddenMatrix.numCols()];
    static List<Double> matrixGradientsBiasesHidden = new ArrayList(Main.outputSize);

    static public void initializeWeightsOutputMatrix() {
        for (int i = 0; i < Main.weightsOutputMatrix.numRows(); i++) {
            for (int j = 0; j < Main.weightsOutputMatrix.numCols(); j++) {
                matrixGradientsWeightsOutput[i][j] = new ArrayList<>();
            }
        }
    }

    static public void initializeWeightsHiddenMatrix() {
        for (int i = 0; i < Main.weightsHiddenMatrix.numRows(); i++) {
            for (int j = 0; j < Main.weightsHiddenMatrix.numCols(); j++) {
                matrixGradientsWeightsHidden[i][j] = new ArrayList<>();
            }
        }
    }

}
