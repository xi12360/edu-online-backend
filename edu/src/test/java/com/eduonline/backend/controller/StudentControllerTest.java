package com.eduonline.backend.controller;

import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.model.dto.user.StudentLoginRequest;
import com.eduonline.backend.model.dto.user.StudentLoginUseVerifiedCodeRequest;
import com.eduonline.backend.model.dto.user.StudentModifyRequest;
import com.eduonline.backend.model.dto.user.StudentRegisterRequest;
import com.eduonline.backend.model.entity.Student;
import com.eduonline.backend.model.vo.user.StudentListVO;
import com.eduonline.backend.model.vo.user.StudentVO;
import com.eduonline.backend.service.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;

class StudentControllerTest {
    @Mock
    StudentService studentService;
    @InjectMocks
    StudentController studentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testStudentLogin() {
        when(studentService.login(any(StudentLoginRequest.class), any(HttpServletRequest.class))).thenReturn(new BaseResponse<String>(0, "data", "message"));

        BaseResponse<String> result = studentController.studentLogin(new StudentLoginRequest(), null);
        Assertions.assertEquals(new BaseResponse<String>(0, "data", "message"), result);
    }

    @Test
    void testStudentSendVerifiedCode() {
        when(studentService.sendVerifiedCode(anyString())).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = studentController.studentSendVerifiedCode("phone");
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testStudentLoginUseVerifiedCode() {
        when(studentService.loginUseVerifiedCode(any(StudentLoginUseVerifiedCodeRequest.class), any(HttpServletRequest.class))).thenReturn(new BaseResponse<String>(0, "data", "message"));

        BaseResponse<String> result = studentController.studentLoginUseVerifiedCode(new StudentLoginUseVerifiedCodeRequest(), null);
        Assertions.assertEquals(new BaseResponse<String>(0, "data", "message"), result);
    }

    @Test
    void testStudentRegister() {
        when(studentService.register(any(StudentRegisterRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = studentController.studentRegister(new StudentRegisterRequest());
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testStudentModify() {
        when(studentService.modify(any(StudentModifyRequest.class), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = studentController.studentModify(new StudentModifyRequest(), null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testGetLoginStudent() {
        when(studentService.getLoginStudent(any(HttpServletRequest.class))).thenReturn(new Student());

        BaseResponse<StudentVO> result = studentController.getLoginStudent(null);
        Assertions.assertEquals(new BaseResponse<StudentVO>(0, new StudentVO(), "message"), result);
    }

    @Test
    void testChangeStatus() {
        when(studentService.changeStatus(anyInt(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = studentController.changeStatus(Integer.valueOf(0), "studentId", null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testList() {
        when(studentService.listStudents(anyInt(), anyInt(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<StudentListVO>(0, new StudentListVO(), "message"));

        BaseResponse<StudentListVO> result = studentController.list(Integer.valueOf(0), Integer.valueOf(0), null);
        Assertions.assertEquals(new BaseResponse<StudentListVO>(0, new StudentListVO(), "message"), result);
    }

    @Test
    void testLogout() {
        when(studentService.logout(any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = studentController.logout(null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme