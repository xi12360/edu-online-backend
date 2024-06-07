package com.eduonline.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.model.dto.user.StudentLoginUseVerifiedCodeRequest;
import com.eduonline.backend.model.dto.user.TeacherLoginRequest;
import com.eduonline.backend.model.dto.user.TeacherModifyRequest;
import com.eduonline.backend.model.dto.user.TeacherRegisterRequest;
import com.eduonline.backend.model.entity.Teacher;
import com.eduonline.backend.model.vo.user.TeacherListVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author Acer
* @description 针对表【teacher】的数据库操作Service
* @createDate 2023-08-25 16:59:15
*/
public interface TeacherService extends IService<Teacher> {

    BaseResponse<String> login(TeacherLoginRequest teacherLoginRequest, HttpServletRequest request);

    BaseResponse<Integer> register(TeacherRegisterRequest teacherRegisterRequest);

    BaseResponse<Integer> modify(TeacherModifyRequest teacherModifyRequest, HttpServletRequest request);

    Teacher getLoginTeacher(HttpServletRequest request);

    BaseResponse<Integer> changeStatus(Integer status, String teacherId, HttpServletRequest request);

    BaseResponse<TeacherListVO> listTeachers(Integer current, Integer pageSize, HttpServletRequest request);

    BaseResponse<Integer> logout(HttpServletRequest request);

    BaseResponse<Integer> sendVerifiedCode(String phone);

    BaseResponse<String> loginUseVerifiedCode(StudentLoginUseVerifiedCodeRequest studentLoginUseVerifiedCodeRequest, HttpServletRequest request);
}
