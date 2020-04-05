import java.util.ArrayList;
import java.util.Collections;

public class GameState {
    private ArrayList<Tower> towers = new ArrayList<>();
    private int maxDiskSize;
    private String diskChar = "_";
    private String poleChar = "|";
    private int spaceBetweenTowers = 6;

    public GameState(int numOfTowers, int maxDiskSize) {
        this.maxDiskSize = maxDiskSize;

        for (int i=0; i<numOfTowers; i++) {
            towers.add(new Tower());
        }
    }

    public int getNumOfTowers() {
        return towers.size();
    }

    public ArrayList<Tower> getTowers() {
        return towers;
    }

    public void updateTower(int index, Tower towerToAdd) {
        if ((index > towers.size()) || (index < 0)) {
            throw new IllegalArgumentException("Provided index is out of range.");
        }

        towers.set(index, towerToAdd);
    }

    public void printGameState() {
        for (Tower tower : towers) {
            if (tower == null) {
                throw new NullPointerException("At least one of the towers has not been initialised.");
            }
        }

        ArrayList<ArrayList<String>> formattedTowers = new ArrayList<>();

        for (Tower tower : towers) {
            formattedTowers.add(mkFormattedTower(tower));
        }

        String currentLine = "";
        for (int i=0; i<maxDiskSize; i++) {
            for (ArrayList<String> formattedTower : formattedTowers) {
                currentLine += formattedTower.get(i);
                currentLine += " ".repeat(spaceBetweenTowers);
            }
            System.out.println(currentLine);
            currentLine = "";
        }
    }

    private ArrayList<String> mkFormattedTower(Tower tower) {
        ArrayList<String> output = new ArrayList<>();
        ArrayList<Integer> stack = tower.getStack();

        for (int disk : stack) {
            output.add(mkFormattedDisk(disk));
        }

        while (output.size() < maxDiskSize) {
            output.add(" ".repeat(maxDiskSize-1) + poleChar.repeat(2) + " ".repeat(maxDiskSize-1));
        }

        Collections.reverse(output);
        return output;
    }

    private String mkFormattedDisk(int diskSize) {
        String output = diskChar.repeat(2*diskSize);

        while (output.length() < maxDiskSize*2) {
            if ((output.length() % 2) == 0) {
                output = " " + output;
            } else {
                output = output + " ";
            }
        }

        return output;
    }
}
