import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class IndexGenericsTest {

    @Test
    public void testSearch() {
        IndexGenerics<Integer> index = new IndexGenerics<>();
        index.insert(4);
        index.insert(5);
        index.insert(6);
        assertTrue(index.search(5));
        assertFalse(index.search(7));
    }

    @Test
    public void testOccurrences() {
        IndexGenerics<Integer> index = new IndexGenerics<>();
        index.insert(3);
        index.insert(3);
        index.insert(3);
        assertEquals(3, index.occurrences(3));
        assertEquals(0, index.occurrences(5));
    }

    @Test
    public void testDelete() {
        IndexGenerics<Integer> index = new IndexGenerics<>();
        index.insert(3);
        index.insert(3);
        index.insert(3);
        index.delete(3);
        assertEquals(2, index.occurrences(3));
        index.delete(3);
        assertEquals(1, index.occurrences(3));
        index.delete(3);
        assertEquals(0, index.occurrences(3));
    }

    @Test
    public void testRange() {
        IndexGenerics<Integer> index = new IndexGenerics<>();
        index.insert(1);
        index.insert(2);
        index.insert(3);
        index.insert(3);
        index.insert(5);
        Integer[] result = index.range(2, 4, true, true);
        assertArrayEquals(new Integer[]{2, 3, 3}, result);
        result = index.range(2, 4, false, true);
        assertArrayEquals(new Integer[]{3, 3}, result);
        result = index.range(2, 4, true, false);
        assertArrayEquals(new Integer[]{2, 3, 3}, result);
        result = index.range(2, 4, false, false);
        assertArrayEquals(new Integer[]{3, 3}, result);
    }
}