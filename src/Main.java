 /*
  * Ed Bull
  * CSC 4340
  * Second test case: I know it incorrectly detects as a Semantic error instead of a Syntax error.
  * Because of the order in which symbol ID is evaluated, it seems to always hit the NullPointerException first
  * Not sure how to fix it yet.
  * However, if you test it with x instead of z, you will see you correctly get a Syntax Error.
  */
import java.io.BufferedInputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	/*
	 * Simple UI loop that takes in multi-line input and feeds it to evaluate().
	 */
	public static void main(String[] args) {	
		Scanner stdin = new Scanner(new BufferedInputStream(System.in));
		String cliString = "WAE> ";
		String quitString = "exit";
		while (true) {
			ArrayList<String> input = new ArrayList<String>();
			String line = "";
			System.out.print(cliString);
			while(stdin.hasNextLine()) {
				System.out.flush();
				line = stdin.nextLine();
				if (line.equals("") || line.equals(quitString)) break;
				input.add(line);
			}
			
			if (line.equals(quitString)) break;
			if (input.size() > 0) {
				String result = String.join("\n", input);
				//The assignment test cases do not seem to require a semicolon
				if (!result.endsWith(";")) result = result + ";";
				evaluate(result);
			}
			input = new ArrayList<String>();
		}
		stdin.close();
	}
	

	/* Creates a Reader for the input string and feeds it into the Lexer/parser.
	 * If there is an Error, prints the type.
	 * If there is an Exception (unintended!) prints a stack trace for debugging.
	 */
	private static void evaluate(String input) {
		StringReader inputReader = new StringReader(input);
		try {
			parser p = new parser(new Lexer(inputReader));
			Object result = p.parse().value;
		} catch (Error e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			//Shouldn't get an Exception unless something really went wrong.
			e.printStackTrace();
		}
	}
}



