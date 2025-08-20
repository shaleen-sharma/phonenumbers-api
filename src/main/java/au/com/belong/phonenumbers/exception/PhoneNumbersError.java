package au.com.belong.phonenumbers.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PhoneNumbersError {

	private HttpStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private String message;

	public PhoneNumbersError(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
}
