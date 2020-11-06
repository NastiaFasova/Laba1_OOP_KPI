package application.exceptions;

public class OutOfBoundsDiscountException extends RuntimeException {
    public OutOfBoundsDiscountException(String message) {
        super(message);
    }
}
