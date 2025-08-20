package au.com.belong.phonenumbers.repository;

import au.com.belong.phonenumbers.model.PhoneNumberStatus;
import au.com.belong.phonenumbers.model.UserPhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.AssertionErrors;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class UserPhoneNumberRepositoryTest {

    @Autowired
    private UserPhoneNumberRepository userPhoneNumberRepository;
    private List<UserPhoneNumber> userPhoneNumbers;
    private UserPhoneNumber userPhoneNumber1;
    private UserPhoneNumber userPhoneNumber2;

    @BeforeEach
    void setUp() {
        userPhoneNumberRepository.deleteAll();
        loadTestDB();
    }

    @Test
    @DisplayName("Should find all the phone numbers that exist in DB")
    void whenGetAllPhoneNumbers_thenFindAll_success() {
        AssertionErrors.assertEquals("Elements found", 2, userPhoneNumberRepository.findAll().size());
    }

    @Test
    @DisplayName("Should find the user and phone number details by userId")
    void givenUserID_whenFindByUserId_thenSuccess() {
        var existsPhoneUser = userPhoneNumberRepository.findByUserId(11);
        AssertionErrors.assertTrue("User Id found", existsPhoneUser.isPresent());
    }

    @Test
    @DisplayName("Should find the user and phone number details by userId and number")
    void givenUserIDAndNumber_whenFindByUserIDAndNumber_thenSuccess() {
        Optional<UserPhoneNumber> existsPhoneUser = userPhoneNumberRepository.findByNumberAndUserId(61411111111L,11);
        AssertionErrors.assertTrue("Phone Number found successfully", existsPhoneUser.isPresent());
    }

    @Test
    @DisplayName("Should fail to find a non existing userId when searched by userId")
    void givenUnknownUserID_whenFindByUserID_thenFail() {
        var existsPhoneUser = userPhoneNumberRepository.findByUserId(111);
        AssertionErrors.assertTrue("User Id not found", existsPhoneUser.get().isEmpty());
    }

    @Test
    @DisplayName("Should fail if the number does not belong to the user")
    void givenValidUserIdAndValidNumber_whenFindByUserIdAndNumber_thenFail() {
        Optional<UserPhoneNumber> existsPhoneUser = userPhoneNumberRepository.findByNumberAndUserId(61411111112L,11);
        AssertionErrors.assertFalse("Phone Number Not found for the user", existsPhoneUser.isPresent());
    }

    @Test
    @DisplayName("Should add new record in User_Phone_Number table")
    void givenValidUserIdAndNumber_whenAdd_thenSaveRecord() {
         userPhoneNumberRepository.save(
                UserPhoneNumber.builder()
                        .userId(44)
                        .number(61411111144L)
                        .status(PhoneNumberStatus.ACTIVE)
                        .build());
        Optional<UserPhoneNumber> existsPhoneUser = userPhoneNumberRepository.findByNumberAndUserId(61411111144L,44);
        AssertionErrors.assertTrue("Phone Number added", existsPhoneUser.isPresent());
    }
    @Test
    @DisplayName("Should be able to update the status of a number in the DB")
    void givenValidUserIdAndNumber_whenUpdateStatus_thenStatusUpdated() {
        Optional<UserPhoneNumber> userPhoneNumber = userPhoneNumberRepository.findByNumberAndUserId(61411111111L,11);
        var statusBefore = userPhoneNumber.get().getStatus();
        userPhoneNumber.get().setStatus(PhoneNumberStatus.ACTIVE);
        userPhoneNumberRepository.save(userPhoneNumber.get());
        var statusAfter = userPhoneNumberRepository.findByNumberAndUserId(61411111111L,11).get().getStatus();
        AssertionErrors.assertEquals("Status before update",statusBefore.toString(), PhoneNumberStatus.INACTIVE.name());
        AssertionErrors.assertEquals("Status after update", statusAfter.toString(), PhoneNumberStatus.ACTIVE.name());
    }

    private void loadTestDB(){
        userPhoneNumber1 = UserPhoneNumber.builder()
                .number(61411111111L)
                .userId(11)
                .status(PhoneNumberStatus.INACTIVE)
                .build();
        userPhoneNumber2 = UserPhoneNumber.builder()
                .number(61455555555L)
                .status(PhoneNumberStatus.INACTIVE)
                .userId(22)
                .build();
        userPhoneNumbers = List.of(userPhoneNumber1, userPhoneNumber2);
        userPhoneNumberRepository.saveAll(userPhoneNumbers);
    }
}
