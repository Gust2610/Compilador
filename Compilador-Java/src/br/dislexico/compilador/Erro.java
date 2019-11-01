
package br.dislexico.compilador;

public class Erro {
	private String erroTipo;
	private String lexema;
	private int linha;
	private int coluna;
	private String descricao;

	public Erro(String erroTipo, String lexema, int linha, int coluna, String descricao) {
		super();
		this.erroTipo = erroTipo;
		this.lexema = lexema;
		this.linha = linha;
		this.coluna = coluna;
		this.descricao = descricao;
	}

	public String getErroTipo() {
		return erroTipo;
	}

	public void setErroTipo(String erroTipo) {
		this.erroTipo = erroTipo;
	}

	public String getLexema() {
		return lexema;
	}

	public void setLexema(String lexema) {
		this.lexema = lexema;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
