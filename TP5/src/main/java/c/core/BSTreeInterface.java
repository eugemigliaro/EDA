package c.core;

public interface BSTreeInterface<T extends Comparable<? super T>> extends Iterable<T>{
    void insert(T myData);
    void preOrder();
    void postOrder();
    void inOrder();
    NodeTreeInterface<T> getRoot();
    int getHeight();
    boolean contains(T myData);
    T getMin();
    T getMax();
    void printByLevels();
    void delete(T myData);
    enum Traversal{
        PREORDER,
        POSTORDER,
        INORDER,
        BYLEVELS
    }

    void setTraversal(Traversal traversal);
    int getOcurrences(T myData);
    T valueAt(int index);
    T getCommonNode(T myData1, T myData2);
}