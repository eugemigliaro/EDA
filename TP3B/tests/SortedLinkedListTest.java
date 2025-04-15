import SortedLinkedList.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SortedLinkedListTest {

    @Test
    public void testSortedLinkedListIterative() {
        SortedListService<String> instance = new SortedLinkedListIterative<>();
        assertTrue(instance.isEmpty());
        assertEquals(0, instance.size());

        instance.insert("b");
        instance.insert("a");
        instance.insert("c");
        assertEquals(3, instance.size());
        assertFalse(instance.isEmpty());

        assertEquals("a", instance.getMin());
        assertEquals("c", instance.getMax());
        assertTrue(instance.find("b"));
        assertTrue(instance.find("a"));
        assertTrue(instance.find("c"));
        assertFalse(instance.find("d"));

        assertTrue(instance.remove("b"));
        assertEquals(2, instance.size());
        assertFalse(instance.remove("d"));

        System.out.println("Iterative");
        instance.dump();

        SortedListService<String> instance2 = new SortedLinkedListDelegated<>();
        instance2.insert("b");
        instance2.insert("a");
        instance2.insert("c");
        assertEquals(3, instance2.size());
        assertFalse(instance2.isEmpty());

        assertEquals("a", instance2.getMin());
        assertEquals("c", instance2.getMax());
        assertTrue(instance2.find("b"));
        assertTrue(instance2.find("a"));
        assertTrue(instance2.find("c"));
        assertFalse(instance2.find("d"));

        assertTrue(instance2.remove("b"));
        assertEquals(2, instance2.size());
        assertFalse(instance2.remove("d"));

        System.out.println("Delegated");
        instance2.dump();

        SortedListService<String> instance3 = new SortedLinkedListRecursive<>();
        instance3.insert("b");
        instance3.insert("a");
        instance3.insert("c");
        assertEquals(3, instance3.size());
        assertFalse(instance3.isEmpty());

        assertEquals("a", instance3.getMin());
        assertEquals("c", instance3.getMax());
        assertTrue(instance3.find("b"));
        assertTrue(instance3.find("a"));
        assertTrue(instance3.find("c"));
        assertFalse(instance3.find("d"));

        assertTrue(instance3.remove("b"));
        assertEquals(2, instance3.size());
        assertFalse(instance3.remove("d"));

        System.out.println("Recursive");
        instance3.dump();

        System.out.println("ok let's fucking go");
    }
}
