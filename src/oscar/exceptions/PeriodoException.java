package oscar.exceptions;

public class PeriodoException extends  RuntimeException{
    public PeriodoException (int ano) {
        super(String.format("Invalido! O periodo de tempo não deve ser negativo [%d]", ano));
    }
}
