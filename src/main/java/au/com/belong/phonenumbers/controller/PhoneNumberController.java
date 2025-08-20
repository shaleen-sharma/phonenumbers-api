package au.com.belong.phonenumbers.controller;

import au.com.belong.phonenumbers.model.PhoneNumberDto;
import au.com.belong.phonenumbers.model.UserPhoneNumberDto;
import au.com.belong.phonenumbers.service.PhoneNumberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class PhoneNumberController {

    Logger logger = LoggerFactory.getLogger(PhoneNumberController.class);

    @Autowired
    PhoneNumberService phoneNumberService;

    @Autowired
    public PhoneNumberController(final PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    @GetMapping(value = "/user/{userId}/numbers")
    @ResponseStatus(HttpStatus.OK)
    public UserPhoneNumberDto getPhoneNumbersByCustomer(
            @PathVariable(name = "userId") @NotNull @Positive(message = "Invalid userId format") final int userId) {
        logger.info("getPhoneNumbersByCustomer called: userId={}: ", userId);
        return phoneNumberService.getPhoneNumbersByCustomer(userId);
    }

    @GetMapping(value = "/numbers")
    @ResponseStatus(HttpStatus.OK)
    public PhoneNumberDto getAllPhoneNumbers(@RequestParam(defaultValue = "0") int pageNo,
                                             @RequestParam(defaultValue = "3") int pageSize) {
        logger.info("getAllPhoneNumbers called");
        return phoneNumberService.getAllPhoneNumbers(pageNo, pageSize);
    }

    @PatchMapping(value = "/user/{userId}/numbers/{number}")
    @ResponseStatus(HttpStatus.OK)
    public void activatePhoneNumber(
            @PathVariable(name = "userId") @NotNull @Positive(message = "Invalid userId format") final int userId,
            @PathVariable(name = "number") @Valid @NotNull @Positive(message = "Invalid number format") final long number) {
        logger.info("activatePhoneNumber called: userId={}, number={}: ", userId, number);
        phoneNumberService.activatePhoneNumber(userId, number);
    }

}
