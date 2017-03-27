/*
  * Ed Bull
  * CSC 4340
  * https://github.com/ebull1985/csc4340
  */
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	private static String dbname;
	private static Database db = new Database();
	
	/*
	 * Simple UI loop that takes in multi-line input and feeds it to evaluate().
	 */
	public static void main(String[] args) {
		boolean dbIsSet = setDBName(args);
		if(!dbIsSet) {
			System.out.println("Missing or invalid argument: database directory.");
			System.out.println("Reminder: provide a path to the database directory, NOT a .dat file");
			return;
		}
		db.initializeDatabase(dbname);
		db.displaySchema();
		
		Scanner stdin = new Scanner(new BufferedInputStream(System.in));
		String cliString = "SQL>";
		String quitString = "exit;";
		String termString = ";";
        String regex= "@[a-zA-z0-9_\\.]+;";
        boolean isFileRead = false;
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
	                        System.out.println(String.join("\n", input));
	                        fileInput.close();
                        } catch(FileNotFoundException e) {
                        	System.out.println("\nFile not found.\n");
                        } catch (IOException e) {
                        	System.out.println("\nIO Error.\n");
                        }
                        isFileRead = true;
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
				evaluate(result, isFileRead);
				isFileRead = false; //After each execution, reset isFileRead
			}
			input = new ArrayList<String>();		//Resetting input after processing it
		}
		stdin.close();
	}
	

	/* Creates a Reader for the input string and feeds it into the Lexer/parser.
	 * If there is an Error, prints the type.
	 * If there is an Exception (unintended!) prints a stack trace for debugging.
	 */
	private static void evaluate(String input, boolean isFileRead) {
		StringReader inputReader = new StringReader(input);
		try {
			parser p = new parser(new Lexer(inputReader));
			Object result = p.parse().value;
		} catch (Error e) {
			//As per example in assignment
			//Need to print only "Syntax Error" or "Lexical Error" if input is from cli
			//But if input is from a file, print detailed information on a Syntax Error
			if (isFileRead) System.out.println("\n" + e.getMessage() + "\n");
			else {
				if (e.getMessage().startsWith("Syntax Error")) System.out.println("Syntax Error");
				else if (e.getMessage().startsWith("Lexical Error")) System.out.println("Lexical Error");
				else System.out.println("\n" + e.getMessage() + "\n");
			}
		} catch (SemanticException e) {
			System.out.println("Semantic Error - " + e.getMessage());
		} catch (Exception e) {
			//Shouldn't get an Exception unless something really went wrong.
			e.printStackTrace();
		}
	}
	
	/*
	 * Sets dbname according to arguments provided. If no dbname is provided, checks if a default exists.
	 * If a default does not exist,checks for args[1]. If args[1] is an absolute path, sets it. If not, checks to see if it might be a relative path.
	 */
	private static boolean setDBName(String[] args) {
		String path;
		if(args.length == 0) {
			try {
				path = new java.io.File(".").getCanonicalPath() + "/db";
				if(!new java.io.File(String.join("/", new String[]{path, "catalog.dat"})).exists()) return false;
			} catch (IOException e) {
				System.out.println("Error detected current working directory.");
				return false;
			}
		} else if(args.length == 1) {
			path = args[1];
			if(!new java.io.File(String.join("/", new String[]{path, "catalog.dat"})).exists()) {
				try {
					path = String.join("/", new String[]{new java.io.File(".").getCanonicalPath(), path});
					if(new java.io.File(String.join("/", new String[]{path, "catalog.dat"})).exists()) return false;
				} catch (IOException e) {
					System.out.println("Error detected current working directory.");
					return false;
				}
			}
		} else {
			return false;
		}
		dbname = path;
		return true;
	}
	
	public static Database getDB() {
		return db;
	}
}

 

