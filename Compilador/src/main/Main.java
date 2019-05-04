package main;
import java.io.*; import lexer.*; 
//import parser.*; import parser.*;


public class Main {

	public static void main(String[] args ) {// throws IOException
		String filepath = new String ("E:\\test1.txt");
		FileReader fr = null;
		try {
			fr = new FileReader(filepath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Lexer lex = new Lexer(fr);
		//Parser parse = new Parser(lex);
		//parse.program();
		System.out.print("Encerrado");
		System.out.write('\n');
	}
	

}
