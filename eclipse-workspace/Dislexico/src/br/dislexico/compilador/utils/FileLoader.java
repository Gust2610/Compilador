package br.dislexico.compilador.utils;

public class FileLoader extends BufferedReader {
    
    private long line;
    private long column;
    private long lastLineSize;

    private char lineSeparatorStart;
    
    /**
     * Construtor que recebe nome do arquivo.
     * @param fileName nome do arquivo que deve ser aberto e mantido pela classe
     * @throws FileNotFoundException o arquivo nao foi encontrado
     */
    public FileLoader(String fileName) throws FileNotFoundException {
        this(new File(fileName));
    }
    
    /**
     * Construtor que recebe um arquivo.
     * @param file arquivo que deve ser aberto e mantido pela classe
     * @throws FileNotFoundException o arquivo nao foi encontrado
     */
    public FileLoader(File file) throws FileNotFoundException {
        super((new FileReader(file)));
        line = 1;
        column = 0;
        lastLineSize = 0;

        if (isCRLFLineEnding()) {
            lineSeparatorStart = '\r';
        } else {
            lineSeparatorStart = '\n';
        }
    }
    
    /**
     * Metodo que retorna um caractere da entrada, marcando sua posicao no buffer e testando o final
     * do arquivo
     * @return proximo caractere do buffer de entrada
     * @throws IOException caso um erro de leitura ocorra
     * @throws EOFException excessao retornada quando o final do arquivo � atingido.
      */
    public char getNextChar() throws EOFException, IOException {
        this.mark(1);
        int charValue = this.read();
        if (charValue == -1) throw new EOFException(); 

        column++;
        if (charValue == lineSeparatorStart) {
            if (isCRLFLineEnding()) {
                column++;
            }

            line++;
            lastLineSize = column;
            column = 0;
        }
       
        return (char) charValue; 
    }
    
    /**
     * Metodo que retorna o ultimo caractere lido.
     * @throws IOException
     */
    public void resetLastChar() throws IOException {
        this.reset();
        column--;
        if (column < 0) {
            column = lastLineSize;
            line--;
        }
    }

    /**
     * @return the line
     */
    public long getLine() {
        return line;
    }

    /**
     * @return the column
     */
    public long getColumn() {
        return column;
    }

    private static boolean isCRLFLineEnding() {
        return System.lineSeparator().length() == 2;
    }
}