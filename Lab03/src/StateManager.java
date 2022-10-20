public class StateManager {
    public static void visitState(boolean[][] visitedStates, int[] state) {
        visitedStates[state[0]][state[1]] = true;
    }

    public static void exploredState(boolean[][] exploredStates, int[] state) {
        exploredStates[state[0]][state[1]] = true;
    }


}
