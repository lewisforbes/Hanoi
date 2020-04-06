import java.util.ArrayList;
import java.util.HashMap;

/**
 * This 'AI' generates the perfect solution for the given number of disks
 * It only works for 3 towers
 * The algorithm is from https://youtu.be/2SUvWfNJSsM (3Blue1Brown on YouTube)
 */

public class AI {

    public static ArrayList<String> moves;

    private static HashMap<Integer, Integer> disksPos = new HashMap();
    private static int[] polesTopLayer = new int[3];
    private static int largeDisk;

    public static void setMoves(int numOfDisks) {
        ArrayList<String> output = new ArrayList<>();
        initialisePositions(numOfDisks);

        int reqMoves = (int) (Math.pow(2, numOfDisks)-1);
        int finalBinLength = Integer.toBinaryString(reqMoves).length();
        String currentBinNum = mkRightLength(finalBinLength, "0");
        String nextBinNum = mkRightLength(finalBinLength, binaryPlusOne(currentBinNum));

        output.add(moveToMake(currentBinNum, nextBinNum));

        while (output.size() < reqMoves) {
            currentBinNum = nextBinNum;
            nextBinNum = mkRightLength(finalBinLength, binaryPlusOne(currentBinNum));
            output.add(moveToMake(currentBinNum, nextBinNum));
        }

        moves = output;
    }

    private static String moveToMake(String current, String next) {
        int changedIndex = -1;
        int length = current.length();

        for (int i = 0; i < length; i++) {
            if (current.charAt(i) != next.charAt(i)) {
                changedIndex = i;
                break;
            }
        }
        if (changedIndex == -1) {
            throw new IllegalArgumentException("The index remained unchanged.");
        }

        int diskToMove = length - changedIndex;
        int currentPos;
        int newPos;
        if (diskToMove == 1) {
            currentPos = disksPos.get(1);
            newPos = currentPos+1;
            if (newPos == 4) {
                newPos = 1;
            }
            updatePositions(diskToMove, currentPos, newPos);
            return currentPos + " " + newPos;
        }

        currentPos = disksPos.get(diskToMove);
        newPos = dest(diskToMove, currentPos);
        updatePositions(diskToMove, currentPos, newPos);
        return currentPos + " " + newPos;
    }

    private static int dest(int disk, int currentPos) {
        int potentialMove = currentPos+1;
        if (potentialMove==4) {
            potentialMove = 1;
        }
        if (disk < polesTopLayer[potentialMove-1]) {
            return potentialMove;
        }

        potentialMove++;
        if (potentialMove==4) {
            potentialMove = 1;
        }
        if (disk < polesTopLayer[potentialMove-1]) {
            return potentialMove;
        }

        throw new IllegalArgumentException("The disk could not be moved.");
    }

    private static void updatePositions(int diskToMove, int currentPos, int newPos) {
        disksPos.put(diskToMove, newPos);
        polesTopLayer[newPos-1] = diskToMove;

        int smallestDisk = largeDisk;
        for (Integer disk : disksPos.keySet()) {
            if (disksPos.get(disk) == currentPos) {
                if (disk < smallestDisk) {
                    smallestDisk = disk;
                }
            }
        }

        polesTopLayer[currentPos-1] = smallestDisk;
    }

    private static void initialisePositions(int numOfDisks) {
        largeDisk = numOfDisks+1;
        for (int i=1; i<=numOfDisks; i++) {
            disksPos.put(i, 1);
        }

        polesTopLayer[0] = 1;
        polesTopLayer[1] = largeDisk;
        polesTopLayer[2] = largeDisk;
    }

    private static String binaryPlusOne(String binaryNum) {
        int output = Integer.parseInt(binaryNum,2);
        output ++;
        return Integer.toBinaryString(output);
    }

    private static String mkRightLength(int length, String binNum) {
        if (binNum.length() > length) {
            throw new IllegalArgumentException("Number is too big.");
        }

        if (binNum.length() == length) {
            return binNum;
        }

        return "0".repeat(length-binNum.length()) + binNum;
    }
}
