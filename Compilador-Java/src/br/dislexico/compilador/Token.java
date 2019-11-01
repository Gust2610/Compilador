
package br.dislexico.compilador;
/*
 *  Representa um simbolo valido para a linguagem. 
 *  Seu estado deve possuir, minimamente, 
 *  o codigo do token (estabelece o tipo de token) e 
 *  o lexema extraido do arquivo de entrada 
 */

public class Token {
	private String lexema;
	private TokenTipo tipoToken;
	private int linha, coluna;

	public Token(String lexema, TokenTipo tipoToken, int linha, int coluna) {
		this.lexema = lexema;
		this.tipoToken = tipoToken;
		this.linha = linha;
		this.coluna = coluna;
	}

	public Token(String lexema, TokenTipo tipoToken) {
		super();
		this.lexema = lexema;
		this.tipoToken = tipoToken;
	}

	public String getLexema() {
		return lexema;
	}

	public void setLexema(String lexema) {
		this.lexema = lexema;
	}

	public TokenTipo getTipoToken() {
		return tipoToken;
	}

	public void setTipoToken(TokenTipo tipoToken) {
		this.tipoToken = tipoToken;
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}

}
