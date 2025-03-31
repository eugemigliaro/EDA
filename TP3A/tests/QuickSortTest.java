import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTest {

    @Test
    void testQuickSort() {
        int[] array = {6, 4, 5, 8, 2};
        QuickSort.quickSort(array);
        int[] expected = {2, 4, 5, 6, 8};
        assertArrayEquals(expected, array);
    }

    @Test
    void testQuickSortWithDuplicates() {
        int[] array = {6, 4, 5, 8, 2, 4};
        QuickSort.quickSort(array);
        int[] expected = {2, 4, 4, 5, 6, 8};
        assertArrayEquals(expected, array);
    }

    @Test
    void testQuickSortEmpty() {
        int[] array = {};
        QuickSort.quickSort(array);
        int[] expected = {};
        assertArrayEquals(expected, array);
    }

    @Test
    void testQuickSortOneElement() {
        int[] array = {1};
        QuickSort.quickSort(array);
        int[] expected = {1};
        assertArrayEquals(expected, array);
    }


    @Test
    void testQuickSortLong() {
        int[] array = new int[1000];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 1000);
        }
        System.out.println("Before: " + Arrays.toString(array));
        QuickSort.quickSort(array);
        System.out.println("After: " + Arrays.toString(array));
        for (int i = 0; i < array.length - 1; i++) {
            assertTrue(array[i] <= array[i + 1]);
        }
    }


    @Test
    void testBubbleSortLong() {
        int[] array = new int[1000];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 1000);
        }
        System.out.println("Before: " + Arrays.toString(array));
        long start = System.nanoTime();
        BubbleSort.bubbleSort(array);
        long finish = System.nanoTime();
        System.out.println("After: " + Arrays.toString(array));
        long timeElapsed = finish - start;
        System.out.println("bubbleSort took " + timeElapsed + " nanoseconds");
        for (int i = 0; i < array.length - 1; i++) {
            assertTrue(array[i] <= array[i + 1]);
        }
    }
}
