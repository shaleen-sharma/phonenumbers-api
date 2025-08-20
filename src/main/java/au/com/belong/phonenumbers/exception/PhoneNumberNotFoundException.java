package au.com.belong.phonenumbers.exception;

public class PhoneNumberNotFoundException extends RuntimeException {
    public PhoneNumberNotFoundException(String err) {
        super(err);
    }
}
