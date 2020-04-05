import java.util.ArrayList;
import java.util.Scanner;

public class NewGame {

    private boolean stop;
    private GameState currentState;
    private String currentMove;
    private Tower completeTower;
    private static final Scanner input = new Scanner(System.in);
    private int movesTaken = 0;
    private double minMoves = 0;
    private final String playAgain = "Y";

    public NewGame(int numOfTowers, int maxDiskSize) {
        this.stop = false;
        this.currentState = new GameState(numOfTowers, maxDiskSize);
        this.completeTower = initialiseTower(maxDiskSize);
        currentState.updateTower(0,completeTower);
        if (numOfTowers==3) {
            this.minMoves = Math.pow(2, maxDiskSize) - 1;
        }
        play();
    }

    private Tower initialiseTower(int maxDiskSize) {
        Tower output = new Tower();
        for (int i=maxDiskSize; i>=1; i--) {
            output.addToStack(i);
        }
        return output;
    }

    private void play() {
        while (!stop) {
            movesTaken += 1;
            System.out.println("");
            currentState.printGameState();
            System.out.println("Enter your move:");
            currentMove = getNextMove();

            String[] move = currentMove.split(" ");
            Tower from = currentState.getTowers().get(Integer.parseInt(move[0]) - 1);
            Tower to = currentState.getTowers().get(Integer.parseInt(move[1]) - 1);
            to.addToStack(from.getTopDisk());
            from.removeFromStack();

            stop = checkWin();
        }
        currentState.printGameState();
        System.out.println("\nCongratulations! It took you " + movesTaken + " moves to finish!");
        if (minMoves!=0) {
            System.out.println("The best solution has " + Math.round(minMoves) + ".");
        }

        System.out.println("\nType '" + playAgain + "' to play again");
        if (input.nextLine().equalsIgnoreCase(playAgain)) {
            Main.initialiseGame();
        }
    }

    private String getNextMove() {
        boolean validMove = false;
        String inputtedLine = "";
        while (!validMove) {
            inputtedLine = input.nextLine();
            validMove = validateMoveFormat(inputtedLine);
            if (validMove) {
                validMove = isMovePossible(inputtedLine);
            }
        }
        return inputtedLine;
    }

    private boolean validateMoveFormat(String inputtedLine) {
        String errorMessage = "Move entered is not of the correct format.";

        // checks string is numeric
        try {
            int i = Integer.parseInt(inputtedLine.replaceAll(" ", ""));
        } catch (NumberFormatException nfe) {
            System.err.println(errorMessage);
            return false;
        }

        // ensures numbers entered are integers
        if (inputtedLine.contains(".")) {
            System.err.println(errorMessage);
            return false;
        }

        // checks inputted line has two strings with a space in between
        String[] inputtedChars = inputtedLine.split(" ");
        if (inputtedChars.length != 2) {
            System.err.println(errorMessage);
            return false;
        }

        // checks inputted numbers are not larger than number of towers or less that 1
        for (String move : inputtedChars)
            if ((Integer.parseInt(move) > currentState.getNumOfTowers()) || (Integer.parseInt(move) < 1)) {
                System.err.println(errorMessage);
                return false;
            }

        // checks inputted numbers are not equal
        if (inputtedChars[0].equals(inputtedChars[1])) {
            System.err.println(errorMessage);
            return false;
        }
        return true;
    }

    private boolean isMovePossible(String inputtedLine) {
        String errorMessage = "Move entered is not possible.";
        String[] attemptedMove = inputtedLine.split(" ");
        if (attemptedMove.length != 2) {
            throw new IllegalArgumentException("The validMoveFormat method has let through " + inputtedLine);
        }

        // gets move and indexes them from 0
        Tower towerFrom = currentState.getTowers().get(Integer.parseInt(attemptedMove[0])-1);
        Tower towerTo = currentState.getTowers().get(Integer.parseInt(attemptedMove[1])-1);

        if (towerFrom.getStack().size() == 0) {
            System.err.println(errorMessage);
            return false;
        }

        if (towerTo.getStack().size() == 0) {
            return true;
        }

        int fromTopDisk = towerFrom.getTopDisk();
        int toTopDisk  = towerTo.getTopDisk();

        if (fromTopDisk==toTopDisk) {
            throw new IllegalArgumentException("Two stacks have the same disk");
        }


        if (fromTopDisk>toTopDisk) {
            System.err.println(errorMessage);
            return false;
        }

        return true;
    }

    private boolean checkWin() {
        ArrayList<Tower> towers = currentState.getTowers();
        for (int i=1; i<towers.size(); i++) {
            if (towers.get(i).equals(completeTower)) {
                return true;
            }
        }
        return false;
    }
}
