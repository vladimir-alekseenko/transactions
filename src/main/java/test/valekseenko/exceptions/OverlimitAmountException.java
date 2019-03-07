package test.valekseenko.exceptions;

public class OverlimitAmountException extends TransactionServiceException {
    public OverlimitAmountException(String message) {
        super(message);
    }
}
