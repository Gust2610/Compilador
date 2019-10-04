package br.dislexico.compilador.analisadores;

public class Lexico {

	private FileLoader fl;
	
	public Lexico(String filename) throws FileNotFoundException {
		this.fl = new FileLoader(filename);
	}
	
	public Token nextToken() {
		char c;
		Token t = null;
		// Verifica fim de arquivo: caso em que retornamos um "Token" EOF
		try {
			c = fl.getNextChar();
		} catch (EOFException e) {
			return new Token(TokenType.EOF, "");
		}
		
		// Descarte de espaços em branco
		while (Character.isWhiteSpace(c)) {
			c = fl.getNextChar();
		}
		
		// Eliminar comentários: {# ... #}
			
		
		// Define variável "lexema"
		StringBuilder sb = new StringBuilder();
		sb.append(c);
		
		// Buscar Token
		switch (c) {
			case '(':
				t = new Token(TokenType.LPAR, sb.toString()); 
				t.setLin(fl.getLine());
				t.setCol(fl.getColumn());
				return t;
			case ')':
				// ...
			
			
			
			
		}
		
		
		
		// TODO: implementar este método
	}
}

