package au.com.belong.phonenumbers;

import au.com.belong.phonenumbers.exception.InvalidUserException;
import au.com.belong.phonenumbers.exception.PhoneNumberNotFoundException;
import au.com.belong.phonenumbers.exception.PhoneNumbersError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class PhoneNumbersControllerAdvice
        extends ResponseEntityExceptionHandler {

    Logger logger = LoggerFactory.getLogger(PhoneNumbersControllerAdvice.class);

    @ExceptionHandler(value
            = {InvalidUserException.class, PhoneNumberNotFoundException.class})
    protected ResponseEntity<Object> handleBadRequests(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Invalid request, " + ex.getMessage();
        logger.error(bodyOfResponse, ex);
        return buildResponseEntity(new PhoneNumbersError(HttpStatus.BAD_REQUEST
				, bodyOfResponse));
    }

    @ExceptionHandler(value
            = RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeExceptions(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Error Occurred, " + ex.getMessage();
        logger.error(bodyOfResponse, ex);
        return buildResponseEntity(new PhoneNumbersError(HttpStatus.INTERNAL_SERVER_ERROR, bodyOfResponse));
    }

    private ResponseEntity<Object> buildResponseEntity(PhoneNumbersError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}