import org.ejml.simple.SimpleMatrix;

public class Main {
    public static void main(String[] args) {
        SimpleMatrix firstMatrix = new SimpleMatrix(
                new double[][] {
                        new double[] {1.2},
                        new double[] {2.2},
                        new double[] {1.2}
                }
        );

        SimpleMatrix secondMatrix = new SimpleMatrix(
                new double[][] {
                        new double[] {1d},
                        new double[] {5d},
                        new double[] {5d}
                }
        );


        System.out.println(firstMatrix.get(0,0));

        SimpleMatrix cacat = firstMatrix.plus(secondMatrix);

        System.out.println(cacat.get(0,0));


        //System.out.println(secondMatrix);
    }
}
