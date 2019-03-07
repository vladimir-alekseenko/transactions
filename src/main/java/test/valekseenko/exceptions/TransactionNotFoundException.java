package test.valekseenko.exceptions;

public class TransactionNotFoundException extends TransactionServiceException {
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
