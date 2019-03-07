package test.valekseenko.exceptions;

public class AccountNotFoundException extends TransactionServiceException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
