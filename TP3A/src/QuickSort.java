public class QuickSort {
    public static void quickSort(int[] array){
        quickSort(array, array.length);
    }

    public static void quickSort(int[] array, int size){
        quickSortHelper(array, 0, size - 1);
    }

    private static void quickSortHelper(int[] array, int left, int right) {
        if(right <= left){
            return;
        }

        int pivotValue = array[left];

        swap(array, left, right);

        int pivotPosition = partition(array, left, right - 1, pivotValue);

        swap(array, pivotPosition, right);

        quickSortHelper(array, left, pivotPosition - 1);
        quickSortHelper(array, pivotPosition + 1, right);
    }

    private static void swap(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    private static int partition(int[] array, int left, int right, int pivotValue) {
        while(left <= right){
            while(left <= right && array[left] < pivotValue){
                left++;
            }

            while(left <= right && array[right] > pivotValue){
                right--;
            }

            if(left <= right){
                swap(array, left, right);
                left++;
                right--;
            }
        }
        return left;
    }
}
