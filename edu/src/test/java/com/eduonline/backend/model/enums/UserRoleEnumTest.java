package com.eduonline.backend.model.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class UserRoleEnumTest {


    UserRoleEnum userRoleEnum = UserRoleEnum.USER;

    @Test
    void testGetValues() {
        List<String> result = UserRoleEnum.getValues();
        Assertions.assertEquals(Arrays.<String>asList("replaceMeWithExpectedResult"), result);
    }

    @Test
    void testGetEnumByValue() {
        UserRoleEnum result = UserRoleEnum.getEnumByValue("value");
        Assertions.assertEquals(UserRoleEnum.USER, result);
    }

    @Test
    void testGetValue() {
        String result = userRoleEnum.getValue();
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetText() {
        String result = userRoleEnum.getText();
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme