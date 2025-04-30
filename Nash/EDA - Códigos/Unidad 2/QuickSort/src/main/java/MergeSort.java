import java.util.Arrays;

//Tiene complejidad NlogN
public class MergeSort {

    public static void sort(int[] array) {
        if(array == null)
            throw new NullPointerException();
        sort(array, array.length);
    }

    public static void sort(int[] array, int n) {
        if(array == null)
            throw new NullPointerException();
        sort(array, 0, n-1);
    }

    private static void merge(int[] array, int leftPos, int mid, int rightPos){
        int[] sorted = new int[rightPos - leftPos + 1];
        int itLeft = leftPos, itMid = mid+1, it = 0;
        while(itLeft <= mid && itMid <= rightPos) {
            if(array[itLeft] <= array[itMid])
                sorted[it] = array[itLeft++];
            else
                sorted[it] = array[itMid++];
            it++;
        }
        while(itLeft <= mid)
            sorted[it++] = array[itLeft++];
        while(itMid <= rightPos)
            sorted[it++] = array[itMid++];
        for(itLeft = leftPos, it = 0; itLeft <= rightPos; itLeft++, it++)
            array[itLeft] = sorted[it];
    }

    private static void sort(int[] array, int leftPos, int rightPos) {
        if(rightPos <= leftPos)
            return;
        int mid = (leftPos + rightPos)/2;
        sort(array, leftPos, mid);
        sort(array, mid+1, rightPos);
        merge(array, leftPos, mid, rightPos);
    }

    public static void main(String[] args) {
        int[] array = {34, 10, 8, 60, 21, 17, 28, 30, 2, 70, 50, 15, 62, 42, -78};
        MergeSort.sort(array);
        System.out.println(Arrays.toString(array));
    }
}
