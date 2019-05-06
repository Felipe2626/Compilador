package main;
import java.io.*; import lexer.*; 
//import parser.*; import parser.*;


public class Main {

	public static void main(String[] args ) {// throws IOException
		String filepath = new String ("src/Test/test2.txt");
		FileReader fr = null;
		try {
			fr = new FileReader(filepath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Lexer lex = new Lexer(fr);
		Token outT=null;
		do {
			try {
				outT=lex.scan();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
			System.out.printf (outT.toString()+" ");
		}while(true);
		//Parser parse = new Parser(lex);
		//parse.program();
		System.out.printf("\nEncerrado\n");
	
	}
	

}
