package b;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;



public class BinaryTree implements BinaryTreeService {

    private Node root;

    private Scanner inputScanner;

    public BinaryTree(String fileName) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, FileNotFoundException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);

        if (is == null)
            throw new FileNotFoundException(fileName);

        inputScanner = new Scanner(is);
        inputScanner.useDelimiter("\\s+");

        buildTree();

        inputScanner.close();
    }



    private void buildTree() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        Queue<NodeHelper> pendingOps= new LinkedList<>();
        String token;

        root= new Node();
        pendingOps.add(new NodeHelper(root, NodeHelper.Action.CONSUMIR));

        while(inputScanner.hasNext()) {

            token= inputScanner.next();

            NodeHelper aPendingOp = pendingOps.remove();
            Node currentNode = aPendingOp.getNode();

            if ( token.equals("?") ) {
                // no hace falta poner en null al L o R porque ya esta con null

                // reservar el espacio en Queue aunque NULL no tiene hijos para aparear
                pendingOps.add( new NodeHelper(null, NodeHelper.Action.CONSUMIR) );  // como si hubiera izq
                pendingOps.add( new NodeHelper(null, NodeHelper.Action.CONSUMIR) );  // como si hubiera der
            }
            else {
                switch (aPendingOp.getAction())
                {
                    case LEFT:
                        currentNode = currentNode.setLeftTree(new Node() );
                        break;
                    case RIGHT:
                        currentNode = currentNode.setRightTree(new Node());
                        break;
                }


                // armo la info del izq, der o el root
                currentNode.data= token;


                // hijos se postergan
                pendingOps.add(new NodeHelper(currentNode, NodeHelper.Action.LEFT));
                pendingOps.add(new NodeHelper(currentNode, NodeHelper.Action.RIGHT));
            }


        }

        if (root.data == null)  // no entre al ciclo jamas
            root= null;

    }


    @Override
    public void preorder() {
        System.out.println("Preorder: ");
        if(root != null)
            root.preorder();
        System.out.println("\n");
    }



    @Override
    public void postorder() {
        System.out.println("Postorder: ");
        if(root != null)
            root.postorder();
        System.out.println("\n");
    }

    public void toFile(String fileName) {
        //ESTO NO FUNCIONA BIEN, NO ES REVERSIBLE, LO SOLUCIONO USANDO UN TOKENCOUNT, NO ME QUEDA OTRA PORQUE SINO NO TERMINA
        try (PrintWriter writer = new PrintWriter(fileName)) {
            Queue<Node> cola = new LinkedList<>();
            cola.offer(root);

            int height = getHeightRec();
            int currentLvl = 0;

            while (currentLvl <= height){
                int levelSize = cola.size();

                for(int i = 0; i < levelSize; i++) {
                    Node node = cola.poll();
                    if(node != null){
                        writer.print(node.data + " ");
                        cola.offer(node.left);
                        cola.offer(node.right);
                    }else{
                        writer.print("? ");
                        cola.offer(null);
                        cola.offer(null);
                    }
                }

                currentLvl++;
            }

            writer.println();
        } catch (IOException e) {
            throw new RuntimeException("Error escribiendo archivo: " + e.getMessage());
        }
    }


    public void printHierarchy(){
        System.out.println("Hierarchy: ");
        printHierarchy(root, "");
        //imprime el árbol con la información indentada según el nivel en el que se encuentra, imprime null si no hay hijos
        /*por ejemplo, para el siguiente árbol:
                --
                    ++
                 5
        imprime:
            --
                null
                ++
                    5
                    null
        */
    }

    private void printHierarchy(Node node, String prefix){
        if(node == null){
            System.out.println(prefix + "└──" + "null");
            return;
        }
        System.out.println(prefix + "└──" + node.data);
        if(node.isLeaf()){
            return;
        }
        printHierarchy(node.left, prefix + "    ");
        printHierarchy(node.right, prefix + "    ");
    }

    public int getHeightIt(){
        if(root == null){
            return -1;
        }

        int height = -1;
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()){
            int lvlSize = q.size();
            for(int i = 0; i < lvlSize; i++){
                Node node = q.remove();
                if(node.left != null){
                    q.add(node.left);
                }
                if(node.right != null){
                    q.add(node.right);
                }
            }
            height++;
        }
        return height;
    }

    public int getHeightRec(){
        return getHeightRec(root);
    }

    private int getHeightRec(Node node){
        if(node == null){
            return -1;
        }

        int leftHeight = getHeightRec(node.left);
        int rightHeight = getHeightRec(node.right);

        return 1 + (leftHeight > rightHeight ? leftHeight : rightHeight);
    }

    // hasta el get() no se evalua
    private static class Node {
        private String data;
        private Node left;
        private Node right;

        public Node setLeftTree(Node aNode) {
            left= aNode;
            return left;
        }


        public Node setRightTree(Node aNode) {
            right= aNode;
            return right;
        }



        public Node() {
            // TODO Auto-generated constructor stub
        }

        private boolean isLeaf() {
            return left == null && right == null;
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

    }  // end Node class


    static class NodeHelper {

        static enum Action { LEFT, RIGHT, CONSUMIR };


        private Node aNode;
        private Action anAction;

        public NodeHelper(Node aNode, Action anAction ) {
            this.aNode= aNode;
            this.anAction= anAction;
        }


        public Node getNode() {
            return aNode;
        }

        public Action getAction() {
            return anAction;
        }

    }



    public static void main(String[] args) throws FileNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        BinaryTree rta = new BinaryTree("data0_1");
        rta.preorder();
        rta.postorder();
        rta.printHierarchy();
        //rta.toFile("newFile");
        System.out.println(rta.getHeightIt());
        System.out.println(rta.getHeightRec());
    }

}