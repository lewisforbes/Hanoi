import java.util.ArrayList;
import java.util.Objects;

public class Tower {

    // stack is in decreasing order (stack.get(0) is bottom disk)
    private ArrayList<Integer> stack = new ArrayList<>();

    public boolean addToStack(int diskToAdd) {
        if (stack.size() == 0) {
            stack.add(diskToAdd);
            return true;
        }

        int topDisk = stack.get(stack.size()-1);

        if (topDisk > diskToAdd) {
            stack.add(diskToAdd);
            return true;
        }

        if (topDisk < diskToAdd) {
            System.err.print("Unable to place disk " + diskToAdd + " on disk " + stack.get(0));
            return false;
        }

        // only reached if diskToAdd==topDisk
        throw new IllegalArgumentException("Tried to add disk on top of one with the same size.");
    }

    public void removeFromStack() {
        if ((stack.size() == 0) || (stack == null)) {
            throw new NullPointerException("Stack is either null or of size 0");
        }
        stack.remove(stack.size()-1);
    }

    public ArrayList<Integer> getStack() {
        return stack;
    }

    public int getTopDisk() {
        return stack.get(stack.size()-1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tower tower = (Tower) o;
        return Objects.equals(stack, tower.stack);
    }
}