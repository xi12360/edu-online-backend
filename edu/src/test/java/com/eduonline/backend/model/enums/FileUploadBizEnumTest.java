package com.eduonline.backend.model.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class FileUploadBizEnumTest {

    FileUploadBizEnum fileUploadBizEnum = FileUploadBizEnum.USER_AVATAR;

    @Test
    void testGetValues() {
        List<String> result = FileUploadBizEnum.getValues();
        Assertions.assertEquals(Arrays.<String>asList("replaceMeWithExpectedResult"), result);
    }

    @Test
    void testGetEnumByValue() {
        FileUploadBizEnum result = FileUploadBizEnum.getEnumByValue("value");
        Assertions.assertEquals(FileUploadBizEnum.USER_AVATAR, result);
    }

    @Test
    void testGetValue() {
        String result = fileUploadBizEnum.getValue();
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetText() {
        String result = fileUploadBizEnum.getText();
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme