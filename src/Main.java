/*
  * Ed Bull
  * CSC 4340
  * https://github.com/ebull1985/csc4340
  */
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
        String regex= "@[a-zA-z0-9_\\.]+;";
		while (true) {
			ArrayList<String> input = new ArrayList<String>();
			String line = "";
			do  {
				System.out.print(cliString);
				System.out.flush();
				if (stdin.hasNextLine()) {
					line = stdin.nextLine().trim();
					//If the line is a file command, read the file and add each line to ArrayList input
                    if(line.matches(regex)) {
                    	//Pulls the filename out from @<filename>; using substring()
                        String filename = line.substring(1, line.length() - 1);
                        try {
	                        BufferedReader fileInput = new BufferedReader(new FileReader(filename));
	                        String cur;
	                        while((cur = fileInput.readLine()) != null) input.add(cur);
	                        fileInput.close();
                        } catch(FileNotFoundException e) {
                        	System.out.println("\nFile not found.\n");
                        } catch (IOException e) {
                        	System.out.println("\nIO Error.\n");
                        }
                        break;	//Must break to allow input to be processed
                    }
					if (line.equals("") || line.equals(quitString)) break;
					input.add(line);	//If no special cases are hit, just add the line
					if (line.endsWith(termString)) break; //And keep going until we hit termString
				}
			} while (stdin.hasNextLine());
			
			if (line.equals(quitString)) break;		//Quit if the user enters the quitString
			if (input.size() > 0) {					//Otherwise, convert input to a string and parse it
				String result = String.join("\n", input);
				evaluate(result);
			}
			input = new ArrayList<String>();		//Resetting input after processing it
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

 

