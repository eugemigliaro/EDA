import java.lang.reflect.Array;
import java.util.Arrays;

public class BoundedQueue<T> {
    private final int MAX;  // or whatever maximum size you want
    private T[] queue;
    private int head;    // index of the first element
    private int tail;    // index where the next element will be inserted
    private int size;    // current number of elements
    
    @SuppressWarnings("unchecked")
    public BoundedQueue(int max) {
        queue = (T[]) new Object[max];
        MAX = max;
        head = 0;
        tail = 0;
        size = 0;
    }
    
    public void enqueue(T item) {
        if (size >= MAX) {
            throw new IllegalStateException("Queue is full");
        }
        
        queue[tail] = item;
        tail = (tail + 1) % MAX;  // wrap around if we reach the end
        size++;
    }
    
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        
        T item = queue[head];
        queue[head] = null;  // help GC
        head = (head + 1) % MAX;  // wrap around if we reach the end
        size--;
        return item;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public void dump() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return;
        }
        
        int current = head;
        for(int i = 0; i < size; i++){
            System.out.print(queue[current] + " ");
            current = (current + 1) % MAX;
        }
        System.out.println();
    }

    public T[] getArray(Class c) {
        T[] ans = (T[]) Array.newInstance(c, size);
        if (size >= 0) System.arraycopy(queue, 0, ans, 0, size);
        return ans;
    }
}