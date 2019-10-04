package br.dislexico.compilador.utils;

/*
 * Esta classe é criada como um Singleton
 */
public class TabSimbolos {
	private static TabSimbolos instance = new TabSimbolos();
	
	private Map<String, Token> tabela;
	
	private TabSimbolos() {
		tabela = new HashMap<String, Token>();
		// Pré-carregar a tabela com todas a PALAVRAS RESERVADAS
	}

	public static TabSimbolos getInstance() {
		return instance;
	}
	
	public void printTabela() {
		
	}
}