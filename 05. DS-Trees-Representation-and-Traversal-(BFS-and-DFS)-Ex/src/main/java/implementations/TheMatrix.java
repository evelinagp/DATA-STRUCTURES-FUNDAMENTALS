package implementations;


public class TheMatrix {
    private char[][] matrix;
    private char fillChar;
    private char toBeReplaced;
    private int startRow;
    private int startCol;

    public TheMatrix(char[][] matrix, char fillChar, int startRow, int startCol) {
        this.matrix = matrix;
        this.fillChar = fillChar;
        this.startRow = startRow;
        this.startCol = startCol;
        this.toBeReplaced = this.matrix[this.startRow][this.startCol];
    }

    public void solve() {
        fillMatrix(this.startRow, this.startCol);
    }

    private void fillMatrix(int row, int col) {
        if (isOutOfBounds(row, col) || this.matrix[row][col] != this.toBeReplaced) {
            return;
        }

        this.matrix[row][col] = fillChar;

        this.fillMatrix(row + 1, col);
        this.fillMatrix(row, col + 1);
        this.fillMatrix(row - 1, col);
        this.fillMatrix(row, col - 1);

    }

    private boolean isOutOfBounds(int row, int col) {
        return row < 0 || row >= matrix.length || col < 0 || col >= matrix[row].length;
    }

    public String toOutputString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                stringBuilder.append(matrix[row][col]);
            }
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString().trim();
    }
}

