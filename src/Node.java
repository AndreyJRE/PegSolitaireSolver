import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class Node implements Iterable<Node>, Comparable<Node> {

    private final char[][] board;

    private final int emptyRow;
    private final int emptyColumn;

    private final Node parent;
    private final int depth;

    public Node(Node parent, int depth, char[][] board, int emptyRow, int emptyColumn) {
        this.board = board;
        this.parent = parent;
        this.depth = depth;
        this.emptyRow = emptyRow;
        this.emptyColumn = emptyColumn;
    }

    public int getDepth() {
        return depth;
    }


    @Override
    public Iterator<Node> iterator() {
        return new Iterator<>() {
            private Node current = Node.this;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Node next() {
                Node node = current;
                current = current.parent;
                return node;
            }
        };
    }

    public char[][] getBoard() {
        return board;
    }

    public int getEmptyRow() {
        return emptyRow;
    }

    public int getEmptyColumn() {
        return emptyColumn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Node node = (Node) o;

        return Arrays.deepEquals(this.board, node.board);
    }

    @Override
    public int hashCode() {
        final int prime = 97;  // Using a larger prime to reduce collision
        int result = 1;  // Start with a non-zero value

        for (char[] row : board) {
            if (row != null) {
                int rowHash = 1;  // Calculate hash for each row
                for (char c : row) {
                    rowHash = prime * rowHash + c;  // Combine each character with prime multiplier
                }
                result = 31 * result + rowHash;  // Combine row hashes into final result
            } else {
                result = 31 * result;  // Handle null rows
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (char[] row : board) {
            for (char field : row) {
                sb.append("[").append(field).append("]");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public int compareTo(Node o) {
        return Comparator.comparing(o1 -> {
            Node n = (Node) o1;
            return Game.heuristic.calculate(n) + n.getDepth();
        }).compare(this, o);
    }
}
