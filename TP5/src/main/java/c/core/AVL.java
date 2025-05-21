package c.core;

import java.util.*;

public class AVL<T extends Comparable<? super T>> implements BSTreeInterface<T> {
    private Node<T> root;

    private Traversal traversal = Traversal.BYLEVELS;

    @Override
    public void insert(T value) {
        if(value == null){
            throw new IllegalArgumentException();
        }

        root = insert(value, root);
    }

    private Node<T> insert(T value, Node<T> current){
        if(current == null){
            return new Node<>(value);
        }
        int cmp = value.compareTo(current.getData());
        if(cmp <= 0){
            current.setLeft(insert(value, current.getLeft()));
        }else{
            current.setRight(insert(value, current.getRight()));
        }

        current.updateHeight();

        return balanceTree(current);
    }

    private Node<T> balanceTree(Node<T> current) {
        int balance = getBalance(current);
        if(balance > 1){
            if(getBalance(current.getLeft()) < 0){
                current.setLeft(leftRotate(current.getLeft()));
            }
            return rightRotate(current);
        }
        if(balance < -1){
            if(getBalance(current.getRight()) > 0){
                current.setRight(rightRotate(current.getRight()));
            }
            return leftRotate(current);
        }

        return current;
    }

    private Node<T> rightRotate(Node<T> node){
        Node<T> aux = node.getLeft();
        node.setLeft(aux.getRight());
        aux.setRight(node);
        node.updateHeight();
        aux.updateHeight();
        return aux;
    }

    private Node<T> leftRotate(Node<T> node){
        Node<T> aux = node.getRight();
        node.setRight(aux.getLeft());
        aux.setLeft(node);
        node.updateHeight();
        aux.updateHeight();
        return aux;
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
        return node.getHeight();
    }

    private int getBalance(Node<T> node) {
        if (node == null) return 0;
        return getHeight(node.getLeft()) - getHeight(node.getRight());
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

        return balanceTree(node);
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

    @Override
    public int getOcurrences(T myData) {
        return getOcurrences(root, myData);
    }

    private int getOcurrences(Node<T> node, T myData) {
        if (node == null) return 0;
        int cmp = myData.compareTo(node.getData());
        if(cmp < 0){
            return getOcurrences(node.getLeft(), myData);
        }
        if(cmp > 0){
            return getOcurrences(node.getRight(), myData);
        }

        return 1 + getOcurrences(node.getLeft(), myData);
    }

    @Override
    public T valueAt(int index) {
        if(index < 1) {
            throw new IllegalArgumentException();
        }

        Iterator<T> it = new InOrderIterator();
        for(int i = 1; i < index; i++){
            it.next();
        }
        return it.next();
    }

    @Override
    public T getCommonNode(T myData1, T myData2) {
        if(myData1 == null || myData2 == null){
            return null;
        }
        if(myData1.compareTo(myData2) > 0){
            T temp = myData1;
            myData1 = myData2;
            myData2 = temp;
        }

        Node<T> ancestor = getCommonNode(root, myData1, myData2);
        if(ancestor == null){
            return null;
        }
        return ancestor.getData();
    }

    private Node<T> getCommonNode(Node<T> node, T lesser, T greater){
        if(node == null){
            return null;
        }

        int cmp1 = lesser.compareTo(node.getData());
        if(cmp1 > 0){
            return getCommonNode(node.getRight(), lesser, greater);
        }

        int cmp2 = greater.compareTo(node.getData());
        if(cmp2 < 0){
            return getCommonNode(node.getLeft(), lesser, greater);
        }

        if(cmp1 == 0 && cmp2 == 0){//both values are equal

            boolean isLeft = contains(lesser, node.getLeft());
            //don't have to check right side, given that the other value is equal, it can't be a right child
            if(isLeft){
                return node;
            }
            //if it's not a left child, then there is no other occurrence of the value
            return null;
        }

        return node;
    }
}
