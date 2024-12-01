import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class Game {

    public static Set<Node> observedStates = new HashSet<>();

    public static Heuristic heuristic = new ManhattanDistance();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the algorithm to use (bfs, dfs, a*): ");
        String algorithm = scanner.nextLine();
        Node node = createStartNode();
        long millis = System.currentTimeMillis();
        Node solution = null;
        switch (algorithm) {
            case "bfs" -> solution = bfs(node);
            case "dfs" -> solution = dfs(node);
            case "a*" -> solution = aStar(node);
            default -> {
                System.out.println("Invalid algorithm");
                System.exit(1);
            }
        }
        if (solution == null) {
            System.out.println("No solution found");
        } else {
            for (Node n : solution) {
                System.out.println("Depth: " + n.getDepth());
                System.out.println(n);
            }
            System.out.println("Solution found in " + solution.getDepth() + " steps");
        }

        long time = System.currentTimeMillis() - millis;
        System.out.println("Time: " + time + "ms");
    }

    public static Node aStar(Node start) {
        PriorityQueue<Node> todo = new PriorityQueue<>();
        todo.add(start);
        int count = 0;
        while (!todo.isEmpty()) {
            count++;

            Node current = todo.poll();
            if (isSolved(current)) {
                System.out.println("Nodes expanded: " + count);
                return current;
            }
            ArrayDeque<Node> children = expandNode(current);
            for (Node child : children) {
                if (!observedStates.contains(child)) {
                    todo.add(child);
                    observedStates.add(child);
                }
            }
        }
        return null;
    }

    public static Node bfs(Node start) {
        ArrayDeque<Node> todo = new ArrayDeque<>();
        todo.offer(start);
        while (!todo.isEmpty()) {
            Node current = todo.poll();
            if (isSolved(current)) {
                return current;
            }
            ArrayDeque<Node> children = expandNode(current);
            for (Node child : children) {
                if (!observedStates.contains(child)) {
                    todo.offer(child);
                    observedStates.add(child);
                }
            }
        }
        return null;
    }

    public static Node dfs(Node start) {
        ArrayDeque<Node> todo = new ArrayDeque<>();
        todo.offer(start);

        while (!todo.isEmpty()) {
            Node current = todo.pollLast();
            if (isSolved(current)) {
                return current;
            }
            ArrayDeque<Node> children = expandNode(current);
            for (Node child : children) {
                if (!observedStates.contains(child)) {
                    todo.offer(child);
                    observedStates.add(child);
                }
            }
        }
        return null;
    }

    public static Node createStartNode() {
        char[][] board = new char[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                switch (i) {
                    case 0, 1 -> {
                        switch (j) {
                            case 0, 1 -> board[i][j] = ' ';
                            default -> board[i][j] = 'W';
                        }
                    }
                    case 2 -> {
                        switch (j) {
                            case 0, 1 -> board[i][j] = 'B';
                            case 3, 4 -> board[i][j] = 'W';
                            case 2 -> board[i][j] = 'o';
                        }
                    }
                    case 3, 4 -> {
                        switch (j) {
                            case 3, 4 -> board[i][j] = ' ';
                            default -> board[i][j] = 'B';
                        }
                    }
                }

            }
        }
        return new Node(null, 0, board, 2, 2);
    }

    public static ArrayDeque<Node> expandNode(Node node) {
        ArrayDeque<Node> list = new ArrayDeque<>();
        char[][] board = node.getBoard();
        int emptyRow = node.getEmptyRow();
        int emptyColumn = node.getEmptyColumn();
        int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-2, 0}, {2, 0}, {0, -2}, {0, 2}};
        for (int[] move : moves) {
            int newRow = emptyRow + move[0];
            int newColumn = emptyColumn + move[1];
            if (newRow >= 0 && newRow < 5 && newColumn >= 0 && newColumn < 5 && board[newRow][newColumn] != ' ') {
                Node newState = getNewNode(board, emptyRow, emptyColumn, newRow, newColumn, node);
                list.add(newState);
            }
        }
        return list;
    }

    private static Node getNewNode(char[][] board, int emptyRow, int emptyColumn, int newEmptyRow, int newEmptyColumn, Node node) {
        char[][] newBoard = deepCopy(board);
        newBoard[emptyRow][emptyColumn] = newBoard[newEmptyRow][newEmptyColumn];
        newBoard[newEmptyRow][newEmptyColumn] = 'o';
        return new Node(node, node.getDepth() + 1, newBoard, newEmptyRow, newEmptyColumn);
    }

    public static boolean isSolved(Node node) {
        char[][] board = node.getBoard();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if ((i < 2 && j >= 3 && board[i][j] != 'B') ||
                    (i >= 3 && j <= 2 && board[i][j] != 'W') ||
                    (i == 2 && ((j < 2 && board[i][j] != 'W') ||
                        (j > 2 && board[i][j] != 'B') ||
                        (j == 2 && board[i][j] != 'o')))) {
                    return false;
                }
            }
        }
        return true;
    }

    static char[][] deepCopy(char[][] board) {
        char[][] newBoard = new char[5][5];
        for (int i = 0; i < 5; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, 5);
        }
        return newBoard;
    }

}
