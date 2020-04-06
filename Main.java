import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public enum player {ME, AI}

    public static void main(String[] args) {
        System.out.println("Welcome to Towers of Hanoi!");
        initialiseGame();
    }

    public static void initialiseGame() {
        int numOfTowers = 3;
        int numOfDisks = 0;
        Scanner input = new Scanner(System.in);
        boolean proceed = false;
        String inputtedLine;

        player chosenPlayer = player.ME;
        while ((!proceed) && (numOfTowers==3)) {
            System.out.println("\nType '" + player.AI + "' to see computer solve, or '" + player.ME + "' to solve yourself: ");
            inputtedLine = input.nextLine();

            if (player.AI.name().equalsIgnoreCase(inputtedLine.strip())) {
                chosenPlayer = player.AI;
                proceed = true;
            }

            if (player.ME.name().equalsIgnoreCase(inputtedLine.strip())) {
                chosenPlayer = player.ME;
                proceed = true;
            }

            if (!proceed) {
                System.err.println("Enter either '" + player.AI + "' or '" + player.ME + ".\n");
            }
        } // human or AI

        proceed = false;
        while (!proceed) {
            System.out.println("\nEnter the number of disks to play with: ");
            inputtedLine = input.nextLine();
            try {
                int i = Integer.parseInt(inputtedLine.replaceAll(" ", ""));
                proceed = true;
            } catch (NumberFormatException nfe) {
                System.err.println("Enter an integer greater than 1.");
            }

            if (proceed) {
                numOfDisks = Integer.parseInt(inputtedLine);
                if (numOfDisks<=1) {
                    System.err.println("Enter an integer greater than 1");
                    proceed = false;
                }
            }
        } // disks to play with

        if (numOfDisks == 0) {
            throw new IllegalArgumentException("Number of disks has not been set.");
        }

        NewGame game = new NewGame(numOfTowers, numOfDisks, chosenPlayer);
    }
}
