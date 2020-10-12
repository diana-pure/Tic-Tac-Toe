package tictactoe;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        GameField field = GameField.fromString(input);
        System.out.println(field.toString());
        System.out.println(field.getSate().getMessage());
    }
}