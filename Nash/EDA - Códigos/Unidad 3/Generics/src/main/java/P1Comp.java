public class P1Comp<E extends Comparable<E>>{
    //Funciona perfecto a pesar de que E extiende comparable
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
        P1Comp<Integer> auxi = new P1Comp<>();
        auxi.initialize(5);
        auxi.setElement(3, 10);
        auxi.setElement(2, 20);
        for(int i = 0; i < 5; i++){
            System.out.println(auxi.getElement(i));
        }
    }
}
