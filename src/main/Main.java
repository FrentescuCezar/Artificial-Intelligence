package main;

public class Main {
    enum ACTIONS {
        FIRST, SECOND, FILL_FIRST, FILL_SECOND, EMPTY_FIRST, EMPTY_SECOND, FIRST_TO_SECOND, SECOND_TO_FIRST
    }

    static boolean[][] visitedStates;
    static int[] capacities = new int[2];  // n and m
    static int k;


    public static int[] initialisation(int n, int m, int k) {
        visitedStates = new boolean[n + 1][m + 1];
        Main.capacities[0] = n;
        Main.capacities[1] = m;
        Main.k = k;
        return new int[]{0, 0};
    }


    public static void main(String[] args) {
        int[] state;

        state = initialisation(6, 4, 2);
        BFS bfs = new BFS(state);
        bfs.solve();

        state = initialisation(6, 4, 2);
        BKT bkt = new BKT(capacities, state, visitedStates);
        bkt.solve();
    }
}