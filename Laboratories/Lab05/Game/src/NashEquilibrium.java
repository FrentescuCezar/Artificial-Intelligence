import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class NashEquilibrium {


    static List<List<Integer>> perspectiveAMaxes = new ArrayList<>();
    static List<List<Integer>> perspectiveBMaxes = new ArrayList<>();
    static List<List<String>> pureNashEquilibrum = new ArrayList<>();


    static public void solve(List<String> actionsPlayerA, List<String> actionsPlayerB, List<List<Integer>> playerACostsList, List<List<Integer>> playerBCostsList) {

        int maxValue = 0;
        // A Perspective - > B choose
        allMaxPositionsPlayerAPerspective(playerACostsList, playerBCostsList, maxValue);

        allMaxPositionsPlayerBPerspective(playerACostsList, playerBCostsList);

        pureNashEquilibrum(perspectiveAMaxes, perspectiveBMaxes, actionsPlayerA,actionsPlayerB);

        System.out.println("PerspectiveAMaxes Positions : " + perspectiveAMaxes);
        System.out.println("PerspectiveBMaxes Positions: " + perspectiveBMaxes);
        if(pureNashEquilibrum.size() != 0){
            System.out.println("Pure Nash Equilibrums found at : " + pureNashEquilibrum);
        }
        else{
            System.out.println("There are not any Pure Nash Equilibrums ");
        }


    }

    private static void pureNashEquilibrum(List<List<Integer>> perspectiveA, List<List<Integer>> perspectiveB, List<String> actionsPlayerA, List<String> actionsPlayerB) {
        for(List<Integer> listA : perspectiveA){
            for(List<Integer> listB : perspectiveB){
                if(Objects.equals(listA.get(0), listB.get(1)) && Objects.equals(listA.get(1), listB.get(0))){
                    pureNashEquilibrum.add(Arrays.asList(actionsPlayerA.get(listA.get(0)), actionsPlayerB.get(listA.get(1))));
                }
            }
        }
    }

    private static void allMaxPositionsPlayerBPerspective(List<List<Integer>> playerACostsList, List<List<Integer>> playerBCostsList) {
        int maxValue;
        for (int rowA = 0; rowA < playerACostsList.size(); rowA++) {
            maxValue = playerBCostsList.get(rowA).get(0);
            for (int colB = 0; colB < playerBCostsList.size(); colB++) {
                int bVar = playerBCostsList.get(rowA).get(colB);
                if (playerBCostsList.get(rowA).get(colB) > maxValue) {
                    maxValue = playerBCostsList.get(rowA).get(colB);
                }
            }

            for (int colB = 0; colB < playerBCostsList.size(); colB++) {
                if (playerBCostsList.get(rowA).get(colB) == maxValue) {
                    perspectiveBMaxes.add(Arrays.asList(colB, rowA));
                }
            }
        }
    }

    private static void allMaxPositionsPlayerAPerspective(List<List<Integer>> playerACostsList, List<List<Integer>> playerBCostsList, int maxValue) {
        for (int colB = 0; colB < playerBCostsList.size(); colB++) {
            boolean firstTime = true;
            for (int rowA = 0; rowA < playerACostsList.size(); rowA++) {
                if (firstTime) {
                    maxValue = playerACostsList.get(rowA).get(colB);
                    firstTime = false;
                } else {
                    if (playerACostsList.get(rowA).get(colB) > maxValue) {
                        maxValue = playerACostsList.get(rowA).get(colB);
                    }
                }
            }
            for (int colA = 0; colA < playerACostsList.size(); colA++) {
                if (playerACostsList.get(colA).get(colB) == maxValue) {
                    perspectiveAMaxes.add(Arrays.asList(colA, colB));
                }
            }
        }
    }

}
