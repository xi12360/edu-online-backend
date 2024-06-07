package com.eduonline.backend.controller;

import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.model.dto.sysmsg.SendSysMsgRequest;
import com.eduonline.backend.model.dto.user.AdminLoginRequest;
import com.eduonline.backend.model.entity.Admin;
import com.eduonline.backend.service.AdminService;
import com.eduonline.backend.service.MsgSystemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;

class AdminControllerTest {
    @Mock
    AdminService adminService;
    @Mock
    MsgSystemService msgSystemService;
    @InjectMocks
    AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAdminLogin() {
        when(adminService.login(any(AdminLoginRequest.class), any(HttpServletRequest.class))).thenReturn(new BaseResponse<String>(0, "data", "message"));

        BaseResponse<String> result = adminController.adminLogin(new AdminLoginRequest(), null);
        Assertions.assertEquals(new BaseResponse<String>(0, "data", "message"), result);
    }

    @Test
    void testGetLoginAdmin() {
        when(adminService.getLoginAdmin(any(HttpServletRequest.class))).thenReturn(new Admin());

        BaseResponse<Admin> result = adminController.getLoginAdmin(null);
        Assertions.assertEquals(new BaseResponse<Admin>(0, new Admin(), "message"), result);
    }

    @Test
    void testSendSysMsg() {
        when(msgSystemService.sendSysMsg(any(SendSysMsgRequest.class), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = adminController.sendSysMsg(new SendSysMsgRequest(), null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testLogout() {
        when(adminService.logout(any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = adminController.logout(null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme