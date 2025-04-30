import java.util.Scanner;

public class Proof {
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
        System.out.print("Introduzca la expresión en notación postfija: ");
        inputScanner.hasNextLine();

        String line = inputScanner.nextLine();

        Scanner lineScanner = new Scanner(line).useDelimiter("\\s+");
        while(lineScanner.hasNext()) {
            //System.out.println(lineScanner.next());


	    	 String token = lineScanner.next();
			 System.out.println(token);
             // PARA EL ¿?, usamos el \\ porque el ? es para una expresion regular.
			 if (token.matches("¿\\?|¡!|,|;|##") )
				    System.out.println("OK");
			 else
				    System.out.println("invalid " + token);

        }
    }

}
