package tictactoe;

public class Move {
    private char sign;
    private int coord1;
    private int coord2;

    public Move(char sign, int coord1, int coord2) {
        this.sign = sign;
        this.coord1 = coord1;
        this.coord2 = coord2;
    }

    public int getCoord1() {
        return coord1;
    }

    public int getCoord2() {
        return coord2;
    }

    public char getSign() {
        return sign;
    }
}
