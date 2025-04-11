import java.util.Arrays;
import java.lang.reflect.Array;

public class IndexGenerics<T extends Comparable<? super T>> implements IndexParametricService<T> {
    private final static int CHUNK_SIZE = 10;
    private T[] elements;
    private int currentSize;

    public IndexGenerics() {
        elements = (T[]) new Comparable[CHUNK_SIZE];
        currentSize = 0;
    }

    @Override
    public void initialize(T[] elements) throws RuntimeException {
        if (elements == null) {
            throw new RuntimeException("elements is null");
        }

        this.elements = (T[]) new Comparable[elements.length];
        for(int i = 0; i < elements.length; i++) {
            insert(elements[i]);
        }
    }

    @Override
    public boolean search(T key) {
        int pos = getClosestRightPosition(key) - 1;

        return pos >= 0 && elements[pos].equals(key);
    }

    @Override
    public void insert(T key) {
        if (currentSize >= elements.length) {
            elements = (T[]) Arrays.copyOf(elements, elements.length + CHUNK_SIZE);
        }

        int pos = getClosestRightPosition(key);

        for (int i = currentSize; i > pos; i--) {
            elements[i] = elements[i - 1];
        }
        elements[pos] = key;
        currentSize++;
    }

    @Override
    public void delete(T key) {
        int pos = getClosestRightPosition(key) - 1;

        if(pos >= 0 && elements[pos].equals(key)) {
            for (int i = pos; i < currentSize - 1; i++) {
                elements[i] = elements[i + 1];
            }
            currentSize--;
        }
    }

    @Override
    public int occurrences(T key) {
        int pos = getClosestRightPosition(key) - 1;

        int count = 0;
        while(pos >= 0 && elements[pos].equals(key)) {
            count++;
            pos--;
        }

        return count;
    }

    private int getClosestRightPosition(T key) {
        int low = 0;
        int high = currentSize - 1;
        int mid;

        while (low <= high) {
            mid = (low + high) / 2;
            if(elements[mid].compareTo(key) <= 0){
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return low;
    }

    private int getClosestLeftPosition(T key) {
        int low = 0;
        int high = currentSize - 1;
        int mid;

        while(low <= high){
            mid = (low + high) / 2;
            if(elements[mid].compareTo(key) >= 0){
                high = mid - 1;
            }else{
                low = mid + 1;
            }
        }

        return high;
    }

    @Override
    public String toString(){
        String ans = "[";
        for(int i = 0; i < currentSize; i++){
            ans += elements[i];
            if(i < currentSize - 1){
                ans += "; ";
            }
        }

        ans += "]";

        return ans;
    }

    @Override
    public void sortedPrint() {
        System.out.println(this);
    }

    @Override
    public T getMax(){
        return elements[currentSize - 1];
    }

    @Override
    public T getMin(){
        return elements[0];
    }

    @SuppressWarnings("unchecked")
    public T[] range(T leftKey, T rightKey, boolean leftIncluded, boolean rightIncluded) {
        int leftPos = leftIncluded ? getClosestLeftPosition(leftKey) + 1 : getClosestRightPosition(leftKey);
        int rightPos = rightIncluded ? getClosestRightPosition(rightKey) - 1 : getClosestLeftPosition(rightKey);

        if(leftPos > rightPos || currentSize == 0){
            return (T[]) Array.newInstance(leftKey.getClass(), 0);
        }
        T[] range = (T[]) Array.newInstance(leftKey.getClass(), rightPos - leftPos + 1);
        for(int i = 0; i < range.length; i++){
            range[i] = elements[leftPos + i];
        }

        return range;
    }
}