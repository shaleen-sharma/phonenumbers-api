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
public class UserPhoneNumberDto {
    private List<PhoneNumber> phoneNumbers;
    private int userId;
    private String userName;
}
