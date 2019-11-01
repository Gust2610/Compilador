
package br.dislexico.compilador;

import java.util.ArrayList;

/*
 * tratador de erros. 
 * Em geral, deve manter registro de todos os erros 
 * encontrados durante a compilacao, e 
 * ser capaz de exibir um relatorio formatado no final
 *
 */
public class ErrorHandler {
	public static ErrorHandler instance = new ErrorHandler();
	private ArrayList<Erro> listError = new ArrayList<Erro>();

	private ErrorHandler() {
		listError = new ArrayList<>();
	}

	public static ErrorHandler getInstance() {
		return ErrorHandler.instance;
	}

	public void gravaErro(Erro erro) {
		listError.add(erro);
	}

	public void printErros() {
		System.out.println("");
		System.out.println("***************************");
		System.out.println("    Erros Encontrados");
		if (listError.isEmpty()) {
			System.out.println("***************************");
			System.out.println("   Nenhum Erro Encontrado!");
			System.out.println("***************************");
		} else {
			for (Erro erro : listError) {
				System.out.println("***************************");
				System.out.println("Lexema: " + erro.getLexema());
				System.out.println("Tipo Erro: " + erro.getErroTipo());
				System.out.println("Linha: " + erro.getLinha());
				System.out.println("Coluna: " + erro.getColuna());
				System.out.println("Descricao: " + erro.getDescricao());
			}
			System.out.println("***************************");
		}
	}
}
