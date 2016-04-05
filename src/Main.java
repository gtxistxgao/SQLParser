import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * 2016 Apr 5, 2016 Main.java
 * 
 * @author: TianxiangGao
 *
 */

public class Main {
	private static BufferedWriter bufferedWriter;
	private static BufferedReader bufferedReader;

	/**
	 * @param args
	 * @throws Exception
	 * @description: this method will read the query command from input.txt and
	 *               parse it then output the resut to AST.xml file
	 *
	 */
	public static void main(String args[]) throws Exception {
		String inputPath = "input.txt";
		String outputPath = "AST.xml";
		System.out.println("read Query from \n\"" + inputPath + "\"");
		String input = readCommand(inputPath);
		System.out.println("input Query: ");
		System.out.println("---------------------------------------");
		System.out.println(input);
		// CALL the parse method and parse the input query then assign the AST
		// to rst String.
		String rst = parse(input);
		System.out.println("\n---------------------------------------");
		System.out.println("AST.xml are output as following : ");
		System.out.println("---------------------------------------\n");
		// print out rst String(AST)
		System.out.println(rst);
		System.out.println("\n---------------------------------------");
		System.out.println("Start to write into file :\n\"" + outputPath + "\"");
		System.out.println("---------------------------------------");
		// output the rst into AST.xml file
		outputAST(rst, outputPath);
		System.out.println("---------------------------------------");
	}

	/**
	 * @param input
	 * @return AST
	 * @description: this method parse the input Query String
	 *
	 */
	private static String parse(String input) {
		String rst = "";
		try {
			rst = Parser.parse(input);
		} catch (Exception e) {
			System.out.println("Error during Parsing");
			e.printStackTrace();
		}
		return rst;
	}

	/**
	 * @param inputPath
	 * @return Query String
	 * @description: this method will read the query from the file of
	 *               "inputPath" and return the Query String
	 *
	 */
	private static String readCommand(String inputPath) {
		String input = "";
		try {
			String temp = "";
			FileReader reader = new FileReader(inputPath);
			bufferedReader = new BufferedReader(reader);
			temp = bufferedReader.readLine();
			while (temp != null) {
				input += "\n" + temp;
				temp = bufferedReader.readLine();
			}
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + inputPath + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + inputPath + "'");
		}
		return input;
	}

	/**
	 * @param rst
	 * @param outputPath
	 * @description: This method will output the AST into the file of
	 *               "outputPath"
	 *
	 */
	private static void outputAST(String rst, String outputPath) {
		FileWriter writer;
		try {
			writer = new FileWriter(outputPath);
			bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(rst);
			bufferedWriter.close();
		} catch (IOException e) {
			System.out.println("Error during writing result");
			e.printStackTrace();
		}
		System.out.println("output Successfully");
	}
}
