package packages.Lesson02Enums;

import packages.helpers.Input;
import java.nio.file.AccessMode;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Lesson02EnumsMain {
    public static void main() {

        /* An enum is a second way to create a custom type along with class. It shares a few class conventions:
            1. Enum names are ProperCase.
            2. A public enum must be stored alone in a .java source file with the same name as the enum.
            3. It may use access modifiers.
            4. It has a code block.

           EnumName value = EnumName.VALUE;*/

        Move p1Move = Move.ROCK;
        Move p2Move = Move.PAPER;
        p1Move = Move.SCISSORS;
        System.out.println(p1Move); // SCISSORS
        System.out.println(p2Move); // PAPER
        // With enums, value equality and reference equality are the same thing.
        if (p1Move == p2Move) {
            System.out.println("It's a tie.");
        } else {
            System.out.println("There's going to be a winner and a loser.");
        }

        // The string argument passed to valueOf must match the name of an enum value exactly or the operation will fail
        // with an IllegalArgumentException.
        Scanner console = new Scanner(System.in);
        System.out.println("Player 1, choose Rock, Paper, or Scissors: ");
        // since values are UPPER_CASE, make the input upper case
        String input = Input.getWord(console).toUpperCase();
        p1Move = Move.valueOf(input);
        System.out.println("Player 1, your move is: " + p1Move);
        System.out.print("Player 2, choose Rock, Paper, or Scissors: ");
        input = console.nextLine().toUpperCase();
        p2Move = Move.valueOf(input);
        System.out.println("Player 2, your move is: " + p2Move);
        // We can make it error proof with a combination of while and try catch:
        p1Move = chooseMove("Player 1");
        p2Move = chooseMove("Player 2");
        System.out.printf("Player 1 chose: %s. Player 2 chose: %s.%n", p1Move, p2Move);

        // Enums work great for switch cases:
        p1Move = chooseMove("Player 1");
        switch (p1Move) {
            case ROCK:
                System.out.println("You chose rock.");
                System.out.println("Rock beats scissors.");
                break;
            case PAPER:
                System.out.println("You chose paper.");
                System.out.println("Paper beats rock.");
                break;
            case SCISSORS:
                System.out.println("You chose scissors.");
                System.out.println("Scissors beats paper.");
                break;
        }

        // never need to instantiate enums because they are pre-instantiated.
        System.out.println(Color.BLUE.getTraditionalCompliment());
        System.out.println(Color.YELLOW.getSpanishName());
        System.out.println(Color.findByHex("FF0000"));
        System.out.println(Color.ORANGE.getTraditionalCompliment().getSpanishName());
        System.out.println(Color.findByHex("ffA500"));

        //here's a random enumerator from java.nio.file
        System.out.println(Arrays.toString(AccessMode.values()));
    }

    static Move chooseMove(String playerName) {

        Scanner console = new Scanner(System.in);
        Move result = Move.ROCK; // Enums can never hold a value that isn't listed, so it must be
                // instantiated with a listed value;
        boolean isValid = false;

        do {
            System.out.printf("%s, choose your move [Rock/Paper/Scissors]: ", playerName);
            try {
                result = Move.valueOf(console.nextLine().toUpperCase());
                isValid = true;
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid move.");
            }
        } while (!isValid);

        return result;
    }
}
