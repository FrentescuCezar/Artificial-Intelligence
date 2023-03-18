import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    static List<String> players = new ArrayList<>();

    static List<String> actionsPlayerA = new ArrayList<>();

    static List<String> actionsPlayerB = new ArrayList<>();
    static List<String> costs = new ArrayList<>();
    static int[] playerACosts;
    static List<List<Integer>> playerACostsList;
    static int[] playerBCosts;
    static List<List<Integer>> playerBCostsList;


    public static void main(String[] args) throws IOException {
        //File file = new File("input.txt");
        //File file = new File("input2.txt");
        //File file = new File("input3.txt");
        //File file = new File("input4.txt");
        File file = new File("input6.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        String[] data;
        int counter = 0;
        String currentPlayer = "A";

        while ((st = br.readLine()) != null) {
            if (counter < 2) {
                data = st.split(" ");
                players.add(data[0]);
                if (currentPlayer == "A") {
                    for (int i = 1; i < data.length; i++) {
                        actionsPlayerA.add(data[i]);
                    }
                    currentPlayer = "B";
                } else {
                    for (int i = 1; i < data.length; i++) {
                        actionsPlayerB.add(data[i]);
                    }
                }

            } else {
                data = st.split(" ");
                costs.addAll(Arrays.asList(data));
            }
            counter++;
        }


        System.out.println();
        System.out.println("The players are : " + players);
        System.out.println("Actions of PlayerA : " + actionsPlayerA);
        System.out.println("Actions of PlayerB : " + actionsPlayerB);
        System.out.println("Intersected Costs : " + costs);
        System.out.println();


        playerACosts = new int[costs.size()];
        playerBCosts = new int[costs.size()];

        for (int i = 0; i < costs.size(); i++) {
            String[] str = costs.get(i).split("/");
            playerACosts[i] = Integer.parseInt(str[0]);
            playerBCosts[i] = Integer.parseInt(str[1]);
        }


        playerACostsList = new ArrayList<>(actionsPlayerA.size());
        addPlayerCostsLists(playerACostsList, playerACosts, actionsPlayerA);

        playerBCostsList = new ArrayList<>(actionsPlayerB.size());
        addPlayerCostsLists(playerBCostsList, playerBCosts, actionsPlayerB);

        System.out.println("Player A costs: " +playerACostsList);
        System.out.println("Player B costs: " +playerBCostsList);
        System.out.println();
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~PURE NASH EQUILIBRUM~~~~~~~~~~~~~~~");


        NashEquilibrium.solve(actionsPlayerA, actionsPlayerB, playerACostsList, playerBCostsList);
        System.out.println();
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~DOMINANT STRATEGIES~~~~~~~~~~~~~~~");


        DominantStrategy.solve(actionsPlayerA, actionsPlayerB, playerACostsList, playerBCostsList);





    }

    private static void addPlayerCostsLists(List<List<Integer>> playerACostsList, int[] playerACosts, List<String> actionsPlayerA) {
        playerACostsList.add(new ArrayList<>());

        for (int i = 0, j = 0; i < playerACosts.length; i++) {
            if (i % actionsPlayerA.size() == 0 && i != 0) {
                playerACostsList.add(new ArrayList<>());
                j++;
            }
            playerACostsList.get(j).add(playerACosts[i]);
        }
    }
}
