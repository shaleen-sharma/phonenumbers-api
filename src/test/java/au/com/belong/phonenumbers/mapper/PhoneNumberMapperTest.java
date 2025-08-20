package au.com.belong.phonenumbers.mapper;

import au.com.belong.phonenumbers.exception.PhoneNumberException;
import au.com.belong.phonenumbers.mapper.PhoneNumberMapper;
import au.com.belong.phonenumbers.model.PhoneNumberStatus;
import au.com.belong.phonenumbers.model.PhoneUser;
import au.com.belong.phonenumbers.model.UserPhoneNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class PhoneNumberMapperTest {

    @InjectMocks
    private PhoneNumberMapper phoneNumberMapper;
    private final int mockUserId = 1;
    private final long mockNumber = 610000000L;

    @Test
    @DisplayName("Should create a list of PhoneNumber objects")
    void toPhoneNumberList_whenDone_thenSuccess() {
        var phoneNumbers = phoneNumberMapper.toPhoneNumberList(getListOfUserPhoneNumber());
        assertThat(phoneNumbers).isNotNull();
        assertThat(phoneNumbers.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Should create a toUserPhoneNumberDto object")
    void toUserPhoneNumberDto_whenDone_thenSuccess() {
        var userPhoneNumberDto = phoneNumberMapper
                .toUserPhoneNumberDto(getPhoneUser(),getListOfUserPhoneNumber() );
        assertThat(userPhoneNumberDto).isNotNull();
        assertThat(userPhoneNumberDto.getUserId()).isEqualTo(mockUserId);
    }

    @Test
    @DisplayName("Should return empty list when number list is null")
    void givenNullListToPhoneNumberList_whenDone_thenReturnEmptyList() {
        var phoneNumberList = phoneNumberMapper
                .toPhoneNumberList(null);
        assertThat(phoneNumberList).isNotNull();
        assertThat(phoneNumberList.size()).isEqualTo(0);
    }

        private List<UserPhoneNumber> getListOfUserPhoneNumber(){
        return List.of(UserPhoneNumber.builder()
                        .userId(mockUserId)
                        .number(mockNumber)
                        .status(PhoneNumberStatus.ACTIVE)
                        .build());
    }

    private PhoneUser getPhoneUser(){
        return PhoneUser.builder()
                .userId(mockUserId)
                .fullname("Test")
                .build();
    }

}