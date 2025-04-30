import java.lang.reflect.Array;

public class P2Comp<T extends Comparable<T>>{

    private T[] arreglo;

    @SuppressWarnings("unchecked")
    public void initialize(int dim, Class<T> theClass){
        arreglo = (T[]) Array.newInstance(theClass, dim);
    }

    public void setElement(int pos, T element){
        arreglo[pos] = element;
    }

    public T getElement(int pos){
        return arreglo[pos];
    }

    public static void main(String[] args) {
        P2Comp<Integer> auxi = new P2Comp<>();
        auxi.initialize(5, Integer.class);
        auxi.setElement(3, 10);
        auxi.setElement(2, 20);
        for(int i = 0; i < 5; i++){
            System.out.println(auxi.getElement(i));
        }
    }
}
