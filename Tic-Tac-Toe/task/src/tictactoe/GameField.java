package tictactoe;

public class GameField {
    private static final int FIELD_SIZE = 3;
    private static final char X_SIGN = 'X';
    private static final char O_SIGN = 'O';
    private static final char EMPTY_SIGN = '_';
    private static final String OCCUPIED_CELL_MESSAGE = "This cell is occupied! Choose another one!";
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
        stat.count();
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

    public void applyMove(Move move) throws WrongMoveException {
        int i = FIELD_SIZE - move.getCoord2();
        int j = move.getCoord1() - 1;
        if (field[i][j] != EMPTY_SIGN) {
            throw new WrongMoveException(OCCUPIED_CELL_MESSAGE);
        }
        field[i][j] = move.getSign();
    }

    enum GameState {
        NOT_FINISHED("Game not finished", false),
        DRAW("Draw", true),
        X_WINS("X wins", true),
        O_WINS("O wins", true),
        IMPOSSIBLE("Impossible", true);

        private final String message;
        private final boolean isTerminal;

        GameState(String msg, boolean isTerminal) {
            message = msg;
            this.isTerminal = isTerminal;
        }

        public String getMessage() {
            return message;
        }

        public boolean isTerminal() {
            return isTerminal;
        }
    }

    private class GameFieldStat {
        private int xSignCount;
        private int oSignCount;
        private int emptySignCount;
        private int xRowCount;
        private int oRowCount;

        public GameFieldStat() {
            count();
        }

        private void count() {
            resetCounters();
            countFieldProperties();
            countSignsInARow();
        }

        private void resetCounters() {
            xSignCount = 0;
            oSignCount = 0;
            emptySignCount = 0;
            xRowCount = 0;
            oRowCount = 0;
        }

        private void countFieldProperties() {
            for (int i = 0; i < FIELD_SIZE; i++) {
                for (int j = 0; j < FIELD_SIZE; j++) {
                    switch (field[i][j]) {
                        case X_SIGN:
                            xSignCount++;
                            break;
                        case O_SIGN:
                            oSignCount++;
                            break;
                        default:
                            emptySignCount++;
                            break;
                    }
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
