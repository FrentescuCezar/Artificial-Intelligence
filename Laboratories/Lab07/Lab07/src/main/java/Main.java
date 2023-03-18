import org.javatuples.Pair;

public class Main {

    static double [][] Q_Table = new double[48][4];
    static double learning_rate;
    static double future;

    static Pair<Integer,Integer> state;

    public static void main(String[] args) {
        QTable qTable = new QTable(48, 4, new Pair<>(0,0));

        System.out.println(qTable);
    }

}
