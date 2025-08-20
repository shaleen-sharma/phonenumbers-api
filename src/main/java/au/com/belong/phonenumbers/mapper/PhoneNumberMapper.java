package au.com.belong.phonenumbers.mapper;

import au.com.belong.phonenumbers.model.PhoneNumber;
import au.com.belong.phonenumbers.model.PhoneUser;
import au.com.belong.phonenumbers.model.UserPhoneNumber;
import au.com.belong.phonenumbers.model.UserPhoneNumberDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class PhoneNumberMapper {

    public UserPhoneNumberDto toUserPhoneNumberDto(PhoneUser user, List<UserPhoneNumber> userPhoneNumbers) {
        var userId = (user != null) ? user.getUserId() : 0;
        var userName = (user != null) ? user.getFullname() : null;

        return UserPhoneNumberDto.builder()
                .phoneNumbers(toPhoneNumberList(userPhoneNumbers))
                .userId(userId)
                .userName(userName)
                .build();
    }


    public List<PhoneNumber> toPhoneNumberList(List<UserPhoneNumber> userPhoneNumbers){
        if (userPhoneNumbers == null || userPhoneNumbers.isEmpty()) {
            return new ArrayList<PhoneNumber>();
        }
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
                userPhoneNumbers.forEach( u -> {
                    phoneNumbers.add(PhoneNumber.builder()
                            .number(u.getNumber())
                            .status(u.getStatus())
                            .build());
                });
        return phoneNumbers;
    }
}
