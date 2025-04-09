import java.util.Arrays;

public class IndexWithDuplicates implements IndexService {

    private final static int CHUNK_SIZE = 10;
    private int[] elements;
    private int currentSize;

    public IndexWithDuplicates() {
        elements = new int[CHUNK_SIZE];
        currentSize = 0;
    }

    @Override
    public void initialize(int[] elements) throws RuntimeException {
        if (elements == null) {
            throw new RuntimeException("elements is null");
        }

        this.elements = new int[elements.length];

        for (int i = 0; i < elements.length; i++) {
            insert(elements[i]);
        }
    }

    @Override
    public boolean search(int key) {
        int pos = getClosestPosition(key) - 1;

        return pos >= 0 && elements[pos] == key;
    }

    @Override
    public void insert(int key) {
        if (currentSize >= elements.length) {
            elements = Arrays.copyOf(elements, elements.length + CHUNK_SIZE);
        }

        int pos = getClosestPosition(key);

        for (int i = currentSize; i > pos; i--) {
            elements[i] = elements[i - 1];
        }
        elements[pos] = key;
        currentSize++;

        printIndex();
    }

    @Override
    public void delete(int key) {
        int pos = getClosestPosition(key) - 1;

        if(pos >= 0 && elements[pos] == key) {
            for (int i = pos; i < currentSize - 1; i++) {
                elements[i] = elements[i + 1];
            }
            currentSize--;
        }
    }

    @Override
    public int occurrences(int key) {
        int pos = getClosestPosition(key) - 1;

        if(pos >= 0 && elements[pos] == key) {
            int count = 1;
            for (int i = pos - 1; i >= 0 && elements[i] == key; i--) {
                count++;
            }
            return count;
        }
        return 0;
    }

    /* Returns the position where the key should be inserted, which is the first position greater than or equal to the key */
    private int getClosestPosition(int key) {
        int low = 0;
        int high = currentSize - 1;
        int mid;

        while (low <= high) {
            mid = (low + high) / 2;
            if (elements[mid] <= key) {
                low = mid + 1;
            } else if (elements[mid] > key) {
                high = mid - 1;
            }
        }
        return low; // Position where the key should be inserted
    }

    private void printIndex() {
        System.out.println("Index: [ ");
        for (int i = 0; i < currentSize; i++) {
            System.out.print(elements[i] + " ");
        }
        System.out.println("]");
    }

    @Override
    public void sortedPrint() {
        printIndex();
    }

    @Override
    public int getMax(){
        return elements[currentSize - 1];
    }

    @Override
    public int getMin(){
        return elements[0];
    }

    public int[] range(int leftKey, int rightKey, boolean leftIncluded, boolean rightIncluded) {
        int leftPos = leftIncluded ? getClosestPosition(leftKey - 1) : getClosestPosition(leftKey);
        int rightPos = rightIncluded ? getClosestPosition(rightKey) - 1 : getClosestPosition(rightKey - 1) - 1;

        if(leftPos > rightPos){
            return new int[0];
        }
        int[] range = new int[rightPos - leftPos + 1];
        for (int i = 0; i < range.length; i++) {
            range[i] = elements[leftPos + i];
        }

        return range;
    }
}
