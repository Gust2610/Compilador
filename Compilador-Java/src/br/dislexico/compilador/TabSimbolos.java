
package br.dislexico.compilador;

import java.util.HashMap;

/*
 *  define a Tabela de Simbolos, 
 *  usada para armazenar dados dos Identificadores 
 *  (preferencialmente, um Singleton) 
 */

public class TabSimbolos extends HashMap<String, Token> {
	private static final long serialVersionUID = 1L;
	private static TabSimbolos uniqueInstance;

	private TabSimbolos() {
		this.configTabelaSimbolos(new Token("verdadeiro", TokenTipo.LOGIC_VAL));
		this.configTabelaSimbolos(new Token("falso", TokenTipo.LOGIC_VAL));
		this.configTabelaSimbolos(new Token("nao", TokenTipo.LOGIC_OP));
		this.configTabelaSimbolos(new Token("e", TokenTipo.LOGIC_OP));
		this.configTabelaSimbolos(new Token("ou", TokenTipo.LOGIC_OP));
		this.configTabelaSimbolos(new Token("bool", TokenTipo.TYPE));
		this.configTabelaSimbolos(new Token("text", TokenTipo.TYPE));
		this.configTabelaSimbolos(new Token("int", TokenTipo.TYPE));
		this.configTabelaSimbolos(new Token("float", TokenTipo.TYPE));
		this.configTabelaSimbolos(new Token("programa", TokenTipo.PROGRAM));
		this.configTabelaSimbolos(new Token("end_prog", TokenTipo.END_PROGRAM));
		this.configTabelaSimbolos(new Token("inicio", TokenTipo.BEGIN));
		this.configTabelaSimbolos(new Token("fim", TokenTipo.END));
		this.configTabelaSimbolos(new Token("se", TokenTipo.IF));
		this.configTabelaSimbolos(new Token("entao", TokenTipo.THEN));
		this.configTabelaSimbolos(new Token("senao", TokenTipo.ELSE));
		this.configTabelaSimbolos(new Token("para", TokenTipo.FOR));
		this.configTabelaSimbolos(new Token("enquanto", TokenTipo.WHILE));
		this.configTabelaSimbolos(new Token("declare", TokenTipo.DECLARE));
		this.configTabelaSimbolos(new Token("ate", TokenTipo.TO));

	}

	public static synchronized TabSimbolos getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new TabSimbolos();
		}
		return uniqueInstance;
	}

	public void configTabelaSimbolos(Token token) {
		this.put(token.getLexema(), token);
	}

	public Token instalaToken(String lexema, int linha, int coluna) {
		Token token = null;

		if (containsKey(lexema)) {
			token = get(lexema);
			token.setLinha(linha);
			token.setColuna(coluna);
		} else {
			token = new Token(lexema, TokenTipo.ID, linha, coluna);
			configTabelaSimbolos(token);
		}
		return token;
	}

	public void printToken() {
		System.out.println();
		System.out.println("##############################");
		System.out.println("      Tabela de Simbolos");
		System.out.println("##############################");
		for (Entry<String, Token> entry : entrySet()) {
			if (entry.getValue().getLinha() != 0 && entry.getValue().getColuna() != 0) {
				System.out.println("Lexema: " + entry.getValue().getLexema());
				System.out.println("Tipo Token: " + entry.getValue().getTipoToken());
				System.out.println("Linha: " + entry.getValue().getLinha());
				System.out.println("Coluna: " + entry.getValue().getColuna());
				System.out.println("##############################");
			}
		}
	}
}
