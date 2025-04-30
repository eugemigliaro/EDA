//Tiene complejidad NlogN
public class QuickSort {

    public static void quicksortHelper(int[] unsorted, int leftPos, int rightPos){
        if(rightPos <= leftPos)
            return;

        //Tomamos como pivot el primero
        int pivotValue = unsorted[leftPos];

        //Excluimos el pivot del conjunto
        swap(unsorted, leftPos, rightPos);

        //Particionamos el conjunto sin pivot
        int pivotPosCalculated = partition(unsorted, leftPos, rightPos - 1, pivotValue);

        //Ponemos el pivot en el lugar correcto
        swap(unsorted, pivotPosCalculated, rightPos);

        quicksortHelper(unsorted, leftPos, pivotPosCalculated - 1);
        quicksortHelper(unsorted, pivotPosCalculated + 1, rightPos);
    }

    static private void swap(int[] unsorted, int pos1, int pos2){
        int aux = unsorted[pos1];
        unsorted[pos1] = unsorted[pos2];
        unsorted[pos2] = aux;
    }

    static private int partition(int[] unsorted, int leftVal, int rightVal, int pivotValue){
        int l = leftVal;
        int r = rightVal;

        while(l <= r){
            if(unsorted[l] < pivotValue){
                l++;
            }
            else{
                if(unsorted[r] > pivotValue){
                    r--;
                }
                else{
                    swap(unsorted, l, r);
                }
            }
        }

        return l;
    }
}
