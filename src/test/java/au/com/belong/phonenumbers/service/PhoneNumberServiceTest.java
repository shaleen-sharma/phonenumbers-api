package au.com.belong.phonenumbers.service;

import au.com.belong.phonenumbers.exception.InvalidUserException;
import au.com.belong.phonenumbers.exception.PhoneNumberNotFoundException;
import au.com.belong.phonenumbers.mapper.PhoneNumberMapper;
import au.com.belong.phonenumbers.model.PhoneNumberStatus;
import au.com.belong.phonenumbers.model.PhoneUser;
import au.com.belong.phonenumbers.model.UserPhoneNumber;
import au.com.belong.phonenumbers.model.UserPhoneNumberDto;
import au.com.belong.phonenumbers.repository.PhoneUserRepository;
import au.com.belong.phonenumbers.repository.UserPhoneNumberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhoneNumberServiceTest {

    @InjectMocks
    private final PhoneNumberService phoneNumberService = new PhoneNumberServiceImpl();

    @Mock
    private PhoneUserRepository phoneUserRepository;

    @Mock
    private UserPhoneNumberRepository userPhoneNumberRepository;

    @Mock
    private PhoneNumberMapper phoneNumberMapper;

    private final Page mockPage = Mockito.mock(Page.class);

    private final int mockUserId = 1;

    private final PhoneUser mockPhoneUser = PhoneUser.builder()
            .userId(mockUserId)
            .fullname("Test").build();

    private final long mockNumber = 610000000L;

    private final UserPhoneNumber mockUserPhoneNumber = UserPhoneNumber.builder()
            .userId(mockUserId)
            .number(mockNumber)
            .status(PhoneNumberStatus.INACTIVE)
            .build();


    @Test
    @DisplayName("Should get all phone numbers from the DB")
    void getAllNumbers_whenDone_thenSuccess() {
        when(userPhoneNumberRepository.findAll(PageRequest.of(0, 3))).thenReturn(mockPage);
        var phoneNumberDto = phoneNumberService.getAllPhoneNumbers(0,3);
        assertThat(phoneNumberDto).isNotNull();
    }

    @Test
    @DisplayName("Should get all phone numbers for a user from the DB")
    void getPhoneNumbersByUser_thenSuccess() {
        when(phoneUserRepository.findById(mockUserId)).thenReturn(Optional.of(mockPhoneUser));
        when(userPhoneNumberRepository.findByUserId(1)).thenReturn(Optional.of(List.of(mockUserPhoneNumber)));
        when(phoneNumberMapper.toUserPhoneNumberDto(any(),any())).thenReturn(UserPhoneNumberDto.builder().userId(1).build());

        var userPhoneNumberDto = phoneNumberService.getPhoneNumbersByCustomer(mockUserId);
        assertThat(userPhoneNumberDto).isNotNull();
        assertThat(userPhoneNumberDto.getUserId()).isEqualTo(mockUserId);
    }

    @Test
    @DisplayName("Should Activate a number by userId")
    void activatePhoneNumber_thenSuccess() {
        when(phoneUserRepository.findById(mockUserId)).thenReturn(Optional.of(mockPhoneUser));
        when(userPhoneNumberRepository.findByNumberAndUserId(mockNumber, mockUserId)).thenReturn(Optional.of(mockUserPhoneNumber));
        when(userPhoneNumberRepository.save(mockUserPhoneNumber)).thenReturn(mockUserPhoneNumber);

        assertThatNoException().isThrownBy(() -> phoneNumberService.activatePhoneNumber(mockUserId, mockNumber));
        verify(userPhoneNumberRepository, times(1)).save(mockUserPhoneNumber);
    }

    @Test
    @DisplayName("Should fail to get all phone numbers for a user that doesn't exist")
    void findUserById_thenNotFound() {
        when(phoneUserRepository.findById(mockUserId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> phoneNumberService.getPhoneNumbersByCustomer(mockUserId))
                .isInstanceOf(InvalidUserException.class)
                .hasMessageContaining("User Not found.");
    }

    @Test
    @DisplayName("Should fail to Activate a number if the number doesnt belong to the user")
    void testActivatePhoneNumber_Fail() {
        when(phoneUserRepository.findById(mockUserId)).thenReturn(Optional.of(mockPhoneUser));
        when(userPhoneNumberRepository.findByNumberAndUserId(mockNumber, mockUserId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> phoneNumberService.activatePhoneNumber(mockUserId, mockNumber))
                .isInstanceOf(PhoneNumberNotFoundException.class)
                .hasMessageContaining("Provided number does not belong to the user");
    }

}