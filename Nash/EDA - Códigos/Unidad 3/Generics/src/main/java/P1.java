public class P1<E>{

    private Object[] arreglo;

    public void initialize(int dim){
        arreglo = new Object[dim];
    }

    public void setElement(int pos, E element){
        arreglo[pos] = element;
    }

    @SuppressWarnings("unchecked")
    public E getElement(int pos){
        return (E) arreglo[pos];
    }

    public static void main(String[] args) {
        P1<Number> auxi = new P1<>();
        auxi.initialize(5);
        auxi.setElement(3, 10);
        auxi.setElement(2, 20.8);
        for(int i = 0; i < 5; i++){
            System.out.println(auxi.getElement(i));
        }
    }
}
