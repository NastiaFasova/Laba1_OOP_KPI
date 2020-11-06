package application.exceptions;

public class EmptyBucketException extends RuntimeException {
    public EmptyBucketException(String message) {
        super(message);
    }
}
