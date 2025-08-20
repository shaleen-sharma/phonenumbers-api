package au.com.belong.phonenumbers.exception;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException(String err) {
        super(err);
    }
}
