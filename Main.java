import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Tianxiang Gao
 *
 */

public class Main {
	private static BufferedWriter bufferedWriter;
	private static BufferedReader bufferedReader;

	public static void main(String args[]) throws Exception {
		String inputPath = "/Users/frankgao/Documents/COMS661/input.txt";
		String outputPath = "/Users/frankgao/Documents/COMS661/AST.xml";
		System.out.println("read Query from \"" + inputPath + "\"");
		String input = readCommand(inputPath);
		System.out.println("input Query: ");
		System.out.println("---------------------------------------");
		System.out.println(input);
		String rst = parse(input);
		System.out.println("\n---------------------------------------");
		System.out.println("AST.xml are output as following : ");
		System.out.println("---------------------------------------\n");
		System.out.println(rst);
		System.out.println("\n---------------------------------------");
		System.out.println("Start to write into file :\n" + outputPath);
		System.out.println("---------------------------------------");
		outputAST(rst, outputPath);
		System.out.println("---------------------------------------");
	}

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
