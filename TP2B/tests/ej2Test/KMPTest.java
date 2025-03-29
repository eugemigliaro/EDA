package ej2Test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ej2.KMP;

import java.util.ArrayList;

public class KMPTest {

    @Test
    public void testNextComputation() {
        char[] query = "ABABAC".toCharArray();
        int[] expectedNext = {0, 0, 1, 2, 3, 0};
        assertArrayEquals(expectedNext, KMP.nextComputation(query));
    }

    @Test
    public void testIndexOfFound() {
        char[] query = "AB".toCharArray();
        char[] target = "ABABABAC".toCharArray();
        int expectedIndex = 0;
        assertEquals(expectedIndex, KMP.indexOf(query, target));
    }

    @Test
    public void testIndexOfNotFound() {
        char[] query = "AC".toCharArray();
        char[] target = "ABABABAB".toCharArray();
        int expectedIndex = -1;
        assertEquals(expectedIndex, KMP.indexOf(query, target));
    }

    @Test
    public void testIndexOfPartialMatch() {
        char[] query = "ABAC".toCharArray();
        char[] target = "ABABABAC".toCharArray();
        int expectedIndex = 4;
        assertEquals(expectedIndex, KMP.indexOf(query, target));
    }

    @Test
    public void testFindAll() {
        char[] query = "ABA".toCharArray();
        char[] target = "ABABABAC".toCharArray();
        ArrayList<Integer> expectedIndex = new ArrayList<>();
        expectedIndex.add(0);
        expectedIndex.add(2);
        expectedIndex.add(4);
        assertEquals(expectedIndex, KMP.findAll(query, target));

        char[] query2 = "AA".toCharArray();
        char[] target2 = "AAAAAAA".toCharArray();
        ArrayList<Integer> expectedIndex2 = new ArrayList<>();
        expectedIndex2.add(0);
        expectedIndex2.add(1);
        expectedIndex2.add(2);
        expectedIndex2.add(3);
        expectedIndex2.add(4);
        expectedIndex2.add(5);
        assertEquals(expectedIndex2, KMP.findAll(query2, target2));
    }
}