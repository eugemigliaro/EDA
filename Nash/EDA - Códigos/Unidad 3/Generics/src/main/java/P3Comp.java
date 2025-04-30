public class P3Comp<T extends Comparable<T>>{

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
        P3Comp<Integer> auxi = new P3Comp<>();
        auxi.initialize(5);
        auxi.setElement(3, 10);
        auxi.setElement(2, 20);
        for(int i = 0; i < 5; i++){
            System.out.println(auxi.getElement(i));
        }
    }
}
