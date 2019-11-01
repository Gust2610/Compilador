
package br.dislexico.compilador;

import java.io.EOFException;
import java.io.IOException;

import br.dislexico.compilador.main.FileLoader;

/*
* classe que implementa o Analisador Lexico 
* para a linguagem estabelecida. 
* Deve possuir o metodo nextToken, que contem 
* a logica de processamento do analisador l�xico 
*/

public class AnLexico {
	private FileLoader buffer;
	private StringBuilder lexema = new StringBuilder();
	private int linha;
	private int coluna;

	public AnLexico(String path) throws IOException {
		buffer = new FileLoader(path);
	}

	public Token nextToken() {
		Token token = null;

		try {
			token = this.processa();
		} catch (EOFException eof) {
			token = new Token("Final", TokenTipo.EOF);
		} catch (Exception e) {
			ErrorHandler.getInstance().gravaErro(new Erro("Erro Nao tratado", "", 0, 0, "Erro Nao tratado"));
		}
		return token;
	}

	private Token processa() throws EOFException, IOException {
		Token tk = null;
		char caractere;
		lexema = new StringBuilder();
		do {
			caractere = buffer.getNextChar();
		} while (Character.isWhitespace(caractere));

		lexema.append(caractere);
		linha = buffer.getLine();
		coluna = buffer.getColumn();

		if (Util.isLetter(caractere)) {
			tk = this.tokenId();
		} else if (Util.isDigit(caractere)) {
			tk = this.tokenNumInt();
		} else {
			switch (caractere) {
			case ':':
				caractere = buffer.getNextChar();
				if (caractere == '[') {
					buffer.rollbackChar();
					this.descartaComentario();
					tk = new Token("Comentario", TokenTipo.ERROR);
				} else {
					buffer.rollbackChar();
					tk = this.tokenAttribOp();
				}
				break;
			case '$':
				tk = this.tokenRelOp();
				break;
			case '\'':
				tk = this.tokenLiteral();
				break;
			case '+':
				tk = new Token(this.lexema.toString(), TokenTipo.ARIT_AS, this.linha, this.coluna);
				break;
			case '-':
				tk = new Token(this.lexema.toString(), TokenTipo.ARIT_AS, this.linha, this.coluna);
				break;
			case '*':
				tk = new Token(this.lexema.toString(), TokenTipo.ARIT_MD, this.linha, this.coluna);
				break;
			case '/':
				tk = new Token(this.lexema.toString(), TokenTipo.ARIT_MD, this.linha, this.coluna);
				break;
			case '.':
				tk = new Token(this.lexema.toString(), TokenTipo.TERM, this.linha, this.coluna);
				break;
			case '(':
				tk = new Token(this.lexema.toString(), TokenTipo.L_PAR, this.linha, this.coluna);
				break;
			case ')':
				tk = new Token(this.lexema.toString(), TokenTipo.R_PAR, this.linha, this.coluna);
				break;
			default:
				ErrorHandler.getInstance().gravaErro(
						new Erro("Lexico", this.lexema.toString(), this.linha, this.coluna, "Caractere Invalido!"));

				tk = new Token("Caractere Invalido", TokenTipo.ERROR);
			}
		}
		return tk;
	}

	private void descartaComentario() throws IOException {
		char c, eof;
		int EOFint = -1;
		eof = (char) EOFint;
		while (true) {
			c = buffer.getNextChar();
			lexema.append(c);
			if (c == '}') {
				c = buffer.getNextChar();
				lexema.append(c);
				if (c == '#') {
					lexema.append(c);
					break;
				}
			}
			if (c == eof) {
				this.lexema.delete(lexema.length() - 1, lexema.length());
				ErrorHandler.getInstance().gravaErro(new Erro("Lexico", this.lexema.toString(), this.linha, this.coluna,
						"Fechamento Invalido do Comentario. Aguardando }#"));
				break;
			}
		}
	}

	private Token tokenLiteral() throws IOException {
		Token tkLiteral = null;
		char c;
		boolean achou = false;
		try {
			while (true) {
				c = buffer.getNextChar();
				this.lexema.append(c);
				if (c == '\'') {
					tkLiteral = new Token(this.lexema.toString(), TokenTipo.LITERAL, this.linha, this.coluna);
					achou = true;
					break;
				}
			}
			if (achou == false) {
				throw new EOFException();
			}
		} catch (EOFException e) {
			ErrorHandler.getInstance().gravaErro(
					new Erro("Caractere Invalido", "'", this.linha, this.coluna, "Aguardando a aspas simples de fechamento"));
			tkLiteral = new Token(this.lexema.toString(), TokenTipo.ERROR);
		}

		return tkLiteral;
	}

	private Token tokenRelOp() throws IOException {
		Token tkRelOp = null;
		char c;
		c = buffer.getNextChar();
		boolean validar = false;

		//valida o RELOP &gt; e &ge;
		if (c == 'g') {
			this.lexema.append(c);
			c = buffer.getNextChar();
			if (c == 't' || c == 'e') {
				this.lexema.append(c);
				c = buffer.getNextChar();
				if (c == ';') {
					this.lexema.append(c);
					tkRelOp = new Token(this.lexema.toString(), TokenTipo.REL_OP, this.linha, this.coluna);
					validar = true;
				}
			}
		}

		//valida o RELOP &lt; e &le;
		if (validar == false && c == 'l') {
			this.lexema.append(c);
			c = buffer.getNextChar();
			if (c == 't' || c == 'e') {
				this.lexema.append(c);
				c = buffer.getNextChar();
				if (c == ';') {
					this.lexema.append(c);
					tkRelOp = new Token(this.lexema.toString(), TokenTipo.REL_OP, this.linha, this.coluna);
					validar = true;
				}
			}
		}

		//valida o RELOP &eq;
		if (validar == false && c == 'e') {
			this.lexema.append(c);
			c = buffer.getNextChar();
			if (c == 'q') {
				this.lexema.append(c);
				c = buffer.getNextChar();
				if (c == ';') {
					this.lexema.append(c);
					tkRelOp = new Token(this.lexema.toString(), TokenTipo.REL_OP, this.linha, this.coluna);
					validar = true;
				}
			}
		}

		//valida o RELOP &df;
		if (validar == false && c == 'd') {
			this.lexema.append(c);
			c = buffer.getNextChar();
			if (c == 'f') {
				this.lexema.append(c);
				c = buffer.getNextChar();
				if (c == ';') {
					this.lexema.append(c);
					tkRelOp = new Token(this.lexema.toString(), TokenTipo.REL_OP, this.linha, this.coluna);
					validar = true;
				}
			}
		}

		if (validar == false) {
			if (!Character.isWhitespace(c))
				this.lexema.append(c);
			ErrorHandler.getInstance().gravaErro(new Erro("Lexico", this.lexema.toString(), this.linha, this.coluna,
					"Operador Relacional Invalido"));
			tkRelOp = new Token("Error", TokenTipo.ERROR);
		}

		return tkRelOp;
	}

	private Token tokenAttribOp() throws IOException {
		Token tkAttribOp = null;
		char c;
		c = buffer.getNextChar();
		if (c == '<') {
			this.lexema.append(c);
			tkAttribOp = new Token(this.lexema.toString(), TokenTipo.ATTRIB_OP, this.linha, this.coluna);
		} else {
			ErrorHandler.getInstance().gravaErro(
					new Erro("Lexico", this.lexema.toString(), this.linha, this.coluna, "Caractere Invalido"));
			tkAttribOp = new Token(this.lexema.toString(), TokenTipo.ERROR);
		}

		return tkAttribOp;
	} 

	private Token tokenNumInt() throws IOException {
		Token tkNumInt = null;
		char c;

		try {
			while (true) {
				c = buffer.getNextChar();
				if (Util.isDigit(c)) {
					lexema.append(c);
				} else if (Util.isDot(c)) {
					this.lexema.append(c);
					tkNumInt = this.tokenNumFloat();
					break;
				} else if (c == 'e' || c == 'E') {
					this.lexema.append(c);
					tkNumInt = this.tokenNumCientifico(TokenTipo.NUM_INT);
					break;
				} else {
					buffer.rollbackChar();
					tkNumInt = new Token(this.lexema.toString(), TokenTipo.NUM_INT, this.linha, this.coluna);
					break;
				}
			}
		} catch (Exception e) {
			ErrorHandler.getInstance().gravaErro(
					new Erro("Erro nao tratado", this.lexema.toString(), this.linha, this.coluna, "Erro nao tratado"));
		} finally {
			this.lexema = new StringBuilder();
		}

		return tkNumInt;
	}

	private Token tokenNumFloat() throws IOException {
		Token tkNumFloat = null;
		char c = buffer.getNextChar();
		boolean first = false;
		boolean cient = false;
		try {
			while (Util.isDigit(c)) {
				this.lexema.append(c);
				c = buffer.getNextChar();
				first = true;
			}
			if (Character.isWhitespace(c) && !first) {
				throw new EOFException();
			}
			if ((!Util.isDigit(c) && c != 'e' && c != 'E' && !first) || ((c == 'e' || c == 'E') && first == false)) {
				if (Util.isLetter(c) || Util.isDot(c) || Util.isDollar(c) || Util.isQuote(c)) {
					this.lexema.append(c);
				}
				throw new EOFException();
			}
			if (c == 'e' || c == 'E') {
				this.lexema.append(c);
				tkNumFloat = this.tokenNumCientifico(TokenTipo.NUM_FLOAT);
				cient = true;
			}

		} catch (EOFException e) {
			ErrorHandler.getInstance().gravaErro(new Erro("N�mero Inv�lido", this.lexema.toString(), this.linha,
					this.coluna, "Caractere Invalido apos o ponto"));
			tkNumFloat = new Token("Erro", TokenTipo.ERROR);
			cient = true;
		}

		if (cient == false) {
			tkNumFloat = new Token(this.lexema.toString(), TokenTipo.NUM_FLOAT, this.linha, this.coluna);
			buffer.rollbackChar();
		}
		return tkNumFloat;
	}

	private Token tokenNumCientifico(TokenTipo tokenTipo) throws IOException {
		Token tkNumCientifico = null;
		char c;
		boolean firstTime = true;
		try {
			c = buffer.getNextChar();
			if (c == '+' || c == '-') {
				this.lexema.append(c);
				firstTime = false;
				c = buffer.getNextChar();
			}
			if (!Util.isDigit(c) && !firstTime) {
				if (Util.isLetter(c) || Util.isDot(c) || Util.isDollar(c) || Util.isQuote(c)) {
					this.lexema.append(c);
				}
				throw new EOFException();
			}
			while (true) {
				if (Util.isDigit(c)) {
					this.lexema.append(c);
					c = buffer.getNextChar();
				} else {
					break;
				}
			}
			tkNumCientifico = new Token(this.lexema.toString(), tokenTipo, this.linha, this.coluna);
		} catch (EOFException e) {
			ErrorHandler.getInstance().gravaErro(new Erro("Numero Invalido", this.lexema.toString(), this.linha,
					this.coluna, "Caractere Invalido apos o E"));
			tkNumCientifico = new Token("Erro", TokenTipo.ERROR);
		}
		buffer.rollbackChar();
		return tkNumCientifico;
	}

	private Token tokenId() throws IOException {
		Token tkId = null;
		char c;

		while (true) {
			c = buffer.getNextChar();
			if ((Util.isLetter(c)) || (Util.isDigit(c))) {
				this.lexema.append(c);
			} else {
				buffer.rollbackChar();
				tkId = TabSimbolos.getInstance().instalaToken(lexema.toString(), linha, coluna);
				lexema = new StringBuilder();
				break;
			}
		}
		return tkId;
	}
}
