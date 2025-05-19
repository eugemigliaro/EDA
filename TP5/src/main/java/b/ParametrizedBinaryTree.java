package b;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.function.Consumer;

public class ParametrizedBinaryTree<T>{
    private Node<T> root;
    private Scanner inputScanner;
    private String placeHolder;
    private Class<T> tClass;

    public ParametrizedBinaryTree(String fileName, Class<T> aClass) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, FileNotFoundException {
        placeHolder = "?";
        this.tClass = aClass;

        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);

        if(is == null){
            throw new FileNotFoundException(fileName);
        }

        inputScanner = new Scanner(is);
        inputScanner.useDelimiter("\\s+");

        buildTree();

        inputScanner.close();
    }

    private void buildTree() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Queue<Consumer<Node<T>>> ops = new LinkedList<>();

        String token;

        ops.add((n) -> root = n);

        while(inputScanner.hasNext()){
            token = inputScanner.next();
            Consumer<Node<T>> op = ops.remove();

            Node<T> newNode;

            if(isPlaceholder(token)){
                newNode = null;
                ops.add((left -> {}));
                ops.add((right -> {}));
            }else{
                T newData = tClass.getConstructor(String.class).newInstance(token);
                newNode = new Node<>(newData);
                ops.add((left -> newNode.left = left));
                ops.add((right -> newNode.right = right));
            }
            op.accept(newNode);
        }
    }

    public void preorder(){
        System.out.println("Preorder: ");
        if(root != null)
            root.preorder();
        System.out.println("\n");
    }

    public void postorder(){
        System.out.println("Postorder: ");
        if(root != null)
            root.postorder();
        System.out.println("\n");
    }

    public void inorder(){
        System.out.println("Inorder: ");
        if(root != null)
            root.inorder();
        System.out.println("\n");
    }

    @Override
    public boolean equals(Object o){
        if(o == null || !(o instanceof ParametrizedBinaryTree)){
            return false;
        }

        ParametrizedBinaryTree<T> other = (ParametrizedBinaryTree<T>) o;

        if(root == null && other.root == null){
            return true;
        }

        if(root == null){
            return false;
        }

        return root.subTreeEquals(other.root);
    }

    public void printHierarchy(){
        System.out.println("Hierarchy: ");
        printHierarchy(root, "");
        System.out.println("\n");
    }

    private void printHierarchy(Node<T> node, String prefix){
        if(node == null){
            System.out.println(prefix + "└──" + "null");
            return;
        }
        System.out.println(prefix + "└──Data: " + node.data);
        if(node.isLeaf()){
            return;
        }
        printHierarchy(node.left, prefix + "    ");
        printHierarchy(node.right, prefix + "    ");
    }

    public int getHeight(){
        if(root == null){
            return -1;
        }
        return root.getSubTreeHeight();
    }

    private static class Node<T>{
        private T data;
        private Node<T> left, right;

        public Node(T data){
            this.data = data;
            this.left = null;
            this.right = null;
        }

        public Node(){
            this.data = null;
            this.left = null;
            this.right = null;
        }

        private void preorder(){
            System.out.print(data + " ");
            if(left != null){
                left.preorder();
            }
            if(right != null){
                right.preorder();
            }
        }

        private void postorder(){
            if(left != null){
                left.postorder();
            }
            if(right != null){
                right.postorder();
            }
            System.out.print(data + " ");
        }

        private void inorder(){
            if(left != null){
                System.out.print("(");
                left.inorder();
            }
            System.out.print(data + " ");
            if(right != null){
                right.inorder();
                System.out.print(")");
            }
        }

        private boolean subTreeEquals(Node<T> other){
            if(other == null){
                return false;
            }

            if(data == null && other.data == null){
                return this.left.subTreeEquals(other.left) && this.right.subTreeEquals(other.right);
            }

            if(data == null || other.data == null){
                return false;
            }

            return data.equals(other.data) && this.left.subTreeEquals(other.left) && this.right.subTreeEquals(other.right);
        }

        private boolean isLeaf(){
            return left == null && right == null;
        }

        private int getSubTreeHeight(){
            if(this.isLeaf()){
                return 0;
            }
            if(left == null){
                return 1 + right.getSubTreeHeight();
            }
            if(right == null){
                return 1 + left.getSubTreeHeight();
            }

            int leftHeight = left.getSubTreeHeight();
            int rightHeight = right.getSubTreeHeight();

            return 1 + (leftHeight > rightHeight ? leftHeight : rightHeight);
        }
    }

    private boolean isPlaceholder(String s){
        return s.equals(placeHolder);
    }
}
