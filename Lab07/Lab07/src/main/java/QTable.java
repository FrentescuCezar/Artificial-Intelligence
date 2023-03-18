import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class QTable {

    // o variabila current position
    // O pereche (stare) -> [rewards..]
    Pair<Integer, Integer> currentPosition;
    Pair<Pair<Integer, Integer>, List<Integer>> stateLine;
    List<Pair<Pair<Integer, Integer>, List<Integer>>> qTable;

    @Override
    public String toString() {
        return "QTable{" +
                "currentPosition=" + currentPosition +
                ", qTable=" + qTable +
                '}';
    }

    QTable(int numberOfStates, int numberOfActons, Pair<Integer, Integer> currentPosition) {
        this.currentPosition = currentPosition;
        this.qTable = new ArrayList<>(numberOfStates);

        Pair<Integer, Integer> auxPair = new Pair<>(0,0);
        List<Integer> auxList = new ArrayList<>();
        this.stateLine = new Pair<>(auxPair,auxList);

        for (int i = 0; i < numberOfStates; i++) {
            List<Integer> listOfAction = new ArrayList<>();
            for (int action = 0; action < numberOfActons; action++) {
                listOfAction.add(0);
            }
           stateLine = stateLine.setAt1(listOfAction);
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 12; j++){
                stateLine = stateLine.setAt0(new Pair<>(i, j));
                qTable.add(stateLine);
            }
        }
    }
}
