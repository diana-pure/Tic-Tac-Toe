package tictactoe;

import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
    private static final String CELLS_REQUEST_MESSAGE = "Enter cells: ";
    private static final String COORDS_REQUEST_MESSAGE = "Enter the coordinates: ";
    private static final String NUMBERS_REQUEST_MESSAGE = "You should enter numbers!";
    private static final String RANGE_NUMBERS_REQUEST_MESSAGE = "Coordinates should be from 1 to 3!";
    private static final Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print(CELLS_REQUEST_MESSAGE);
        String input = s.nextLine();
        GameField field = GameField.fromString(input);
        System.out.println(field.toString());
//        System.out.println(field.getSate().getMessage());
        Move move = getMove();
        applyMove(field, move);
        System.out.println(field.toString());
    }

    private static void applyMove(GameField field, Move move) {
        Move m = move;
        do {
            try {
                field.applyMove(m);
                break;
            } catch (WrongMoveException e) {
                System.out.println(e.getMessage());
                m = getMove();
            }
        } while (true);
    }

    private static Move getMove() {
        int coord1;
        int coord2;
        do {
            System.out.print(COORDS_REQUEST_MESSAGE);
            try {
                String coords = s.nextLine();
                coords = coords.trim();
                if (Character.isDigit(coords.charAt(0))) {
                    coord1 = Character.digit(coords.charAt(0), 10);
                } else {
                    throw new InputMismatchException(NUMBERS_REQUEST_MESSAGE);
                }
                if (coords.charAt(1) == ' ' && (Character.isDigit(coords.charAt(2)))) {
                    coord2 = Character.digit(coords.charAt(2), 10);
                } else {
                    throw new InputMismatchException(NUMBERS_REQUEST_MESSAGE);
                }
                if (coord1 < 1 || coord1 > 3 || coord2 < 1 || coord2 > 3) {
                    throw new InputMismatchException(RANGE_NUMBERS_REQUEST_MESSAGE);
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        return new Move(coord1, coord2);
    }
}