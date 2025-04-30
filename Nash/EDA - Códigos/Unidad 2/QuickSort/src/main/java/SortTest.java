import java.util.Arrays;

public class SortTest {
    public static void main(String[] args) {

        int[] arr = {1, 2, 3, 4, 5, 6};
        QuickSort.quicksortHelper(arr, 0, 5);
        System.out.println(Arrays.toString(arr));
    }
}
