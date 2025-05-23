package SortedLinkedList;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedLinkedListRecursive<T extends Comparable<? super T>> implements SortedListService<T> {
    private Node root;

    @Override
    public boolean insert(T data) {
        root = insertRec(data, root);
        return true;
    }

    private Node insertRec(T data, Node current){
        if(current == null || current.data.compareTo(data) > 0){
            return new Node(data, current);
        }

        if(current.data.compareTo(data) == 0){
            current.repetitions++;
            return current;
        }

        current.next = insertRec(data, current.next);
        return current;
    }

    @Override
    public boolean find(T data) {
        return findRec(data, root);
    }

    private boolean findRec(T data, Node current){
        if(current == null)
            return false;
        if(current.data.compareTo(data) == 0)
            return true;
        return findRec(data, current.next);
    }

    @Override
    public boolean remove(T data) {
        boolean[] removed = new boolean[1];
        root = removeRec(data, root, removed);
        return removed[0];
    }

    private Node removeRec(T data, Node current, boolean[] removed){
        if(current == null){
            removed[0] = false;
            return null;
        }

        if(current.data.compareTo(data) == 0){
            if(current.repetitions > 1){
                current.repetitions--;
                removed[0] = true;
                return current;
            }else{
                removed[0] = true;
                return current.next;
            }
        }

        current.next = removeRec(data, current.next, removed);
        return current;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
        return sizeRec(root);
    }

    private int sizeRec(Node current){
        if(current == null)
            return 0;
        return current.repetitions + sizeRec(current.next);
    }

    @Override
    public T getMin() {
        return root.data;
    }

    @Override
    public T getMax() {
        return getMaxRec(root);
    }

    private T getMaxRec(Node current){
        if(current.next == null)
            return current.data;
        return getMaxRec(current.next);
    }

    @Override
    public void dump() {
        dumpRec(root);
    }

    private void dumpRec(Node current) {
        if (current == null){
            root = null;
            return;
        }

        for(int i = 0; i < current.repetitions; i++)
            System.out.println("data: " + current.data + " repetitions: " + current.repetitions);
        dumpRec(current.next);
    }

    @Override
    public Iterator<T> iterator(){
        return new Iterator<T>() {
            private Node current = root;
            private boolean canRemove = false;
            private Node prev = null;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next(){
                if(!hasNext()){
                    throw new NoSuchElementException();
                }
                T data = current.data;
                prev = current;
                current = current.next;

                canRemove = true;

                return data;
            }


            public void remove(){
                if(!canRemove){
                    throw new IllegalStateException();
                }

                if(prev == null){
                    root = current.next;
                }else{
                    prev.next = current.next;
                }
                current = current.next;
                canRemove = false;
            }
        };
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
}
