import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author eugenio migliaro
 */
public class ClosedHashingTest {

    @Test
    public void testSize() {
        ClosedHashing<Integer, String> instance = new ClosedHashing<>(f -> f);
        assertEquals(0, instance.size());
        instance.insertOrUpdate(1, "uno");
        assertEquals(1, instance.size());
        instance.insertOrUpdate(2, "dos");
        assertEquals(2, instance.size());
    }

    @Test
    public void testInsertOrUpdate() {
        ClosedHashing<Integer, String> instance = new ClosedHashing<>(f -> f);
        instance.insertOrUpdate(1, "uno");
        assertEquals("uno", instance.find(1));
        instance.insertOrUpdate(1, "uno-modificado");
        assertEquals("uno-modificado", instance.find(1));
        instance.insertOrUpdate(2, "dos");
        assertEquals("dos", instance.find(2));
    }

    @Test
    public void testFind() {
        ClosedHashing<Integer, String> instance = new ClosedHashing<>(f -> f);
        instance.insertOrUpdate(1, "uno");
        assertEquals("uno", instance.find(1));
        assertNull(instance.find(2));
    }

    @Test
    public void testRemove() {
        ClosedHashing<Integer, String> instance = new ClosedHashing<>(f -> f);
        assertFalse(instance.remove(1));
        instance.insertOrUpdate(1, "uno");
        assertTrue(instance.remove(1));
        assertFalse(instance.remove(1));
    }

    @Test
    public void testDump() {
        ClosedHashing<Integer, String> instance = new ClosedHashing<>(f -> f);
        instance.insertOrUpdate(1, "uno");
        instance.insertOrUpdate(2, "dos");
        instance.dump();
    }

}