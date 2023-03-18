import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the size of matrix: ");

        int n = scanner.nextInt();

        int[][] queens = new int[n + 1][n + 1];

        int numberOfBlocks = scanner.nextInt();
        for (int i = 1; i < numberOfBlocks + 1; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            queens[x][y] = -1;
        }

        for (int i = 1; i < queens.length; i++) {
            for (int j = 1; j < queens.length; j++) {
                if (queens[i][j] != -1)
                    queens[i][j] = 0;
            }
        }

        char[][] finalMatrix = new char[n + 1][n + 1];
        System.out.println();


        ForwardChecking.solve(queens);

        for (int i = 1; i < queens.length; i++) {
            for (int j = 1; j < queens.length; j++) {
                if (queens[i][j] == -1)
                    finalMatrix[i][j] = 'B';

                else if (queens[i][j] == -2)
                    finalMatrix[i][j] = 'R';

                else if (queens[i][j] != 0 || queens[i][j] == 0)
                    finalMatrix[i][j] = '*';
            }
        }

        System.out.println();
        for (int i = 1; i < queens.length; i++) {
            for (int j = 1; j < queens.length; j++) {
                System.out.print(finalMatrix[i][j] + " ");
            }
            System.out.println();
        }

    }
}