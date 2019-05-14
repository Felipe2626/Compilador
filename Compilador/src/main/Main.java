package main;
import java.io.*;
import java.util.ArrayList;

import lexer.*;
import symbols.Env; 
//import parser.*; import parser.*;


public class Main {

	public static void main(String[] args ) {// throws IOException
		String filepath = new String (args[0]);
		FileReader fr = null;
		ArrayList<Token> lToken = new ArrayList<Token>();
		try {
			fr = new FileReader(filepath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Instacia a classe do lexer
		Env env = new Env();

		Token outT=null;
		Lexer lex = new Lexer(fr,env);
		
		//Faz a leitura de todos os tokens
		do {
			try {
				outT=lex.scan();
			} catch (IOException e ) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				break;
			}catch(NullPointerException e) {
				
				System.out.printf("!Erro:Token mal formado!\n");
				break;
			}
			if(outT!=null)
				lToken.add(outT);
		}while(outT!=null);
		
		
		System.out.printf("Token\tTag\n");
		System.out.printf("----------------\n");
		for (Token token : lToken) {
			System.out.printf(token.toString()+"\t"+token.tag+"\n");
		}
		System.out.printf("\nTabela de Simbolos\n");
		System.out.printf("Tipo\tNome\tValor\n");
		System.out.printf("-------------------------\n");
		env.showItens();
		
	}
	

}
