package c.core;

import java.util.*;

public class BST<T extends Comparable<? super T>> implements BSTreeInterface<T> {

    private Node<T> root;

    private Traversal traversal = Traversal.BYLEVELS;

    @Override
    public void insert(T value) {
        if (root == null) root = new Node<>(value);
        else root.insert(value);
    }

    public void insertIt(T value) {
        if(root == null) {
            root = new Node<>(value);
        }else{
            Node<T> node = root;
            while(true){
                int cmp = value.compareTo(node.getData());
                if(cmp <= 0){
                    if(node.getLeft() == null){
                        node.setLeft(new Node<>(value));
                        break;
                    }else{
                        node = node.getLeft();
                    }
                }else{
                    if(node.getRight() == null){
                        node.setRight(new Node<>(value));
                        break;
                    }else{
                        node = node.getRight();
                    }
                }
            }
        }
    }

    @Override
    public NodeTreeInterface<T> getRoot() {
        return root;
    }

    @Override
    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(Node<T> node) {
        if (node == null) return -1;
        return 1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight()));
    }

    @Override
    public void preOrder() {
        preOrder(root);
        System.out.println();
    }

    private void preOrder(Node<T> node) {
        if (node == null) return;
        System.out.print(node.getData() + " ");
        preOrder(node.getLeft());
        preOrder(node.getRight());
    }

    @Override
    public void inOrder() {
        inOrder(root);
        System.out.println();
    }

    private void inOrder(Node<T> node) {
        if (node == null) return;
        inOrder(node.getLeft());
        System.out.print(node.getData() + " ");
        inOrder(node.getRight());
    }

    @Override
    public void postOrder() {
        postOrder(root);
        System.out.println();
    }

    private void postOrder(Node<T> node) {
        if (node == null) return;
        postOrder(node.getLeft());
        postOrder(node.getRight());
        System.out.print(node.getData() + " ");
    }

    @Override
    public boolean contains(T value) {
        if(value == null){
            return false;
        }
        return contains(value, root);
    }

    private boolean contains(T value, Node<T> node) {
        if (node == null) return false;
        int cmp = value.compareTo(node.getData());
        if (cmp < 0) return contains(value, node.getLeft());
        if (cmp > 0) return contains(value, node.getRight());
        // cmp == 0
        return true;
    }

    @Override
    public T getMin() {
        if(root == null){
            return null;
        }
        Node<T> node = root;
        while(node.getLeft() != null){
            node = node.getLeft();
        }
        return node.getData();
    }

    @Override
    public T getMax() {
        if(root == null){
            return null;
        }
        Node<T> node = root;
        while(node.getRight() != null){
            node = node.getRight();
        }
        return node.getData();
    }

    @Override
    public void printByLevels(){
        if(root == null){
            return;
        }

        Queue<Node<T>> q = new LinkedList<>();
        q.add(root);
        Node<T> node;
        while(!q.isEmpty()){
            node = q.remove();
            System.out.print(node.getData() + " ");
            if(node.getLeft() != null){
                q.add(node.getLeft());
            }
            if(node.getRight() != null){
                q.add(node.getRight());
            }
        }
        System.out.println();
    }

    @Override
    public void delete(T value) {
        root = delete(root, value);
    }

    private Node<T> delete(Node<T> node, T value) {
        if (node == null) return null;

        int cmp = value.compareTo(node.getData());

        if (cmp < 0) {
            node.setLeft(delete(node.getLeft(), value));
        } else if (cmp > 0) {
            node.setRight(delete(node.getRight(), value));
        } else {
            // caso 1 y 2
            if (node.getLeft() == null) return node.getRight();
            if (node.getRight() == null) return node.getLeft();

            // caso 3 - dos hijos: usar predecesor inorder
            Node<T> predecessor = findMax(node.getLeft());
            node.setData(predecessor.getData());
            node.setLeft(delete(node.getLeft(), predecessor.getData()));
        }

        return node;
    }

    private Node<T> findMax(Node<T> node) {
        while (node.getRight() != null) {
            node = node.getRight();
        }
        return node;
    }

    @Override
    public Iterator<T> iterator() {
        switch(traversal){
            case BYLEVELS:
                return new LevelIterator();
            case INORDER:
                return new InOrderIterator();
            case POSTORDER:
                return new PostOrderIterator();
            case PREORDER:
                return new PreOrderIterator();
            default:
                throw new UnsupportedOperationException();
        }
    }

    private class LevelIterator implements Iterator<T>{
        private Queue<Node<T>> q = new LinkedList<>();

        public LevelIterator(){
            if(root == null){
                return;
            }
            q.add(root);
        }

        @Override
        public boolean hasNext() {
            return !q.isEmpty();
        }

        @Override
        public T next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            Node<T> current = q.remove();
            if(current.getLeft() != null){
                q.add(current.getLeft());
            }
            if(current.getRight() != null){
                q.add(current.getRight());
            }
            return current.getData();
        }
    }

    private class InOrderIterator implements Iterator<T> {
        private Stack<Node<T>> stack = new Stack<>();
        private Node<T> current = root;

        public InOrderIterator() {
        }

        private void advanceToLeftmost(Node<T> node) {
            while (node != null) {
                stack.push(node);
                node = node.getLeft();
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty() || current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            advanceToLeftmost(current);
            current = stack.pop();
            T result = current.getData();
            current = current.getRight();

            return result;
        }
    }


    private class PostOrderIterator implements Iterator<T>{
        private Stack<Node<T>> s = new Stack<>();
        Node<T> current = root;
        Node<T> prev = null;

        public PostOrderIterator(){
            pushToLeftLeaf(root);
        }

        private void pushToLeftLeaf(Node<T> node){
            while(node != null){
                while(node.getLeft() != null){
                    s.push(node);
                    node = node.getLeft();
                }
                s.push(node);
                node = node.getRight();
            }
        }

        @Override
        public boolean hasNext() {
            return !s.isEmpty();
        }

        @Override
        public T next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            current = s.pop();
            T result = current.getData();
            prev = current;

            if(!s.isEmpty()){
                current = s.peek();
                if(prev != current.getRight()){
                    pushToLeftLeaf(current.getRight());
                }
            }
            return result;
        }
    }

    private class PreOrderIterator implements Iterator<T> {
        private Stack<Node<T>> stack = new Stack<>();

        public PreOrderIterator() {
            if (root != null) stack.push(root);
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            Node<T> current = stack.pop();
            if (current.getRight() != null) stack.push(current.getRight());
            if (current.getLeft() != null) stack.push(current.getLeft());
            return current.getData();
        }
    }

    @Override
    public void setTraversal(Traversal traversal) {
        this.traversal = traversal;
    }
}