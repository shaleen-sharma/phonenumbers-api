package au.com.belong.phonenumbers.repository;

import au.com.belong.phonenumbers.model.PhoneUser;
import au.com.belong.phonenumbers.repository.PhoneUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.AssertionErrors;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class PhoneUserRepositoryTest {


    @Autowired
    private PhoneUserRepository phoneUserRepository;

    @BeforeEach
    void setUp() {
        phoneUserRepository.deleteAll();
        loadTestData();
    }

    @Test
    @DisplayName("Should find all the users that exist in DB")
    void whenFindAllUsers_thenSuccess() {
        AssertionErrors.assertEquals("", 2, phoneUserRepository.findAll().size());
    }

    @Test
    @DisplayName("Should find the userId which exists in DB")
    void givenUserID_whenFindByUserId_thenSuccess() {
        Optional<PhoneUser> existsPhoneUser = phoneUserRepository.findById(11);
        AssertionErrors.assertTrue("User Id found", existsPhoneUser.isPresent());
    }

    @Test
    @DisplayName("Should not find the userId which does not exist in DB")
    void givenNonExistentUserID_whenFindByUserId_thenFail() {
        int nonExistentUserId = 1234;
        Optional<PhoneUser> existsPhoneUser = phoneUserRepository.findById(nonExistentUserId);
        AssertionErrors.assertFalse("User does not exist", existsPhoneUser.isPresent());
    }

    @Test
    @DisplayName("Should add multiple new users in DB")
    void givenUserData_whenAddUsers_thenSuccess() {
        phoneUserRepository.saveAll(List.of(
                PhoneUser.builder()
                        .userId(11)
                        .fullname("Bob A")
                        .build(),
                PhoneUser.builder()
                        .userId(22)
                        .fullname("Bob B")
                        .build()));
        AssertionErrors.assertTrue("User Id found", phoneUserRepository.findById(11).isPresent());
        AssertionErrors.assertTrue("User Id found", phoneUserRepository.findById(22).isPresent());
    }

    @Test
    @DisplayName("Should add a single new user in DB")
    void givenUserData_whenAddOneUser_thenSuccess() {
        phoneUserRepository.save(
                PhoneUser.builder()
                        .userId(11)
                        .fullname("Bob A")
                        .build());
        AssertionErrors.assertTrue("User Id found", phoneUserRepository.findById(11).isPresent());
    }
    private void loadTestData(){
        phoneUserRepository.saveAll(List.of(
                PhoneUser.builder()
                        .userId(11)
                        .fullname("Bob A")
                        .build(),
                PhoneUser.builder()
                        .userId(22)
                        .fullname("Bob B")
                        .build()));
    }
}
