package au.com.belong.phonenumbers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhoneNumber {
    private Long number;
    private PhoneNumberStatus status;
}
