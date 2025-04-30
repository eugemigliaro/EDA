import java.lang.reflect.Array;

public class P2<T>{

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
        P2<Number> auxi = new P2<>();
        auxi.initialize(5, Number.class);
        auxi.setElement(3, 10);
        auxi.setElement(2, 20.8);
        for(int i = 0; i < 5; i++){
            System.out.println(auxi.getElement(i));
        }
    }
}
