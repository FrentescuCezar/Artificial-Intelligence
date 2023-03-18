package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {
    int[] startingState;

    Queue<int[]> queue = new LinkedList<>();
    int numberOfSolutions = 0;

    public BFS(int[] startingState) {
        this.startingState = startingState;
    }

    public void solve() {
        System.out.println("--------BFS------- ");
        bfsAlgorithm(startingState);
        System.out.println("Total number of solutions: " + numberOfSolutions + '\n');
    }

    private void bfsAlgorithm(int[] startingState) {
        queue.add(startingState);

        int[] state;
        int[] newState;

        HashMap<int[], int[]> stateLink= new HashMap<>();
        stateLink.put(startingState, startingState.clone());

        while (!queue.isEmpty()) {
            state = queue.poll();

            if(StateVerifier.isFinalState(state)) {
                int[] prevState = state;
                do {
                    System.out.print(Arrays.toString(prevState));
                    prevState = stateLink.get(prevState);
                } while(stateLink.get(prevState) != null);

                System.out.println();
                numberOfSolutions++;
                continue;
            }

            StateManager.visitState(Main.visitedStates, state);

            for (Main.ACTIONS ac : Main.ACTIONS.values()) {
                newState = Actions.doAction(state, Main.capacities, ac);
                if(StateVerifier.isValidState(newState) && !StateVerifier.isStateVisited(newState, Main.visitedStates)) {
                    stateLink.put(newState, state); // Key, Value
                    queue.add(newState);
                }
            }
        }



    }
}
