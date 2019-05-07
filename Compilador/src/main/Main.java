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
		//Instacia a classe do lexer
		Lexer lex = new Lexer(fr);
		Token outT=null;
		
		//Faz a leitura de todos os tokens
		do {
			try {
				outT=lex.scan();
				System.out.print (outT.toString()+" ");
			} catch (IOException e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}catch(NullPointerException e) {
				
				System.out.printf("!Erro:Token mal formado!");
				break;
			}
		}while(outT!=null);
         
		//Parser parse = new Parser(lex);
		//parse.program();
		System.out.printf("\nEncerrado\n");
	
	}
	

}
