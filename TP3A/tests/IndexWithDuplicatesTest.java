import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IndexWithDuplicatesTest {

    private IndexWithDuplicates index;

    @BeforeEach
    public void setUp() {
        index = new IndexWithDuplicates();
    }

    @Test
    public void testInitialize() {
        int[] elements = {1, 2, 3, 4, 5, 3};
        index.initialize(elements);
        assertEquals(2, index.occurrences(3));
        assertEquals(1, index.occurrences(1));
        assertEquals(0, index.occurrences(6));
    }

    @Test
    public void testInsert() {
        index.insert(5);
        index.insert(2);
        index.insert(2);
        assertEquals(2, index.occurrences(2));
        assertEquals(1, index.occurrences(5));
    }

    @Test
    public void testDelete() {
        index.insert(3);
        index.insert(3);
        index.delete(3);
        assertEquals(1, index.occurrences(3));
        index.delete(3);
        assertEquals(0, index.occurrences(3));
    }

    @Test
    public void testSearch() {
        index.insert(7);
        assertTrue(index.search(7));
        assertFalse(index.search(5));
    }

    @Test
    public void testOccurrences() {
        index.insert(4);
        index.insert(4);
        index.insert(4);
        assertEquals(3, index.occurrences(4));
        assertEquals(0, index.occurrences(5));
    }


    @Test
    public void testRange() {
        index.insert(1);
        index.insert(2);
        index.insert(3);
        index.insert(3);
        index.insert(5);
        int[] result = index.range(2, 4, true, true);
        assertArrayEquals(new int[]{2, 3, 3}, result);
        result = index.range(2, 4, false, true);
        assertArrayEquals(new int[]{3, 3}, result);
        result = index.range(2, 4, true, false);
        assertArrayEquals(new int[]{2, 3, 3}, result);
        result = index.range(2, 4, false, false);
        assertArrayEquals(new int[]{3, 3}, result);
    }
}