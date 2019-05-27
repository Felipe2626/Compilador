package sintatico;

public class ErroSintatico extends Exception{
	  private String msg;
	    public ErroSintatico(){
	      super("Erro Sintatico");
	    }
	    public String getMessage(){
	      return msg;
	    }
	  
}
