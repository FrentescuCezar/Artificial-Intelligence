import org.ejml.simple.SimpleMatrix;
import org.javatuples.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataStructuresGeneration {
    public static void readFile() throws IOException {
        File file = new File(
                "E:\\NEW FRESH\\Facultate\\Anul 3\\Semestrul 1\\Inteligenta Artificiala\\GIT\\Inteligenta-Artificiala\\Lab06\\Lab06\\src\\main\\java\\iris.data");
        BufferedReader buffer
                = new BufferedReader(new FileReader(file));
        String allDataLine;
        while ((allDataLine = buffer.readLine()) != null)
            if (!allDataLine.equals(""))
                Main.allDataInit.add(allDataLine);
    }

    public static void addAllData() {
        for (String s : Main.allDataInit) {
            String[] str = s.split(",");
            Main.allDataString.add(List.of(str));
        }
    }

    public static String addTypeOfFlower(List<String> line) {
        String flower = line.get(line.size() - 1);
        if (!Main.typesOfFlowers.contains(flower) && !flower.equals("")) {
            Main.typesOfFlowers.add(flower);
        }
        return flower;
    }
    public static void separateData() {
        for (List<String> line : Main.allDataString) {
            List<Double> auxList = new ArrayList<>();
            for (int i = 0; i < line.size() - 1; i++) {
                auxList.add(Double.parseDouble(line.get(i)));
            }
            String flower = addTypeOfFlower(line);
            Pair<List<Double>, String> auxPair = new Pair<>(auxList, flower);
            Main.allDataSeparated.add(auxPair);
        }
    }

    public static void addFlowersPositions() {
        for (int i = 0; i < Main.typesOfFlowers.size(); i++) {
            List<Integer> auxList = new ArrayList<>();
            for (String flower : Main.typesOfFlowers) {
                if (flower.equals(Main.typesOfFlowers.get(i))) {
                    auxList.add(1);
                } else {
                    auxList.add(0);
                }
            }
            Main.flowersPositions.add(auxList);
        }
    }

    public static void separateAllDataWithPositions() {
        for (Pair<List<Double>, String> line : Main.allDataSeparated) {
            String flowerName = (String) line.getValue(1);
            int indexOfFlower = Main.typesOfFlowers.indexOf(flowerName);
            //line.get = flowersPositions.get(indexOfFlower);
            Pair<List<Double>, List<Integer>> auxPair = new Pair(line.getValue0(), Main.flowersPositions.get(indexOfFlower));
            Main.allDataFinal.add(auxPair);
        }
    }

    public static void assignTrainAndTestData() {
        for (Pair<List<Double>, List<Integer>> line : Main.allDataFinal) {
            Random random = new Random();
            int randomValue = random.nextInt(100 - 1) + 1;

            if (randomValue <= 70) {
                Main.trainData.add(line);
            } else {
                Main.testData.add(line);
            }
        }
    }

    public static void initMatrixes() {
        Main.inputMatrix = new SimpleMatrix(
                new double[][]{
                        new double[]{-1},
                        new double[]{-1},
                        new double[]{-1},
                        new double[]{-1},
                }
        );


        Main.hiddenMatrix = new SimpleMatrix(
                new double[][]{
                        new double[]{-1},
                        new double[]{-1},
                        new double[]{-1},
                        new double[]{-1},
                }
        );

        Main.outputMatrix = new SimpleMatrix(
                new double[][]{
                        new double[]{-1},
                        new double[]{-1},
                        new double[]{-1},
                }
        );

        Main.biasHiddenMatrix = new SimpleMatrix(
                new double[][]{
                        new double[]{-1},
                        new double[]{-1},
                        new double[]{-1},
                        new double[]{-1},
                }
        );

        Main.biasOutputMatrix = new SimpleMatrix(
                new double[][]{
                        new double[]{-1},
                        new double[]{-1},
                        new double[]{-1},
                }
        );
        Main.weightsHiddenMatrix = new SimpleMatrix(
                new double[][]{
                        new double[]{-1, -1, -1, -1},
                        new double[]{-1, -1, -1, -1},
                        new double[]{-1, -1, -1, -1},
                        new double[]{-1, -1, -1, -1},
                }
        );
        Main.weightsOutputMatrix = new SimpleMatrix(
                new double[][]{
                        new double[]{-1, -1, -1, -1},
                        new double[]{-1, -1, -1, -1},
                        new double[]{-1, -1, -1, -1},
                }
        );

        Main.flowerPositionsMatrix = new SimpleMatrix(
                new double[][]{
                        new double[]{-1},
                        new double[]{-1},
                        new double[]{-1},
                }
        );
    }
}
