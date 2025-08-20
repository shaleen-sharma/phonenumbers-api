package au.com.belong.phonenumbers.service;

import au.com.belong.phonenumbers.exception.InvalidUserException;
import au.com.belong.phonenumbers.exception.PhoneNumberNotFoundException;
import au.com.belong.phonenumbers.mapper.PhoneNumberMapper;
import au.com.belong.phonenumbers.model.PhoneNumberDto;
import au.com.belong.phonenumbers.model.PhoneNumberStatus;
import au.com.belong.phonenumbers.model.PhoneUser;
import au.com.belong.phonenumbers.model.UserPhoneNumber;
import au.com.belong.phonenumbers.model.UserPhoneNumberDto;
import au.com.belong.phonenumbers.repository.PhoneUserRepository;
import au.com.belong.phonenumbers.repository.UserPhoneNumberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface PhoneNumberService {
    PhoneNumberDto getAllPhoneNumbers(int pageNo, int pageSize);

    UserPhoneNumberDto getPhoneNumbersByCustomer(int userId);

    void activatePhoneNumber(int userId, long number);
}

@Service
class PhoneNumberServiceImpl implements PhoneNumberService {

    @Autowired
    UserPhoneNumberRepository userPhoneNumberRepository;
    @Autowired
    PhoneUserRepository phoneUserRepository;
    @Autowired
    PhoneNumberMapper phoneNumberMapper;

    Logger logger = LoggerFactory.getLogger(PhoneNumberServiceImpl.class);

    @Override
    public PhoneNumberDto getAllPhoneNumbers(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<UserPhoneNumber> pagedResult = userPhoneNumberRepository.findAll(paging);
            return PhoneNumberDto.builder()
                    .phoneNumbers(phoneNumberMapper.toPhoneNumberList(pagedResult.getContent()))
                    .build();
    }

    @Override
    public UserPhoneNumberDto getPhoneNumbersByCustomer(int userId) {
        // findByUserId() returns Optional
        return phoneNumberMapper.toUserPhoneNumberDto(getUserDetails(userId),
                userPhoneNumberRepository.findByUserId(userId).get());
    }

    @Override
    public void activatePhoneNumber(int userId, long number) {
        //Check if user exists
        getUserDetails(userId);
        // Check if the number belongs to the user provided
        var phoneNumberDetails = userPhoneNumberRepository.findByNumberAndUserId(number, userId);
        // If record found then set status Active otherwise throw exception
        phoneNumberDetails.ifPresentOrElse(n -> n.setStatus(PhoneNumberStatus.ACTIVE), () -> {
            logger.warn("Phone number does not belong to the user userId={}, number={}", userId, number);
            throw new PhoneNumberNotFoundException("Provided number does not belong to the user");
        });
        userPhoneNumberRepository.save(phoneNumberDetails.get());
    }

    /**
     * Checks if the userId exists in the db and returns it if it does
     *
     * @param userId
     */
    private PhoneUser getUserDetails(int userId) {
        var phoneUser = phoneUserRepository.findById(userId);
        if (phoneUser.isEmpty()) {
            logger.warn("User Not found. userId={}", userId);
            throw new InvalidUserException("User Not found.");
        }
        return phoneUser.get();
    }
}

