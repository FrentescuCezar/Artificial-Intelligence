import org.ejml.simple.SimpleMatrix;
import org.javatuples.Pair;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.*;


public class Main {
    static DecimalFormat decimalFormatter = new DecimalFormat("#.#####");
    static List<String> typesOfFlowers = new ArrayList<>();
    static List<List<Integer>> flowersPositions = new ArrayList<>();
    static List<String> allDataInit = new ArrayList<>();
    static List<List<String>> allDataString = new ArrayList<>();
    static List<Pair<List<Double>, String>> allDataSeparated = new ArrayList<>();
    static List<Pair<List<Double>, List<Integer>>> allDataFinal = new ArrayList<>();
    static List<Pair<List<Double>, List<Integer>>> trainData = new ArrayList<>();
    static List<Pair<List<Double>, List<Integer>>> testData = new ArrayList<>();
    static int inputSize;
    static int hiddenSize;
    static int outputSize;
    static double learningRate;
    static int epochs;
    static List<Double> weight1 = new ArrayList<>();
    static List<Double> weight2 = new ArrayList<>();
    static List<Double> bias1 = new ArrayList<>();
    static List<Double> bias2 = new ArrayList<>();
    static SimpleMatrix inputMatrix;
    static SimpleMatrix hiddenMatrix;
    static SimpleMatrix outputMatrix;
    static SimpleMatrix biasHiddenMatrix;
    static SimpleMatrix biasOutputMatrix;
    static SimpleMatrix weightsHiddenMatrix;
    static SimpleMatrix weightsOutputMatrix;
    static SimpleMatrix flowerPositionsMatrix;
    static double squaredErrorsSum = 0;
    static double meanSquaredError = 0;

    public static void main(String[] args) throws IOException {
        DataStructuresGeneration.readFile();
        DataStructuresGeneration.addAllData();

        DataStructuresGeneration.separateData();

        DataStructuresGeneration.addFlowersPositions();

        DataStructuresGeneration.separateAllDataWithPositions();

        Collections.shuffle(allDataFinal);
        DataStructuresGeneration.assignTrainAndTestData();

        inputSize = allDataFinal.get(0).getValue0().size();
        outputSize = typesOfFlowers.size();
        hiddenSize = 4;
        learningRate = 0.2;
        epochs = 2000;

        WeightsAndBiasesGeneration.generateWeight(inputSize, weight1);
        WeightsAndBiasesGeneration.generateWeight(outputSize, weight2);


        WeightsAndBiasesGeneration.generateBias(hiddenSize, bias1);
        WeightsAndBiasesGeneration.generateBias(outputSize, bias2);

        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("    ALL DATA: ");
        System.out.println(allDataFinal);
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("    Types of flowers ");
        System.out.println(typesOfFlowers);
        System.out.println("    The flower positions are");
        System.out.println(flowersPositions);
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("    Train Data length");
        System.out.println(trainData.size());
        System.out.println("    Test Data length");
        System.out.println(testData.size());


        DataStructuresGeneration.initMatrixes();

        MatrixWeightsAndBiases.initializeWeightsOutputMatrix();
        MatrixWeightsAndBiases.initializeWeightsHiddenMatrix();

        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");


        for (int epoch = 1; epoch <= 200; epoch++) {
            ForwardAndBack.iterateHiddenAndOutput();
            ForwardAndBack.updateWeightsAndBiases();
            squaredErrorsSum = 0;
        }

        for (Pair<List<Double>, List<Integer>> pair : Main.testData) {

            List<Double> inputValues = pair.getValue0();
            List<Integer> outputPositions = pair.getValue1();
            for (int flowerPos = 0; flowerPos < outputPositions.size(); flowerPos++) {
                Main.flowerPositionsMatrix.set(flowerPos, 0, outputPositions.get(flowerPos));
            }

            ForwardAndBack.generateHiddenValues(inputValues);
            ForwardAndBack.generateOutputValues();

            if (outputPositions.get(0) == 1) {
                System.out.print(inputValues + " ");
                for (int i = 0; i < Main.outputSize; i++) {

                    System.out.print(Main.outputMatrix.get(i, 0) + " ");
                }
                System.out.println(outputPositions);
            }
        }
    }
}
