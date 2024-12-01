public class MisplacedTiles implements Heuristic {

    @Override
    public int calculate(Node node) {
        char[][] board = node.getBoard();
        int count = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                switch (i) {
                    case 0, 1 -> {
                        switch (j) {
                            case 0, 1 -> {
                                if (board[i][j] != ' ') {
                                    count++;
                                }
                            }
                            default -> {
                                if (board[i][j] != 'B') {
                                    count++;
                                }
                            }
                        }
                    }
                    case 2 -> {
                        switch (j) {
                            case 0, 1 -> {
                                if (board[i][j] != 'W') {
                                    count++;
                                }
                            }
                            case 3, 4 -> {
                                if (board[i][j] != 'B') {
                                    count++;
                                }
                            }
                            case 2 -> {
                                if (board[i][j] != 'o') {
                                    count++;
                                }
                            }
                        }
                    }
                    case 3, 4 -> {
                        switch (j) {
                            case 3, 4 -> {
                                if (board[i][j] != ' ') {
                                    count++;
                                }
                            }
                            default -> {
                                if (board[i][j] != 'W') {
                                    count++;
                                }
                            }
                        }
                    }
                }
            }

        }
        return count;
    }
}
