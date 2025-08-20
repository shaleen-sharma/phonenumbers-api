package au.com.belong.phonenumbers.controller;

import au.com.belong.phonenumbers.exception.InvalidUserException;
import au.com.belong.phonenumbers.exception.PhoneNumberNotFoundException;
import au.com.belong.phonenumbers.model.PhoneNumber;
import au.com.belong.phonenumbers.model.PhoneNumberDto;
import au.com.belong.phonenumbers.model.UserPhoneNumberDto;
import au.com.belong.phonenumbers.service.PhoneNumberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PhoneNumberControllerTest {

    private final int mockUserId = 1;
    private final long mockNumber = 610000000L;
    private final PhoneNumberDto mockPhoneNumberDto = PhoneNumberDto.builder()
            .phoneNumbers(List.of(new PhoneNumber()))
            .build();
    private final UserPhoneNumberDto mockUserPhoneNumberDto = UserPhoneNumberDto.builder()
            .phoneNumbers(List.of(new PhoneNumber()))
            .userId(mockUserId)
            .build();
    @InjectMocks
    private PhoneNumberController phoneNumberController;
    @Mock
    private PhoneNumberService phoneNumberService;

    @Test
    @DisplayName("Should get all phone numbers from the DB")
    void getAllNumbers_whenDone_then200() throws Exception {

        when(phoneNumberService.getAllPhoneNumbers(0,3)).thenReturn(mockPhoneNumberDto);

        var phoneNumberDto = phoneNumberController.getAllPhoneNumbers(0,3);

        assertThat(phoneNumberDto).isNotNull();
        assertThat(phoneNumberDto.getPhoneNumbers()).isNotNull();
    }

    @Test
    @DisplayName("Should get all phone numbers for a user from the DB")
    void getAllNumbersByUserId_whenDone_then200() throws Exception {

        when(phoneNumberService.getPhoneNumbersByCustomer(mockUserId)).thenReturn(mockUserPhoneNumberDto);

        var userPhoneNumberDto = phoneNumberController.getPhoneNumbersByCustomer(mockUserId);
        assertThat(userPhoneNumberDto).isNotNull();
        assertThat(userPhoneNumberDto.getUserId()).isEqualTo(mockUserId);
    }

    @Test
    @DisplayName("Should Activate a number by userId")
    void activateNumbersByUserId_whenDone_then200() throws Exception {
        doNothing().when(phoneNumberService).activatePhoneNumber(mockUserId, mockNumber);

        assertThatNoException().isThrownBy(() -> phoneNumberController.activatePhoneNumber(mockUserId, mockNumber));
        verify(phoneNumberService, times(1)).activatePhoneNumber(mockUserId, mockNumber);
    }

    @Test
    @DisplayName("Should fail to Activate a number if the number doesnt belong to the userId")
    void activateNumbersByInvalidUserId_whenDone_then400() throws Exception {
        doThrow(new PhoneNumberNotFoundException(""))
                .when(phoneNumberService).activatePhoneNumber(mockUserId, mockNumber);

        assertThatExceptionOfType(PhoneNumberNotFoundException.class).isThrownBy(()
                -> phoneNumberController.activatePhoneNumber(mockUserId, mockNumber));

    }

    @Test
    @DisplayName("Should fail to Activate a number if the userId does not exist")
    void activateNumbersByNonExistentUserId_whenDone_then400() throws Exception {
        doThrow(new InvalidUserException(""))
                .when(phoneNumberService).activatePhoneNumber(mockUserId, mockNumber);

        assertThatExceptionOfType(InvalidUserException.class).isThrownBy(()
                -> phoneNumberController.activatePhoneNumber(mockUserId, mockNumber));
    }

}