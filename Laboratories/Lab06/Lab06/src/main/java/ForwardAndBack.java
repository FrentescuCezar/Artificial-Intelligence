import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class ForwardAndBack {
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
                row++;
                col = 0;
            }
            Main.weightsHiddenMatrix.set(row, col, Main.weight1.get(i));
            col++;
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
            if (i % (Main.outputSize+1) == 0 && i != 0) {
                row++;
                col = 0;
            }

            Main.weightsOutputMatrix.set(row, col, Main.weight2.get(i));
            col++;
        }

        Main.outputMatrix = Main.weightsOutputMatrix.mult(Main.hiddenMatrix).plus(Main.biasOutputMatrix);
        //System.out.println(Main.outputMatrix);

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




            //BACK PROPAGATION
            //1. Compute the errors for "FINAL layer"
            double errorNodeFinalLayer = 0;
            List<Double> listOfErrorOutputNodes = new ArrayList<>();
            for (int row = 0; row < Main.outputMatrix.numRows(); row++) {
                double y_i_L = Main.outputMatrix.get(row, 0);
                errorNodeFinalLayer = y_i_L * (1 - y_i_L) * (y_i_L - Main.flowerPositionsMatrix.get(row, 0));
                listOfErrorOutputNodes.add(errorNodeFinalLayer);
            }

            //1. Compute the errors for "PREVIOUS layer"
            double errorNodeHiddenLayer = 0;
            List<Double> listOfErrorHiddenNodes = new ArrayList<>();

            for (int row = 0; row < Main.hiddenMatrix.numRows(); row++) {
                double y_i_L = Main.hiddenMatrix.get(row, 0);
                double sumOfErrorsAndWeights = 0;

                for (int errorIndex = 0; errorIndex < listOfErrorOutputNodes.size(); errorIndex++) {
                    sumOfErrorsAndWeights += listOfErrorOutputNodes.get(errorIndex) * Main.weightsOutputMatrix.get(errorIndex, row);
                }

                errorNodeHiddenLayer = y_i_L * (1 - y_i_L) * sumOfErrorsAndWeights;
                listOfErrorHiddenNodes.add(errorNodeHiddenLayer);
            }


            //2.Compute the gradient of WEIGHTS OUTPUT

            for (int i = 0; i < listOfErrorOutputNodes.size(); i++) {
                for (int j = 0; j < Main.hiddenMatrix.numRows(); j++) {
                    double gradient = listOfErrorOutputNodes.get(i) * Main.hiddenMatrix.get(j, 0);
                    MatrixWeightsAndBiases.matrixGradientsWeightsOutput[i][j].add(gradient);
                    MatrixWeightsAndBiases.matrixGradientsBiasesOutput.add(listOfErrorOutputNodes.get(i));
                }
            }


            //2.Compute the gradient of WEIGHTS HIDDEN

            for (int i = 0; i < listOfErrorHiddenNodes.size(); i++) {
                for (int j = 0; j < Main.inputMatrix.numRows(); j++) {
                    double gradient = listOfErrorHiddenNodes.get(i) * Main.inputMatrix.get(j, 0);
                    MatrixWeightsAndBiases.matrixGradientsWeightsHidden[i][j].add(gradient);
                    MatrixWeightsAndBiases.matrixGradientsBiasesHidden.add(listOfErrorHiddenNodes.get(i));
                }
            }
        }
    }


    public static void updateWeightsAndBiases() {
        // WEIGHTS FOR OUTPUT
        int weightOutputIndex = 0;
        for (int i = 0; i < Main.weightsOutputMatrix.numRows(); i++) {
            for (int j = 0; j < Main.weightsOutputMatrix.numCols(); j++) {
                double weight = Main.weightsOutputMatrix.get(i, j);

                double sumOfWeightsGradientsOutput = 0;
                for (int i2 = 0; i2 < MatrixWeightsAndBiases.matrixGradientsWeightsOutput.length; i2++) {
                    for (int j2 = 0; j2 < MatrixWeightsAndBiases.matrixGradientsWeightsOutput[i2].length; j2++) {
                        for(int k2=0;k2<MatrixWeightsAndBiases.matrixGradientsWeightsOutput[i][j].size(); k2++){
                            sumOfWeightsGradientsOutput += MatrixWeightsAndBiases.matrixGradientsWeightsOutput[i][j].get(k2);
                        }
                    }
                }
                double result = weight - (Main.learningRate / Main.trainData.size()) * sumOfWeightsGradientsOutput;

                Main.weight2.set(weightOutputIndex,result);
                weightOutputIndex++;

                //System.out.println(Main.weightsOutputMatrix.get(0,0));
            }
        }
        //BIASES FOR OUTPUT
        for (int i = 0; i < Main.weightsOutputMatrix.numRows(); i++) {
                double bias = Main.biasOutputMatrix.get(i, 0);

                double sumOfBiasesGradientsOutput = 0;
                for(int i2=0; i2< MatrixWeightsAndBiases.matrixGradientsBiasesOutput.size(); i2++){
                    sumOfBiasesGradientsOutput += MatrixWeightsAndBiases.matrixGradientsBiasesOutput.get(i2);
                }

                double result = bias - (Main.learningRate / Main.trainData.size()) * sumOfBiasesGradientsOutput;

                Main.bias2.set(i,result);
        }









        // WEIGHTS FOR HIDDEN
        int weightHiddenIndex = 0;
        for (int i = 0; i < Main.weightsHiddenMatrix.numRows(); i++) {
            for (int j = 0; j < Main.weightsHiddenMatrix.numCols(); j++) {
                double weight = Main.weightsHiddenMatrix.get(i, j);

                double sumOfWeightsGradientsHidden = 0;
                for (int i2 = 0; i2 < MatrixWeightsAndBiases.matrixGradientsWeightsHidden.length; i2++) {
                    for (int j2 = 0; j2 < MatrixWeightsAndBiases.matrixGradientsWeightsHidden[i2].length; j2++) {
                        for(int k2=0;k2<MatrixWeightsAndBiases.matrixGradientsWeightsHidden[i][j].size(); k2++){
                            sumOfWeightsGradientsHidden += MatrixWeightsAndBiases.matrixGradientsWeightsHidden[i][j].get(k2);
                        }
                    }
                }
                double result = weight - (Main.learningRate / Main.trainData.size()) * sumOfWeightsGradientsHidden;

                Main.weight1.set(weightHiddenIndex,result);
                weightHiddenIndex++;
            }
        }
        //BIASES FOR HIDDEN
        for (int i = 0; i < Main.weightsHiddenMatrix.numRows(); i++) {
            double bias = Main.biasHiddenMatrix.get(i, 0);

            double sumOfBiasesGradientsHidden = 0;
            for(int i2=0; i2< MatrixWeightsAndBiases.matrixGradientsBiasesHidden.size(); i2++){
                sumOfBiasesGradientsHidden += MatrixWeightsAndBiases.matrixGradientsBiasesHidden.get(i2);
            }

            double result = bias - (Main.learningRate / Main.trainData.size()) * sumOfBiasesGradientsHidden;
            Main.bias1.set(i,result);
        }



    }
}
