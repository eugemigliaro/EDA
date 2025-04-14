import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoundedQueueTest {

    @Test
    public void testBoundedQueue() {
        BoundedQueue<Integer> queue = new BoundedQueue<>(3);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertEquals(1, queue.dequeue());
        queue.enqueue(4);
        assertEquals(2, queue.dequeue());
        queue.enqueue(5);
        Integer[] array = queue.getArray(Integer.class);
        assertEquals(3, array.length);
        for(int i = 0; i < array.length; i++) {
            System.out.println("array[" + i + "] = " + array[i]);
        }

        assertThrows(IllegalStateException.class, () -> queue.enqueue(6));

        assertEquals(3, queue.dequeue());
        assertEquals(4, queue.dequeue());
        assertEquals(5, queue.dequeue());

        assertThrows(IllegalStateException.class, () -> queue.dequeue());
        System.out.println("ok");
    }
}
