import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class ForwardChecking {

    static int[] queensPositions;
    static List<Integer> colList;

    public ForwardChecking() {

    }

    public static void solve(int[][] queens) {
        boolean ok = false;
        colList = new ArrayList<>();

        queensPositions = new int[queens.length + 1];

        int col = 1;
        int row = 1;
        int colFromQueen;
        while (!ok) {

            if (row == queens.length) {
                col--;
                row = 1;
            }

            if (queens[row][col] != 0) {
                if (queens[row][col] == -2) {
                    col = col + 1;

                    colFromQueen = col - 1;
                    DeAttackEverything(queens, col, row, colFromQueen);
                    col = col - 1;

                    queens[row][col] = 0;
                    int rowOriginal = row;

                    if(col-1 != 0 && row==queens.length-2) {
                        if(colList.size() != 0)
                            colList.remove(col-1);
                        col = col - 1;
                        row = 1;
                    }
                    else{
                        if(colList.size() != 0)
                            colList.remove(col-1);
                        row= rowOriginal + 1;
                    }
                    continue;
                }
                row = row + 1;
                continue;
            } else {

                if (!colList.contains(col)) {
                    queens[row][col] = -2;
                    colList.add(col);

                    if(colList.size() == queens.length-1)
                        break;

                    col = col + 1;

                    colFromQueen = col - 1;
                    AttackEverything(queens, col, row, colFromQueen);


                    int counter = 1;
                    for (int rowFinding = 1; rowFinding < queens.length; rowFinding++) {
                        if (queens[rowFinding][col] != 0) {
                            counter = counter + 1;
                            continue;
                        } else {
                            row = rowFinding;
                            break;
                        }
                    }


                    if (counter == queens.length) {
                        col = col - 1;

                        for (int rowFinding = 1; rowFinding < queens.length; rowFinding++) {
                            if (queens[rowFinding][col] == -2) {
                                col = col + 1;

                                colFromQueen = col - 1;
                                DeAttackEverything(queens, col, row, colFromQueen);
                                break;
                            }
                        }
                        col = col - 1;

                        queens[row][col] = 0;
                        colList.remove(col-1);
                        row++;

                        if (row == queens.length) {
                            col = col - 1;
                            row = 1;
                        }
                    }
                } else {
                    row = row + 1; // Se apeleaza cand avem un 0, dar noi vrem sa gasim regina de mai jos
                }
            }


        }

    }

    private static void AttackEverything(int[][] queens, int col, int row, int colFromQueen) {
        for (int colAttack = col; colAttack < queens.length; colAttack++) { // HORIZONTAL
            queens[row][colAttack]++;
            for (int rowAttack = 1; rowAttack < queens.length; rowAttack++) {
                if (queens[rowAttack][colAttack] == -1)
                    continue;
                if (abs(colAttack - colFromQueen) == abs(rowAttack - row))
                    queens[rowAttack][colAttack]++;
            }
        }
    }

    private static void DeAttackEverything(int[][] queens, int col, int row, int colFromQueen) {
        for (int colAttack = col; colAttack < queens.length; colAttack++) { // HORIZONTAL
            queens[row][colAttack]--;
            for (int rowAttack = 1; rowAttack < queens.length; rowAttack++) {
                if (queens[rowAttack][colAttack] == -1)
                    continue;
                if (abs(colAttack - colFromQueen) == abs(rowAttack - row))
                    queens[rowAttack][colAttack]--;
            }
        }
    }
}
