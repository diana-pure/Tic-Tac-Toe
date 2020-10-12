package tictactoe;

public class GameField {
    private final static int FIELD_SIZE = 3;
    private final static char X_SIGN = 'X';
    private final static char O_SIGN = 'O';
    private final char[][] field;
    private final GameFieldStat stat;

    public GameField(char[][] f) {
        field = f;
        stat = new GameFieldStat();
    }

    public static GameField fromString(String s) {
        return new GameField(getFieldFromString(s));
    }

    public GameState getSate() {
        if (Math.abs(stat.xSignCount - stat.oSignCount) > 1) {
            return GameState.IMPOSSIBLE;
        }
        if ((stat.xRowCount + stat.oRowCount) > 1) {
            return GameState.IMPOSSIBLE;
        }
        if (stat.xRowCount == 1) {
            return GameState.X_WINS;
        }
        if (stat.oRowCount == 1) {
            return GameState.O_WINS;
        }
        if (stat.emptySignCount == 0) {
            return GameState.DRAW;
        }
        return GameState.NOT_FINISHED;
    }

    private static char[][] getFieldFromString(String s) {
        char[][] field = new char[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i++) {
            field[i][0] = s.charAt(i * 3);
            field[i][1] = s.charAt(i * 3 + 1);
            field[i][2] = s.charAt(i * 3 + 2);
        }
        return field;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("---------\n");
        for (int i = 0; i < FIELD_SIZE; i++) {
            sb.append("| ")
                    .append(field[i][0]).append(" ")
                    .append(field[i][1]).append(" ")
                    .append(field[i][2]).append(" |\n");
        }
        sb.append("---------");
        return sb.toString();
    }

    enum GameState {
        NOT_FINISHED("Game not finished"),
        DRAW("Draw"),
        X_WINS("X wins"),
        O_WINS("O wins"),
        IMPOSSIBLE("Impossible");

        private final String message;

        GameState(String msg) {
            message = msg;
        }

        public String getMessage() {
            return message;
        }
    }

    private class GameFieldStat {
        private int xSignCount;
        private int oSignCount;
        private int emptySignCount;
        private int xRowCount;
        private int oRowCount;

        public GameFieldStat() {
            countFieldProperties();
            countSignsInARow();
        }

        private void countFieldProperties() {
            for (int i = 0; i < FIELD_SIZE; i++) {
                for (int j = 0; j < FIELD_SIZE; j++) {
                    if (field[i][j] == X_SIGN) {
                        xSignCount++;
                        continue;
                    }
                    if (field[i][j] == O_SIGN) {
                        oSignCount++;
                        continue;
                    }
                    emptySignCount++;
                }
            }
        }

        private void countSignsInARow() {
            for (int i = 0; i < FIELD_SIZE; i++) {
                //row
                if (field[i][0] == field[i][1] && field[i][1] == field[i][2]) {
                    switch (field[i][0]) {
                        case X_SIGN:
                            xRowCount++;
                            break;
                        case O_SIGN:
                            oRowCount++;
                            break;
                    }
                }
                //col
                if (field[0][i] == field[1][i] && field[1][i] == field[2][i]) {
                    switch (field[0][i]) {
                        case X_SIGN:
                            xRowCount++;
                            break;
                        case O_SIGN:
                            oRowCount++;
                            break;
                    }
                }
            }
            //diagonal 1
            if (field[0][0] == field[1][1] && field[1][1] == field[2][2]) {
                switch (field[1][1]) {
                    case X_SIGN:
                        xRowCount++;
                        break;
                    case O_SIGN:
                        oRowCount++;
                        break;
                }
            }
            //diagonal 2
            if (field[0][2] == field[1][1] && field[1][1] == field[2][0]) {
                switch (field[1][1]) {
                    case X_SIGN:
                        xRowCount++;
                        break;
                    case O_SIGN:
                        oRowCount++;
                        break;
                }
            }
        }
    }
}
