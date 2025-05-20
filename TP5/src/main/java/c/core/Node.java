package c.core;

public class Node<T extends Comparable<? super T>> implements NodeTreeInterface<T> {
    private T data;
    private Node<T> left, right;

    public Node(T data) {
        this.data = data;
    }

    public void insert(T value) {
        int cmp = value.compareTo(data);
        if (cmp <= 0) {
            if (left == null) left = new Node<>(value);
            else left.insert(value);
        } else {
            if (right == null) right = new Node<>(value);
            else right.insert(value);
        }
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public Node<T> getLeft() {
        return left;
    }

    @Override
    public Node<T> getRight() {
        return right;
    }

    // Additional setters
    public void setData(T data) {
        this.data = data;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }
}