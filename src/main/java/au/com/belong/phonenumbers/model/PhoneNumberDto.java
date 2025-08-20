package au.com.belong.phonenumbers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhoneNumberDto {
    private List<PhoneNumber> phoneNumbers;
}
