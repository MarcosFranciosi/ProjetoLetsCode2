package oscar.exceptions;

public class IdentificadorNullException extends RuntimeException {
    public IdentificadorNullException() { super ("A identificação não pode estar em branco");}
}
