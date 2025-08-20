package au.com.belong.phonenumbers.service;

import au.com.belong.phonenumbers.exception.InvalidUserException;
import au.com.belong.phonenumbers.exception.PhoneNumberNotFoundException;
import au.com.belong.phonenumbers.model.PhoneNumberStatus;
import au.com.belong.phonenumbers.repository.PhoneUserRepository;
import au.com.belong.phonenumbers.repository.UserPhoneNumberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional // Rolls back changes after each test
class PhoneNumberServiceIT {

    @Autowired
    private PhoneNumberService phoneNumberService;

    @Autowired
    private PhoneUserRepository phoneUserRepository;

    @Autowired
    private UserPhoneNumberRepository userPhoneNumberRepository;

    @Test
    @DisplayName("Should get all phone numbers from the DB")
    void getAllNumbers_whenDone_thenSuccess() {
        var phoneNumberDto = phoneNumberService.getAllPhoneNumbers(0,3);
        assertThat(phoneNumberDto).isNotNull();
        assertThat(phoneNumberDto.getPhoneNumbers().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Should get all phone numbers for a user from the DB")
    void getPhoneNumbersByUser_thenSuccess() {
        var userPhoneNumberDto = phoneNumberService.getPhoneNumbersByCustomer(11);
        assertThat(userPhoneNumberDto).isNotNull();
        assertThat(userPhoneNumberDto.getPhoneNumbers().size()).isEqualTo(3);
        assertThat(userPhoneNumberDto.getUserId()).isEqualTo(11);
    }

    @Test
    @DisplayName("Should Activate a number by userId")
    void activatePhoneNumber_thenSuccess() {
        var statusBefore = userPhoneNumberRepository.findByNumberAndUserId(61466666666L, 33)
                .get().getStatus();
        assertThatNoException().isThrownBy(() -> phoneNumberService.activatePhoneNumber(33, 61466666666L));
        var statusAfter = userPhoneNumberRepository.findByNumberAndUserId(61466666666L, 33)
                .get().getStatus();

        Assertions.assertEquals(statusBefore, PhoneNumberStatus.INACTIVE);
        Assertions.assertEquals(statusAfter, PhoneNumberStatus.ACTIVE);
    }

    @Test
    @DisplayName("Should fail to get all phone numbers for a user that doesn't exist")
    void findUserById_thenNotFound() {
        assertThatThrownBy(() -> phoneNumberService.getPhoneNumbersByCustomer(123))
                .isInstanceOf(InvalidUserException.class)
                .hasMessageContaining("User Not found.");
    }

    @Test
    @DisplayName("Should fail to Activate a number if the number doesnt belong to the user")
    void testActivatePhoneNumber_Fail() {
        assertThatThrownBy(() -> phoneNumberService.activatePhoneNumber(33, 61466666669L))
                .isInstanceOf(PhoneNumberNotFoundException.class)
                .hasMessageContaining("Provided number does not belong to the user");
    }

}