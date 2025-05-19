package b;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParametrizedBinaryTreeTest {
    public static class Jefe
    {
        private String name;
        private int edad;
        public Jefe(String value) {
            try {
                String[] arrOfStr = value.split(":");
                if (arrOfStr.length != 2)
                    throw new IllegalArgumentException();
                if ((!arrOfStr[0].startsWith("(")) || (! arrOfStr[1].endsWith(")")))
                    throw new IllegalArgumentException();
                name= arrOfStr[0].substring(1);
                edad= Integer.parseInt(arrOfStr[1].substring(0,
                        arrOfStr[1].length()-1));
            }
            catch(Exception e) {
            name= value;
            edad= Integer.MIN_VALUE;
            }
        }
        @Override
        public String toString() {
            return String.format("(%s:%d)", name, edad);
        }
    }

    @Test
    void testConstructorWithJefeFile() throws Exception {
        ParametrizedBinaryTree<Jefe> tree = new ParametrizedBinaryTree<>("jefe", Jefe.class);
        tree.inorder();
        tree.preorder();
        tree.postorder();

        tree.printHierarchy();
        int height = tree.getHeight();
        System.out.println("height: " + height);
        //assert(3 == height);
    }
}
