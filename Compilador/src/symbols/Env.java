package symbols;

import java.util.Hashtable;
import java.util.Map;

import lexer.*;
import inter.*;

public class Env {
	//Tipo nome valor
	private Hashtable table;
//	protected Env up;
	//protected Env botton;
	
	public Env() { 
		table = new Hashtable<Id,Word>();
//	up=null;
//		botton=null;
	}
	/*public void setBotton(Env botton) {
		this.botton=botton;
	}
	public void setUp(Env up) {
		this.up=up;
	}
	public Env getBotton() {
		return botton;
	}
	public Env getUp() {
		return up;
	}*/
	public void put( Id i,Token w) { //ID guarda o no nome //guarda a tipagem
		table.put(i,w);
		
	}
	public void showItens() {
		Map<Id,Word> map = table; 

		map.forEach((k, v) -> { 
			System.out.printf(v.toString()+"\t"+k.name+"\t"+k.value+"\n");  	
        }); 
			
	}

	public Id get(Token w) { //Busca em ascensão
/*		for(Env e = this; e != null; e = e.up) {
			Id found = (Id)(e.table.get(w));
			if(found != null )
				return found;
		}*/
		Id found = (Id)(table.get(w));
		if(found != null )
			return found;
		return null;
	}
}
