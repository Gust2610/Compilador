package br.dislexico.compilador;

public class Compilador {
	public static void main(String[] args) {
		// Recebe nome do arquivo a ser compilado
		if (args.length != 1) {
			// Imprimir o "usage" do programa
	
		} else {
			// Cria instância do Sintatico e o executa
			try {
				Sintatico s = new Sintatico(args[0]);
				s.processar();
			} catch (FileNotFoundException fnf) {
				// Imprimir mensagem apropriada
				
			}
		}	
	}
}