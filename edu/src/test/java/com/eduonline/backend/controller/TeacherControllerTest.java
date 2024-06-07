package com.eduonline.backend.controller;

import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.model.dto.user.StudentLoginUseVerifiedCodeRequest;
import com.eduonline.backend.model.dto.user.TeacherLoginRequest;
import com.eduonline.backend.model.dto.user.TeacherModifyRequest;
import com.eduonline.backend.model.dto.user.TeacherRegisterRequest;
import com.eduonline.backend.model.entity.Teacher;
import com.eduonline.backend.model.vo.user.TeacherListVO;
import com.eduonline.backend.model.vo.user.TeacherVO;
import com.eduonline.backend.service.TeacherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;

class TeacherControllerTest {
    @Mock
    TeacherService teacherService;
    @InjectMocks
    TeacherController teacherController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTeacherRegister() {
        when(teacherService.register(any(TeacherRegisterRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = teacherController.teacherRegister(new TeacherRegisterRequest());
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testTeacherLogin() {
        when(teacherService.login(any(TeacherLoginRequest.class), any(HttpServletRequest.class))).thenReturn(new BaseResponse<String>(0, "data", "message"));

        BaseResponse<String> result = teacherController.teacherLogin(new TeacherLoginRequest(), null);
        Assertions.assertEquals(new BaseResponse<String>(0, "data", "message"), result);
    }

    @Test
    void testTeacherModify() {
        when(teacherService.modify(any(TeacherModifyRequest.class), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = teacherController.teacherModify(new TeacherModifyRequest(), null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testGetLoginTeacher() {
        when(teacherService.getLoginTeacher(any(HttpServletRequest.class))).thenReturn(new Teacher());

        BaseResponse<TeacherVO> result = teacherController.getLoginTeacher(null);
        Assertions.assertEquals(new BaseResponse<TeacherVO>(0, new TeacherVO(), "message"), result);
    }

    @Test
    void testChangeStatus() {
        when(teacherService.changeStatus(anyInt(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = teacherController.changeStatus(Integer.valueOf(0), "teacherId", null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testList() {
        when(teacherService.listTeachers(anyInt(), anyInt(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<TeacherListVO>(0, new TeacherListVO(), "message"));

        BaseResponse<TeacherListVO> result = teacherController.list(Integer.valueOf(0), Integer.valueOf(0), null);
        Assertions.assertEquals(new BaseResponse<TeacherListVO>(0, new TeacherListVO(), "message"), result);
    }

    @Test
    void testLogout() {
        when(teacherService.logout(any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = teacherController.logout(null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testTeacherSendVerifiedCode() {
        when(teacherService.sendVerifiedCode(anyString())).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = teacherController.teacherSendVerifiedCode("phone");
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testTeacherLoginUseVerifiedCode() {
        when(teacherService.loginUseVerifiedCode(any(StudentLoginUseVerifiedCodeRequest.class), any(HttpServletRequest.class))).thenReturn(new BaseResponse<String>(0, "data", "message"));

        BaseResponse<String> result = teacherController.teacherLoginUseVerifiedCode(new StudentLoginUseVerifiedCodeRequest(), null);
        Assertions.assertEquals(new BaseResponse<String>(0, "data", "message"), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme