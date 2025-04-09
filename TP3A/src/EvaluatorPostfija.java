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
            if (!token.matches("((-)?[0-9]+(.[0-9]+)?|\\+|-|\\*|/)") ) {
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
            if (token.matches("((-)?[0-9]+)(.[0-9]+)?")) {
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

        Map<String, Map<String, Boolean>> precedencia = new HashMap<>();
        precedencia.put("+", new HashMap<>());
        precedencia.put("-", new HashMap<>());
        precedencia.put("*", new HashMap<>());
        precedencia.put("/", new HashMap<>());

        precedencia.get("+").put("+", true);
        precedencia.get("+").put("-", true);
        precedencia.get("+").put("*", false);
        precedencia.get("+").put("/", false);

        precedencia.get("-").put("+", true);
        precedencia.get("-").put("-", true);
        precedencia.get("-").put("*", false);
        precedencia.get("-").put("/", false);

        precedencia.get("*").put("+", true);
        precedencia.get("*").put("-", true);
        precedencia.get("*").put("*", true);
        precedencia.get("*").put("/", true);

        precedencia.get("/").put("+", true);
        precedencia.get("/").put("-", true);
        precedencia.get("/").put("*", true);
        precedencia.get("/").put("/", true);

        while (scannerLine.hasNext()) {
            String token = scannerLine.next();
            if (token.matches("((-)?[0-9]+)(.[0-9]+)?")) {
                postfija += token + " ";
            } else {
                while (!stack.isEmpty() && precedencia.get(stack.peek()).get(token)) {
                    postfija += stack.pop() + " ";
                }
                stack.push(token);
            }
        }
        while (!stack.isEmpty()) {
            postfija += stack.pop() + " ";
        }
        return postfija;
    }



    public static void main(String[] args) {
        Double rta = new EvaluatorPostfija().evaluate();
        System.out.println("El resultado es: " + rta);
    }


}
