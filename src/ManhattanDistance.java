public class ManhattanDistance implements Heuristic {

    @Override
    public int calculate(Node node) {
        char[][] board = node.getBoard();
        int distance = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board[i][j] == 'W') {
                    if (i == 0) {
                        distance += 4;
                    } else if (i == 1) {
                        distance += 2;
                    }
                    if (j == 4) {
                        distance += 4;
                    } else if (j == 3) {
                        distance += 2;
                    }
                }
            }
        }
        return distance;
    }
}
