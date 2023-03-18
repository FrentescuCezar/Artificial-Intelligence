import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DominantStrategy {
    static boolean found = false;
    static List<List<Boolean>> localBMaxes = new ArrayList<>();
    static List<List<Boolean>> localAMaxes = new ArrayList<>();


    static public String getDominantStrategy(List<List<Boolean>> localMaxes, List<String> actionsPlayer) {

        boolean good = true;
        for (int i = 0; i < localMaxes.size(); i++) {
            good = true;
            if (localMaxes.get(0).get(i)) {
                for (int j = 0; j < localMaxes.size() && good; j++) {
                    if (!localMaxes.get(j).get(i) && j != 0) {
                        good = false;
                    }
                }
                if (good) {
                    return actionsPlayer.get(i);    // DACA VREM MAI MULTE STRATEGII DOMINANTE, ELIMINAM RETURN UL DE LA FUNCTIE, DAM DOAR PRINT LA TOATE ACTIUNILE
                }

            }
        }
        return "None"; // DACA VREM MAI MULTE STRATEGII DOMINANTE, ELIMINAM RETURN UL DE LA FUNCTIE

    }


    static public void solve(List<String> actionsPlayerA, List<String> actionsPlayerB, List<List<Integer>> playerACostsList, List<List<Integer>> playerBCostsList) {

        generateAllPlayerBMaxes(playerBCostsList);
        generateAllPlayerAMaxes(playerACostsList);

        System.out.println("Player A Maxes(True) : " + localAMaxes);
        System.out.println("Player B Maxes(True) : " + localBMaxes);


        String playerAStrategy = getDominantStrategy(localAMaxes, actionsPlayerA);
        String playerBStrategy = getDominantStrategy(localBMaxes, actionsPlayerB);


        System.out.println("The Dominant Strategy for player B is : " + playerBStrategy);
        if (Objects.equals(playerBStrategy, "None"))  {
            System.out.println("The Dominant strategy for player A is : " + playerAStrategy);
        }


        //getDominantStrategy(localAMaxes, actionsPlayerA);
        //getDominantStrategy(localBMaxes, actionsPlayerB);

        // AICI SUNT 2 FUNCTII COMENTATE IN CAZ DE TREBUIE SA ARATAM CA SUNT MAI MULTE STRATEGII DOMINANTE
    }

    private static void generateAllPlayerAMaxes(List<List<Integer>> playerACostsList) {
        for (int i = 0; i < playerACostsList.size(); i++) {
            int localMax = playerACostsList.get(i).get(i);
            localAMaxes.add(new ArrayList<>());
            for (int j = 0; j < playerACostsList.size(); j++) {
                if (playerACostsList.get(j).get(i) >= localMax) {
                    localMax = playerACostsList.get(j).get(i);
                }
            }

            for (int j = 0; j < playerACostsList.size(); j++) {
                if (playerACostsList.get(j).get(i) == localMax) {
                    localAMaxes.get(i).add(true);
                } else {
                    localAMaxes.get(i).add(false);
                }
            }
        }
    }

    private static void generateAllPlayerBMaxes(List<List<Integer>> playerBCostsList) {
        for (int i = 0; i < playerBCostsList.size(); i++) {
            int localMax = playerBCostsList.get(i).get(0);
            localBMaxes.add(new ArrayList<>());
            for (int j = 0; j < playerBCostsList.get(i).size(); j++) {
                if (playerBCostsList.get(i).get(j) >= localMax) {
                    localMax = playerBCostsList.get(i).get(j);
                }
            }
            for (int j = 0; j < playerBCostsList.get(i).size(); j++) {
                if (playerBCostsList.get(i).get(j) == localMax) {
                    localBMaxes.get(i).add(true);
                } else {
                    localBMaxes.get(i).add(false);
                }
            }
        }
    }
}
