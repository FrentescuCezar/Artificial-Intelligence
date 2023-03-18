import java.util.List;
import java.util.Random;

public class WeightsAndBiasesGeneration {
    static void generateBias(int hiddenSize, List<Double> bias1) {
        for (int i = 0; i < hiddenSize; i++) {
            Random random = new Random();
            double randomValue = random.nextDouble(1 - (-1)) + (-1);
            randomValue = Double.parseDouble(Main.decimalFormatter.format(randomValue));

            bias1.add(randomValue);
        }
    }
    static void generateWeight(int neuronsColSize, List<Double> weight1) {
        generateBias(neuronsColSize * Main.hiddenSize, weight1);
    }
}
