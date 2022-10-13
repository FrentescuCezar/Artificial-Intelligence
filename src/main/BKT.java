package main;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class BKT {

    private final int[] startingState;
    int[] capacities;
    boolean[][] visitedStates;
    private Deque<int[]> solution = new ArrayDeque<>();
    private int numberOfSolutions = 0;

    public BKT(int[] capacities, int[] state, boolean[][] visitedStates) {
        this.startingState = state;
        this.capacities = capacities.clone();
        this.visitedStates = visitedStates.clone();
    }

    public int getNumberOfSolutions() {
        return numberOfSolutions;
    }

    public void solve() {
        System.out.println("--------BKT------- ");
        solution = new ArrayDeque<>();
        bktAlgorithm(startingState);
        System.out.println("Total number of solutions: " + numberOfSolutions + '\n');

    }
    private void bktAlgorithm(int[] currentState) {
        int[] state = currentState.clone();

        solution.push(state);


        if (StateVerifier.isFinalState(state)) {
            numberOfSolutions++;
            solution.forEach(s -> System.out.print(Arrays.toString(s)));
            System.out.println();
            return;
        }
        if (state[0] == -999 || state[1] == -999) {
            return;
        }

        if (StateVerifier.isStateVisited(state, visitedStates)) {
            return;
        }

        StateManager.visitState(visitedStates, state);


        for (Main.ACTIONS ac : Main.ACTIONS.values()) {
            bktAlgorithm(Actions.doAction(state, capacities, ac));
            solution.removeFirst();
        }

    }
}
