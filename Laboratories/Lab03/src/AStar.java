import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class AStar {
    int[] startingState;
    int depth = 0;
    HashMap<int[], int[]> stateLink = new HashMap<>();

    boolean firstIteration = false;

    public AStar(int[] startingState) {
        this.startingState = startingState;
        this.depth = 0;

    }

    public void solve() {
        System.out.println("Solving with A*: ");
        astar(startingState);

    }

    private int getHeuristic(int[] state) {
        return 1 - Math.min(Math.abs(Main.k - state[0]), Math.abs(Main.k - state[1]));
    }

    private ArrayList<int[]> getNeighbours(int[] currentState) {
        ArrayList<int[]> neighbourStates = new ArrayList<>();

        for (Main.ACTIONS action : Main.ACTIONS.values()) {
            int[] auxState = ActionManager.doAction(currentState, Main.capacities, action);
            if (StateVerifier.isValidState(auxState))
                if (!StateVerifier.isStateVisited(auxState, Main.visitedStates)) {
                    StateManager.visitState(Main.visitedStates, auxState);
                    neighbourStates.add(auxState);
                    stateLink.put(auxState, currentState);
                }
        }
        return neighbourStates;
    }

    private void getNeighboursDepth(ArrayList<int[]> neighbours, int[] currentState) {

        for (int[] state : neighbours) {
            int indexCurrentState = Main.visitedList.lastIndexOf(currentState);

            for (int[] visited : Main.visitedList) {
                if (visited[0] == currentState[0] && visited[1] == currentState[1])
                    indexCurrentState = Main.visitedList.indexOf(visited);
            }


            int depth = Main.aStarDepthList.get(indexCurrentState) + 1;
            Main.aStarDepthList.add(depth); // Am introdus in vectorul Main.aStarDepthList adancimea nodurilor curente

        }


    }

    private void astar(int[] startingState) {

        int[] currentState = startingState.clone();


        stateLink.put(startingState, startingState.clone());


        while (true) {

            if (StateVerifier.isFinalState(currentState)) {
                int[] prevState = currentState;
                do {
                    System.out.print(Arrays.toString(prevState));
                    prevState = stateLink.get(prevState);
                } while (stateLink.get(prevState) != null);

                System.out.println("[0, 0]");
                break;
            }
            ArrayList<int[]> neighbourStates = getNeighbours(currentState);
            Main.exploredStates[currentState[0]][currentState[1]] = true;
            Main.visitedList.addAll(neighbourStates);

            int min = Integer.MAX_VALUE;
            int statePosition = -1;

            if (firstIteration) {
                for (int[] state : neighbourStates)
                    Main.aStarDepthList.add(1);
                stateLink.put(new int[]{0, 0}, new int[]{0, 0});
            } else {
                getNeighboursDepth(neighbourStates, currentState);

                for (int[] state : Main.visitedList) {
                    if (Main.exploredStates[state[0]][state[1]] == true)
                        continue;

                    int heuristic = getHeuristic(state);
                    int position = Main.visitedList.indexOf(state);
                    int depth = Main.aStarDepthList.get(position);
                    int fScore = heuristic + depth;

                    if (fScore <= min) {
                        statePosition = position;
                        min = fScore;
                    }
                }
            }
            try {
                currentState = Main.visitedList.get(statePosition);
            } catch (IndexOutOfBoundsException a) {
                System.out.println("\n Nu exista nicio solutie");
                break;
            }
        }
    }
}
