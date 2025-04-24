import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 *
 * @author eugenio migliaro
 */
public class ClosedHashingTest {

    private ClosedHashing<Integer, String> instance;

    @BeforeEach
    public void setUp() {
        instance = new ClosedHashing<>(f -> f);
    }

    @Test
    @DisplayName("Test size method with empty and populated hash table")
    public void testSize() {
        assertEquals(0, instance.size(), "Size should be 0 for empty hash table");
        instance.insertOrUpdate(1, "uno");
        assertEquals(1, instance.size(), "Size should be 1 after inserting one element");
        instance.insertOrUpdate(2, "dos");
        assertEquals(2, instance.size(), "Size should be 2 after inserting two elements");

        // Test size after removing elements
        instance.remove(1);
        assertEquals(1, instance.size(), "Size should be 1 after removing one element");
        instance.remove(2);
        assertEquals(0, instance.size(), "Size should be 0 after removing all elements");
    }

    @Test
    @DisplayName("Test insertOrUpdate method for inserting and updating values")
    public void testInsertOrUpdate() {
        // Test inserting new elements
        instance.insertOrUpdate(1, "uno");
        assertEquals("uno", instance.find(1), "Should insert and retrieve value correctly");

        // Test updating existing elements
        instance.insertOrUpdate(1, "uno-modificado");
        assertEquals("uno-modificado", instance.find(1), "Should update existing value correctly");

        // Test multiple insertions
        instance.insertOrUpdate(2, "dos");
        assertEquals("dos", instance.find(2), "Should handle multiple insertions correctly");
        assertEquals("uno-modificado", instance.find(1), "Original value should remain unchanged");
    }

    @Test
    @DisplayName("Test insertOrUpdate with null values throws exception")
    public void testInsertOrUpdateWithNulls() {
        // Test null key
        Exception exceptionKey = assertThrows(IllegalArgumentException.class, () -> {
            instance.insertOrUpdate(null, "value");
        }, "Should throw IllegalArgumentException for null key");
        assertTrue(exceptionKey.getMessage().contains("Key cannot be null"), 
                "Exception message should mention null key");

        // Test null value
        Exception exceptionValue = assertThrows(IllegalArgumentException.class, () -> {
            instance.insertOrUpdate(1, null);
        }, "Should throw IllegalArgumentException for null value");
        assertTrue(exceptionValue.getMessage().contains("Data cannot be null"), 
                "Exception message should mention null data");
    }

    @Test
    @DisplayName("Test find method for existing and non-existing keys")
    public void testFind() {
        // Test finding in empty hash table
        assertNull(instance.find(1), "Should return null for non-existing key in empty table");

        // Test finding existing element
        instance.insertOrUpdate(1, "uno");
        assertEquals("uno", instance.find(1), "Should find existing element");

        // Test finding non-existing element in populated table
        assertNull(instance.find(2), "Should return null for non-existing key");

        // Test finding with null key
        assertNull(instance.find(null), "Should return null for null key");
    }

    @Test
    @DisplayName("Test remove method for existing and non-existing keys")
    public void testRemove() {
        // Test removing from empty hash table
        assertFalse(instance.remove(1), "Should return false when removing non-existing element");

        // Test removing existing element
        instance.insertOrUpdate(1, "uno");
        assertTrue(instance.remove(1), "Should return true when removing existing element");
        assertNull(instance.find(1), "Element should be removed after successful removal");

        // Test removing already removed element
        assertFalse(instance.remove(1), "Should return false when removing already removed element");

        // Test removing with null key
        assertFalse(instance.remove(null), "Should return false for null key");
    }

    @Test
    @DisplayName("Test collision handling in hash table")
    public void testCollisionHandling() {
        // Create hash function that always returns the same hash code to force collisions
        ClosedHashing<Integer, String> collisionInstance = new ClosedHashing<>(f -> 1);

        // Insert multiple elements that will collide
        collisionInstance.insertOrUpdate(1, "one");
        collisionInstance.insertOrUpdate(11, "eleven");
        collisionInstance.insertOrUpdate(21, "twenty-one");

        // Verify all elements can be retrieved correctly despite collisions
        assertEquals("one", collisionInstance.find(1), "Should find first element after collision");
        assertEquals("eleven", collisionInstance.find(11), "Should find second element after collision");
        assertEquals("twenty-one", collisionInstance.find(21), "Should find third element after collision");

        // Test removing middle element in collision chain
        assertTrue(collisionInstance.remove(11), "Should remove element in collision chain");
        assertEquals("one", collisionInstance.find(1), "First element should still be accessible after removing middle element");
        assertNull(collisionInstance.find(11), "Removed element should not be found");
        assertEquals("twenty-one", collisionInstance.find(21), "Last element should still be accessible after removing middle element");
    }

    @Test
    @DisplayName("Test resize functionality when load factor is exceeded")
    public void testResize() {
        // Insert enough elements to trigger resize (default initial size is 10, load factor is 0.75)
        for (int i = 0; i < 8; i++) {
            instance.insertOrUpdate(i, "value" + i);
        }

        // Verify all elements are still accessible after resize
        for (int i = 0; i < 8; i++) {
            assertEquals("value" + i, instance.find(i), "Element should be accessible after resize");
        }

        // Insert more elements to potentially trigger another resize
        for (int i = 8; i < 16; i++) {
            instance.insertOrUpdate(i, "value" + i);
        }

        // Verify all elements are still accessible
        for (int i = 0; i < 16; i++) {
            assertEquals("value" + i, instance.find(i), "Element should be accessible after multiple resizes");
        }
    }

    @Test
    @DisplayName("Test dump method outputs expected content")
    public void testDump() {
        // Redirect System.out to capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            // Test dump on empty hash table
            instance.dump();
            String emptyOutput = outContent.toString();
            assertTrue(emptyOutput.contains("empty"), "Dump of empty table should mention empty slots");

            // Reset output stream
            outContent.reset();

            // Test dump with elements
            instance.insertOrUpdate(1, "uno");
            instance.insertOrUpdate(2, "dos");
            instance.dump();

            String populatedOutput = outContent.toString();
            assertTrue(populatedOutput.contains("uno"), "Dump should contain inserted values");
            assertTrue(populatedOutput.contains("dos"), "Dump should contain inserted values");
        } finally {
            // Restore original System.out
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("Test custom hash function behavior")
    public void testCustomHashFunction() {
        // Create instance with custom hash function
        ClosedHashing<String, Integer> customInstance = new ClosedHashing<>(s -> s.length());

        // Insert elements with same hash (same length strings)
        customInstance.insertOrUpdate("abc", 3);
        customInstance.insertOrUpdate("def", 3);

        // Verify elements can be retrieved correctly
        assertEquals(3, customInstance.find("abc"), "Should retrieve correct value for first element");
        assertEquals(3, customInstance.find("def"), "Should retrieve correct value for second element");

        // Insert element with different hash
        customInstance.insertOrUpdate("a", 1);
        assertEquals(1, customInstance.find("a"), "Should retrieve correct value for element with different hash");
    }
}
