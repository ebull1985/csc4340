/*
  * Ed Bull
  * CSC 4340
  * https://github.com/ebull1985/csc4340
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
		String cliString = "SQL>";
		String quitString = "exit;";
		String termString = ";";
        String regex= "@[a-zA-z0-9_]+;";
        String l;
		while (true) {
			ArrayList<String> input = new ArrayList<String>();
			String line = "";
			do  {
				System.out.print(cliString);
				System.out.flush();
				if (stdin.hasNextLine()) {
					line = stdin.nextLine().trim();
                    if(line.matches(regex) {
                        String filename = line.substring(1, line.length() - 1);
                        BufferedReader in = new BufferedReader(new FileReader("<Filename>"));
                        while((l = in.readLine()) != null) {
                            System.out.println(l);
                        }
                        in.close();
                    }
					if (line.equals("") || line.equals(quitString)) break;
					input.add(line);
					if (line.endsWith(termString)) break;
				}
			} while (stdin.hasNextLine());
			
			if (line.equals(quitString)) break;
			if (input.size() > 0) {
				String result = String.join("\n", input);
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
			System.out.println("\n" + e.getMessage() + "\n");
		} catch (Exception e) {
			//Shouldn't get an Exception unless something really went wrong.
			e.printStackTrace();
		}
	}
}
