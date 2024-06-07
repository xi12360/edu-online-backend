package com.eduonline.backend.controller;

import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.model.vo.msg.MsgSystemListVO;
import com.eduonline.backend.service.MsgSystemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;

class SysMsgControllerTest {
    @Mock
    MsgSystemService msgSystemService;
    @InjectMocks
    SysMsgController sysMsgController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMsgSystem() {
        when(msgSystemService.getMsgSystem(anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<MsgSystemListVO>(0, new MsgSystemListVO(), "message"));

        BaseResponse<MsgSystemListVO> result = sysMsgController.getMsgSystem("userId", null);
        Assertions.assertEquals(new BaseResponse<MsgSystemListVO>(0, new MsgSystemListVO(), "message"), result);
    }

    @Test
    void testGetMsgSystemNum() {
        when(msgSystemService.getMsgSystemNum(anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = sysMsgController.getMsgSystemNum("userId", null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme