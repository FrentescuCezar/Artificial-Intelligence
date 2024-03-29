import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    enum ACTIONS {
        FIRST, SECOND, FILL_FIRST, FILL_SECOND, EMPTY_FIRST, EMPTY_SECOND, FIRST_TO_SECOND, SECOND_TO_FIRST
    }

    static int k;
    static int[] capacities = new int[2];
    static boolean[][] visitedStates;
    static boolean[][] exploredStates;
    static List<int[]> visitedList;

    static List<Integer> aStarDepthList;
   // static List<int[]> aStarFList; // In aceasta lista pastram pe poz 0 -> F iar pe poz 1 -> pozitia starii in visitedList

    public static int[] init(int n, int m, int k) {
        visitedStates = new boolean[n + 1][m + 1];
        exploredStates = new boolean[n + 1][m + 1];

        visitedList = new ArrayList<>();
        aStarDepthList = new ArrayList<>();
        aStarDepthList.add(0);
        // aStarFList = new ArrayList<>();

        Main.capacities[0] = n;
        Main.capacities[1] = m;
        Main.k = k;
        return new int[]{0, 0};
    }

    public static void executeOption(int option, int n, int m, int k) {
        int[] state = init(n, m, k);

        System.out.println("-------------");
        switch (option) {
            case 1:
                BKT bkt = new BKT(capacities, state, visitedStates);
                bkt.solve();
                break;

            case 2:
                BFS bfs = new BFS(state);
                bfs.solve();
                break;

            case 3:
                HillClimbing hillClimbing = new HillClimbing(state);
                hillClimbing.solve();
                break;

            case 4:
                AStar aStar = new AStar(state);
                aStar.solve();
                break;

            default:
                System.out.println("Invalid option");

        }
        System.out.println();
        System.out.println("-------------");

    }

    public static void main(String[] args) {

        String[] options = {"1 - BKT", "2 - BFS", "3 - Hill Climbing", "4 - A *", "5 - EXIT"};
        Scanner scanner = new Scanner(System.in);

        int option = 0;
        int n = 0;
        int m = 0;
        int k = 0;

        while (true) {
            for (String opc : options) {
                System.out.println(opc);
            }

            System.out.println("Choose an option: ");
            option = scanner.nextInt();

            if (option == 5)
                break;

            System.out.println("Enter first jug capacity: ");
            n = scanner.nextInt();

            System.out.println("Enter second jug capacity: ");
            m = scanner.nextInt();

            System.out.println("Enter target: ");
            k = scanner.nextInt();


            executeOption(option, n, m, k);
        }

    }


}

