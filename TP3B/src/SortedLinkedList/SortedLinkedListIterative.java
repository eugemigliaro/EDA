package SortedLinkedList;

// lista simplemente encadenada, no acepta repetidos (false e ignora) ni nulls (exception)
public class SortedLinkedListIterative<T extends Comparable<? super T>> implements SortedListService<T> {
    private Node root;
    // insert resuelto todo en la clase SortedLinkedList, iterativo
    @Override
    public boolean insert(T data) {
        if (data == null)
            throw new IllegalArgumentException("data cannot be null");
        Node prev= null;
        Node current = root;
        while (current != null && current.data.compareTo(data) < 0) {
            // avanzo
            prev = current;
            current = current.next;
        }
        // repetido?
        if (current != null && current.data.compareTo(data) == 0) {
            current.repetitions++;
            return true;
        }
        Node aux = new Node(data, current);
        // es el lugar para colocarlo
        if(current == root){
        // el primero es un caso especial: cambia root
            root = aux;
        }else{
        // nodo interno
            prev.next = aux;
        }
        return true;
    }
    @Override
    public boolean find(T data) {
        Node current = root;
        while (current != null && current.data.compareTo(data) < 0) {
            // avanzo
            current = current.next;
        }
        return current != null && current.data.compareTo(data) == 0;
    }
    @Override
    public boolean remove(T data) {
        Node prev = null;
        Node current = root;
        while(current != null && current.data.compareTo(data) < 0) {
            // avanzo
            prev = current;
            current = current.next;
        }
        if(current != null && current.data.compareTo(data) == 0) {
            if (current.repetitions > 1) {
                current.repetitions--;
            }else{
                if (prev == null) {
                    root = current.next;
                }else{
                    prev.next = current.next;
                }
            }
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean isEmpty() {
        return root == null;
    }
    @Override
    public int size() {
        Node current = root;
        int count = 0;
        while(current != null){
            count += current.repetitions;
            current = current.next;
        }

        return count;
    }
    @Override
    public T getMin() {
        return root.data;
    }
    @Override
    public T getMax() {
        Node current = root;
        while (current.next != null) {
            // avanzo
            current = current.next;
        }

        return current.data;
    }
    @Override
    public void dump() {
        Node current = root;
        while (current != null ) {
            // avanzo
            System.out.println("data: " + current.data + " repetitions: " + current.repetitions);
            current = current.next;
        }
    }
    // clase auxiliary
    private final class Node {
        private T data;
        private Node next;
        private int repetitions;
        private Node(T data) {
            this.data = data;
            this.repetitions = 1;
        }
        private Node(T data, Node next) {
            this.data = data;
            this.next = next;
            this.repetitions = 1;
        }
    } // end Node
} // end SortedLinkedList