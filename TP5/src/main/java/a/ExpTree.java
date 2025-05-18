package a;

import java.util.Scanner;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class ExpTree implements ExpressionService {
    private Node root;
    public ExpTree() {
        System.out.print("Introduzca la expresión en notación infija con todos los paréntesis y blancos: ");
        // token analyzer
        Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
        String line= inputScanner.nextLine();
        inputScanner.close();
        buildTree(line);
    }
    private void buildTree(String line) {
        // space separator among tokens
        Scanner lineScanner = new Scanner(line).useDelimiter("\\s+");
        root = new Node(lineScanner);
        lineScanner.close();
    }

    @Override
    public void inorder(){
        if(root != null){
            root.inorder();
        }
    }

    @Override
    public void preorder(){
        if(root != null){
            root.preorder();
        }
    }

    @Override
    public void postorder(){
        if(root != null){
            root.postorder();
        }
    }

    @Override
    public double eval(){
        if(root != null){
            return root.eval();
        }

        throw new RuntimeException("Expresión vacía o mal formada");
    }

    private static BinaryOperator<Double> evalFn(String op){
        switch(op){
            case "+": return (a, b) -> a + b;
            case "-": return (a, b) -> a - b;
            case "*": return (a, b) -> a * b;
            case "/": return (a, b) -> a / b;
            case "^": return (a, b) -> Math.pow(a,b);
            default: return null;
        }
    }

    private static double getValue(String token){
        if(token.matches("[0-9]+(\\.[0-9]+)?")){
            return Double.parseDouble(token);
        } else {
            throw new RuntimeException("Variable no definida: " + token);
        }
    }

    static final class Node {
        private String data;
        private Node left, right;
        private Scanner lineScanner;
        public Node(Scanner theLineScanner) {
            lineScanner = theLineScanner;
            Node auxi = buildExpression();
            data = auxi.data;
            left = auxi.left;
            right = auxi.right;
            if (lineScanner.hasNext() )
                throw new RuntimeException("Bad expression");
        }
        private Node() {
        }

        private Node buildExpression() {
            if (!lineScanner.hasNext())
                throw new RuntimeException("Expresión vacía o mal formada");

            String token = lineScanner.next();

            if (token.equals("(")) {
                // Subexpresión
                Node left = buildExpression();

                if (!lineScanner.hasNext())
                    throw new RuntimeException("Operador faltante");

                String operator = lineScanner.next();
                if (!isOperator(operator))
                    throw new RuntimeException("Operador inválido: " + operator);

                Node right = buildExpression();

                if (!lineScanner.hasNext("\\)"))
                    throw new RuntimeException("Falta cierre de paréntesis");
                lineScanner.next(); // consumir ")"

                Node result = new Node();
                result.data = operator;
                result.left = left;
                result.right = right;
                return result;
            } else {
                // Constante (número o variable)
                Node result = new Node();
                result.data = token;
                return result;
            }
        }

        private boolean isOperator(String token) {
            return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("^");
        }

        private void inorder(){
            if(left != null){
                System.out.println("(");
                left.inorder();
            }
            System.out.print(data + " ");
            if(right != null){
                right.inorder();
                System.out.println(")");
            }
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

        private double eval(){
            if(isOperator(data)){
                return evalFn(data).apply(left.eval(), right.eval());
            }
            return getValue(data);
        }
    }
}
