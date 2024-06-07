package com.eduonline.backend.controller;

import com.eduonline.backend.common.BaseResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UploadControllerTest {
    UploadController uploadController = new UploadController();

    @Test
    void testUploadImg() {
        BaseResponse<String> result = uploadController.uploadImg(null);
        Assertions.assertEquals(new BaseResponse<String>(0, "data", "message"), result);
    }

    @Test
    void testUploadMaterial() {
        BaseResponse<String> result = uploadController.uploadMaterial(null);
        Assertions.assertEquals(new BaseResponse<String>(0, "data", "message"), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme