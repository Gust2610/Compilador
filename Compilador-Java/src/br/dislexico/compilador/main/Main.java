
package br.dislexico.compilador.main;

import java.io.IOException;

import br.dislexico.compilador.AnSintatico;
import br.dislexico.compilador.ErrorHandler;
import br.dislexico.compilador.TabSimbolos;

 class Main {

	private static AnSintatico anSintatico;

	 public static void main(String[] args) throws IOException {
		anSintatico = new AnSintatico("arquivo.txt");

		anSintatico.executar();

		ErrorHandler.getInstance().printErros();

		TabSimbolos.getInstance().printToken();
		System.out.println("Fim");
	}

}
