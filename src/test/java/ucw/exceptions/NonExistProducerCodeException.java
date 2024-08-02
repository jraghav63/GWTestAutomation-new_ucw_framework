package ucw.exceptions;

public class NonExistProducerCodeException extends IllegalArgumentException {
    public NonExistProducerCodeException(String msg) {
        super(msg);
    }
}
