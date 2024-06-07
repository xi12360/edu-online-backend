package com.eduonline.backend.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.model.entity.Admin;
import com.eduonline.backend.model.entity.Student;
import com.eduonline.backend.model.entity.Teacher;
import com.eduonline.backend.model.vo.user.UserVO;
import com.eduonline.backend.service.AdminService;
import com.eduonline.backend.service.StudentService;
import com.eduonline.backend.service.TeacherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;

import static org.mockito.Mockito.*;

class GetLoginUserControllerTest {
    @Mock
    AdminService adminService;
    @Mock
    StudentService studentService;
    @Mock
    TeacherService teacherService;
    @Mock
    RedisTemplate redisTemplate;
    @InjectMocks
    GetLoginUserController getLoginUserController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetLoginUser() {
        when(adminService.getOne(any(Wrapper.class))).thenReturn(new Admin());
        when(studentService.getOne(any(Wrapper.class))).thenReturn(new Student());
        when(teacherService.getOne(any(Wrapper.class))).thenReturn(new Teacher());
        when(redisTemplate.opsForHash()).thenReturn(null);

        BaseResponse<UserVO> result = getLoginUserController.getLoginUser(null);
        Assertions.assertEquals(new BaseResponse<UserVO>(0, new UserVO(), "message"), result);
    }

    @Test
    void testLogout() {
        when(redisTemplate.opsForHash()).thenReturn(null);

        BaseResponse<Integer> result = getLoginUserController.logout(null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testStudentSendVerifiedCode() {
        when(studentService.sendVerifiedCode(anyString())).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = getLoginUserController.studentSendVerifiedCode("phone");
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme