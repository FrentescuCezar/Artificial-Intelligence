import java.util.ArrayList;
import java.util.Arrays;

public class HillClimbing {
    int[] startingState;
    int numberOfSolutions = 0;
    ArrayList<int[]> path = new ArrayList<>();

    public HillClimbing(int[] startingState) {
        this.startingState = startingState;
    }

    public void solve() {
        System.out.println("Solving with Hill Climbing: ");
        hillClimbing(startingState);
        if (numberOfSolutions != 0)
            for (int[] state : path) {
                System.out.print(Arrays.toString(state));
            }
    }

    private int getCost(int[] state) {
        return 1 - Math.min(Math.abs(Main.k - state[0]), Math.abs(Main.k - state[1]));
    }

    private ArrayList<int[]> getNeighbours(int[] currentState) {

        ArrayList<int[]> neighbourStates = new ArrayList<>();

        for (Main.ACTIONS action : Main.ACTIONS.values()) {
            if (action == Main.ACTIONS.FIRST || action == Main.ACTIONS.SECOND)
                continue;
            neighbourStates.add(ActionManager.doAction(currentState, Main.capacities, action));
        }

        return neighbourStates;
    }

    private ArrayList<int[]> sortByCost(ArrayList<int[]> neighbourStates) {
        for (int i = 0; i < neighbourStates.size(); ++i) {
            for (int j = i + 1; j < neighbourStates.size(); ++j) {

                int x = getCost(neighbourStates.get(i));
                int y = getCost(neighbourStates.get(j));

                if (x < y) {
                    int[] aux = neighbourStates.get(i);
                    neighbourStates.set(i, neighbourStates.get(j));
                    neighbourStates.set(j, aux);
                }
            }
        }
        return neighbourStates;
    }

    private void hillClimbing(int[] startingState) {
        int[] currentState = startingState.clone();

        if (StateVerifier.isFinalState(currentState)) {
            return;
        }

        while (true) {
            ArrayList<int[]> neighbourStates = getNeighbours(currentState);
            neighbourStates = sortByCost(neighbourStates);

            path.add(currentState);

            if (StateVerifier.isFinalState(currentState)) {
                numberOfSolutions++;
                break;
            }

            for (int[] neighbour : neighbourStates) {
                if (neighbour[0] == -1 || neighbour[1] == -1) {
                    return;
                }

                if (!StateVerifier.isStateVisited(neighbour, Main.visitedStates)) {
                    StateManager.visitState(Main.visitedStates, neighbour);
                    currentState = neighbour;
                    break;
                }
            }

        }
    }
}
