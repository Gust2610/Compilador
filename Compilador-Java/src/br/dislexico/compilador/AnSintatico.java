
package br.dislexico.compilador;

/*
 * nesta etapa, esta classe sera apenas um "boneco" para testar o AnLexico. 
 * Classe executavel (metodo main) que deve ser capaz de acionar todas 
 * as partes do Analisador, desempenhando as funcoes abaixo: 
 * - receber o nome de um arquivo-fonte como argumento 
 * - criar instancia de AnLexico, passando o nome desse arquivo como parametro 
 * - chamar o metodo nextToken() ate que um token EOF seja produzido 
 * - escrever o nome de cada token e seu lexema (quando existir) na saida padrao, 
 * em ordem de ocorrencia 
 * - exibir o relatorio de erros, se existir algum 
 * 
 */

import java.io.IOException;

public class AnSintatico {
	AnLexico anLexico;
	private Token token;

	public AnSintatico(String path) throws IOException {
		anLexico = new AnLexico(path);
	}

	public void executar() {
		System.out.println("=========================");
		System.out.println("     Analixador Lexico");
		System.out.println("=========================");
		do {
			token = anLexico.nextToken();
			if (token.getTipoToken() != TokenTipo.ERROR) {
				System.out.println("Lexema: " + token.getLexema());
				System.out.println("Tipo Token: " + token.getTipoToken());
				if (token.getTipoToken() != TokenTipo.EOF) {
					System.out.println("Linha: " + token.getLinha());
					System.out.println("Coluna: " + token.getColuna());
				}
				System.out.println("=========================");
			}
		} while (token.getTipoToken() != TokenTipo.EOF);
	}

}
