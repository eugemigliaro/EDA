import java.util.Scanner;
import java.util.Stack;

public class EvaluatorPostija {

	
 	private Scanner scannerLine;
	

	public EvaluatorPostija()
	{
	    Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
	    System.out.print("Introduzca la expresiOn en notaciOn postfija: ");
	    inputScanner.hasNextLine();

	    String line = inputScanner.nextLine();

	    scannerLine = new Scanner(line).useDelimiter("\\s+");

	}
	
	public Double evaluate() {
		Stack<Double> auxi= new Stack<Double>();

		while( scannerLine.hasNext() )
		{
			String s = scannerLine.next();

			if ( isOperand(s) )
			{
				auxi.push(Double.valueOf(s));
			}
			else{	// operador o error
				if (! isOperator(s) )
					throw new RuntimeException("Invalid operator " + s);

				Double operand2;
				if (auxi.empty())
					throw new RuntimeException("Operand missing");
				else
					operand2= auxi.pop();

				Double operand1;
				if (auxi.empty())
					throw new RuntimeException("Operand missing");
				else
					operand1= auxi.pop();

				auxi.push(  eval(s, operand1 , operand2) );
			}
		}
		double rta= auxi.pop();
		if (auxi.empty())
			return rta;

		throw new RuntimeException("Operator missing");
	}

	private boolean isOperand(String s)
	{
		try
		{
			Double.valueOf(s);
		}
		catch(NumberFormatException e)
		{
			return false;
		}
		return true;
	}

	private boolean isOperator(String s)
	{
		return s.matches("\\+|-|\\*|/");
	}

	private double eval(String op, double val1, double val2)
	{
		switch (op)
		{
			case "+": return val1 + val2;
			case "-": return val1 - val2;
			case "*": return val1 * val2;
			case "/": return val1 / val2;
		};

		throw new RuntimeException("invalid operator" +  op);
	}

		
	public static void main(String[] args) {
		Double rta = new EvaluatorPostija().evaluate();
		System.out.println(rta);
	}
	

}
