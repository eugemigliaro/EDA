package SortedLinkedList;

import java.util.Iterator;

public class SortedLinkedListDelegated<T extends Comparable<T>> implements SortedListService<T> {
    private Node root;

    public SortedLinkedListDelegated() {
        this.root = null;
    }

    @Override
    public boolean insert(T data) {
        if(root == null){
            root = new Node(data);
            return true;
        }
        root = root.insert(data);
        return true;
    }

    @Override
    public boolean remove(T data) {
        if(root == null)
            return false;
        if(root.data.compareTo(data) == 0){
            root = root.next;
            return true;
        }
        return root.remove(data);
    }

    @Override
    public boolean find(T data) {
        return root == null ? false : root.find(data);
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
        return root == null ? 0 : root.size();
    }

    @Override
    public T getMin() {
        return root == null ? null : root.data;
    }

    @Override
    public T getMax() {
        return root == null ? null : root.getMax();
    }

    @Override
    public void dump() {
        if (root != null)
            root.dump();

        root = null;
    }

    @Override
    public Iterator<T> iterator(){
        return new Iterator<T>() {
            private Node current = root;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next(){
                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    private class Node {
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

        private Node insert(T data) {
            if(this.data.compareTo(data) > 0) {
                return new Node(data, this);
            }else if(this.data.compareTo(data) == 0){
                this.repetitions++;
                return this;
            }
            if(next == null) {
                next = new Node(data);
                return this;
            }
            this.next = this.next.insert(data);
            return this;
        }

        private boolean remove(T data) {
            if(next == null || this.data.compareTo(data) > 0){
                return false;
            }
            if(this.next.data.compareTo(data) == 0){
                if(this.next.repetitions > 1){
                    this.next.repetitions--;
                    return true;
                }else{
                    this.next = this.next.next;
                    return true;
                }
            }

            return next.remove(data);
        }

        private boolean find(T data) {
            if(this.data.compareTo(data) > 0){
                return false;
            }
            if(this.data.compareTo(data) == 0){
                return true;
            }
            if(next == null){
                return false;
            }
            return next.find(data);
        }

        private int size() {
            if(next == null){
                return repetitions;
            }
            return repetitions + next.size();
        }

        private T getMax(){
            if(next == null){
                return data;
            }
            return next.getMax();
        }

        private void dump(){
            for(int i = 0; i < repetitions; i++)
                System.out.println("data: " + data + " repetitions: " + repetitions);

            if(next == null){
                return;
            }
            next.dump();
        }
    }
}
