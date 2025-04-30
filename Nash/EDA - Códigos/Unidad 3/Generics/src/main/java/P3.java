public class P3<T>{

    private T[] arreglo;

    @SuppressWarnings("unchecked")
    public void initialize(int dim){
        arreglo = (T[]) new Object[dim];
    }

    public void setElement(int pos, T element){
        arreglo[pos] = element;
    }

    public T getElement(int pos){
        return arreglo[pos];
    }

    public static void main(String[] args) {
        P3<Number> auxi = new P3<>();
        auxi.initialize(5);
        auxi.setElement(3, 10);
        auxi.setElement(2, 20.8);
        for(int i = 0; i < 5; i++){
            System.out.println(auxi.getElement(i));
        }
    }
}

