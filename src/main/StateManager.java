package main;

public class StateManager {
    public static void visitState(boolean[][] visitedStates, int[] state) {
        visitedStates[state[0]][state[1]] = true;
    }
}
