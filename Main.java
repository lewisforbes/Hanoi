import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Towers of Hanoi!\n");
        initialiseGame();
    }

    public static void initialiseGame() {
        int numOfTowers = 3;
        int numOfDisks = 0;
        Scanner input = new Scanner(System.in);
        boolean proceed = false;

        while (!proceed) {
            System.out.println("Enter the number of disks to play with: ");
            String inputtedLine = input.nextLine();
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
        }

        NewGame game = new NewGame(numOfTowers, numOfDisks);
    }
}
