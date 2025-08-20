package au.com.belong.phonenumbers.controller;

import au.com.belong.phonenumbers.exception.InvalidUserException;
import au.com.belong.phonenumbers.exception.PhoneNumberNotFoundException;
import au.com.belong.phonenumbers.service.PhoneNumberService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc

public class PhoneNumberControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PhoneNumberService phoneNumberService;


    @Test
    @DisplayName("Should get all phone numbers from the DB")
    void getAllNumbers_whenDone_then200() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/numbers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andDo(print());

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.phoneNumbers[0]", Matchers.notNullValue()));
    }

    @Test
    @DisplayName("Should get all phone numbers for a user from the DB")
    void getAllNumbersByUserId_whenDone_then200() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/user/11/numbers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andDo(print());

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.phoneNumbers[0]", Matchers.notNullValue()));
        resultActions.andExpect(jsonPath("$.userId", Matchers.notNullValue()));
    }

    @Test
    @DisplayName("Should Activate a number by userId")
    void activateNumbersByUserId_whenDone_then200() throws Exception {
        ResultActions resultActions = mockMvc.perform(patch("/user/33/numbers/61466666666")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andDo(print());

        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should fail to Activate a number if the number doesnt belong to the userId")
    void activateNumbersByInvalidUserId_whenDone_then400() throws Exception {
        ResultActions resultActions = mockMvc.perform(patch("/user/22/numbers/61466666666")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andDo(print());

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(result
                -> Assertions.assertTrue(result.getResolvedException() instanceof PhoneNumberNotFoundException));
        resultActions.andExpect(result
                -> Assertions.assertEquals(result.getResolvedException().getMessage(),
                "Provided number does not belong to the user"));
    }

    @Test
    @DisplayName("Should fail to Activate a number if the userId does not exist")
    void activateNumbersByNonExistentUserId_whenDone_then400() throws Exception {
        ResultActions resultActions = mockMvc.perform(patch("/user/123/numbers/61466666666")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andDo(print());

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(result
                -> Assertions.assertTrue(result.getResolvedException() instanceof InvalidUserException));
        resultActions.andExpect(result
                -> Assertions.assertEquals(result.getResolvedException().getMessage(),
                "User Not found."));
    }

    @Test
    @DisplayName("Should fail to get all phone numbers for a user that doesn't exist")
    void getAllNumbersByNonExistentUserId_whenDone_then400() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/user/123/numbers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andDo(print());

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(result
                -> Assertions.assertTrue(result.getResolvedException() instanceof InvalidUserException));
        resultActions.andExpect(result
                -> Assertions.assertEquals(result.getResolvedException().getMessage(),
                "User Not found."));
    }
}