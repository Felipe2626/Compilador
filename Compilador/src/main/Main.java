package main;
import java.io.*;
import java.util.ArrayList;


import lexer.*;
import symbols.Env; 
import sintatico.*;


public class Main {

	public static void main(String[] args ) {// throws IOException
		//LE o arquivo
		String filepath;
		try {
			filepath=new String (args[0]);
		}catch(Exception e) {
			filepath=new String("src/t2/test6.txt");
		}
		FileReader fr = null;
		ArrayList<Token> lToken = new ArrayList<Token>();
		try {
			fr = new FileReader(filepath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Parser parser = new Parser(fr);
		parser.S();
		//Faz a leitura de todos os tokens
		/*do {
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
		}while(outT!=null);*/
		
		
		/*System.out.printf("Token\tTag\n");
		System.out.printf("----------------\n");
		for (Token token : lToken) {
			System.out.printf(token.toString()+"\t"+token.tag+"\n");
		}
		System.out.printf("\nTabela de Simbolos\n");
		System.out.printf("Tipo\tNome\tValor\n");
		System.out.printf("-------------------------\n");
		env.showItens();*/
		
	}
	

}
