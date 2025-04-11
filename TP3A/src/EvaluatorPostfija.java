import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class EvaluatorPostfija {


    private Scanner scannerLine;
    private boolean valid = true;


    public EvaluatorPostfija()
    {
        Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
        System.out.print("Introduzca la expresiOn en notaciOn infija: ");
        inputScanner.hasNextLine();

        String line = inputScanner.nextLine();

        scannerLine = new Scanner(line).useDelimiter("\\s+");
        while(scannerLine.hasNext()) {

            String token = scannerLine.next();
            if (!isNumber(token) && !isOperator(token)) {
                System.out.println("invalid " + token);
                valid = false;
            }

        }

        scannerLine = new Scanner(line).useDelimiter("\\s+");
    }

    public Double evaluate() {
        if(!valid) return null;

        String exp = InfijaToPostfija();
        scannerLine = new Scanner(exp).useDelimiter("\\s+");

        Stack<Double> auxi = new Stack<Double>();

        while(scannerLine.hasNext()){
            String token = scannerLine.next();
            if (isNumber(token)) {
                auxi.push(Double.valueOf(token));
            } else {
                Double b = auxi.pop();
                Double a = auxi.pop();
                switch (token) {
                    case "+":
                        auxi.push(a + b);
                        break;
                    case "-":
                        auxi.push(a - b);
                        break;
                    case "*":
                        auxi.push(a * b);
                        break;
                    case "/":
                        auxi.push(a / b);
                        break;
                    case "^":
                        auxi.push(Math.pow(a, b));
                        break;
                    default:
                        break;
                }
            }
        }

        return auxi.pop();
    }

    private String InfijaToPostfija() {
        String postfija = "";
        Stack<String> stack = new Stack<>();

        while (scannerLine.hasNext()) {
            String token = scannerLine.next();
            if (isNumber(token)) {
                postfija += token + " ";
            } else if (isOperator(token)) {
                while (!(stack.isEmpty()) && shouldPop(stack.peek(), token)) {
                    if(!stack.peek().equals("(")){
                        postfija += stack.pop() + " ";
                    }
                }

                if(token.equals(")")){
                    if(stack.peek().equals("(")){
                        stack.pop();
                    }else{
                        throw new RuntimeException("Invalid expression");
                    }
                }else{
                    stack.push(token);
                }
            }
        }
        while (!stack.isEmpty()) {
            postfija += stack.pop() + " ";
        }
        return postfija;
    }

    private boolean shouldPop(String op1, String op2) {
        Map<String, Integer> precedencia = new HashMap<>(){
            {
                put("+", 1);
                put("-", 1);
                put("*", 2);
                put("/", 2);
                put("^", 3);
                put("(", -1);
                put(")", 0);
            }
        };

        if(!precedencia.containsKey(op1) || !precedencia.containsKey(op2)) return false;

        if(op1.equals("^") && op2.equals("^")) return false;

        if(op2.equals("(")) return false;

        return precedencia.get(op1) >= precedencia.get(op2);
    }
    
    private boolean isNumber(String token) {
        return token.matches("((-)?[0-9]+)(.[0-9]+)?");
    }
    
    private boolean isOperator(String token) {
        return token.matches("[+\\-*/^()]");
    }

    public static void main(String[] args) {
        Double rta = new EvaluatorPostfija().evaluate();
        System.out.println("El resultado es: " + rta);
    }
}