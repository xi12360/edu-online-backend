package com.eduonline.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.model.dto.user.*;
import com.eduonline.backend.model.entity.Student;
import com.eduonline.backend.model.vo.user.StudentListVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author Acer
* @description 针对表【student】的数据库操作Service
* @createDate 2023-08-25 16:59:15
*/
public interface StudentService extends IService<Student> {

    BaseResponse<String> login(StudentLoginRequest studentService, HttpServletRequest request);

    BaseResponse<Integer> register(StudentRegisterRequest studentRegisterRequest);

    BaseResponse<Integer> modify(StudentModifyRequest studentModifyRequest, HttpServletRequest request);

    Student getLoginStudent(HttpServletRequest request);

    BaseResponse<Integer> changeStatus(Integer status, String studentId, HttpServletRequest request);

    BaseResponse<StudentListVO> listStudents(Integer current, Integer pageSize, HttpServletRequest request);

    BaseResponse<Integer> sendVerifiedCode(String phone);

    BaseResponse<String> loginUseVerifiedCode(StudentLoginUseVerifiedCodeRequest studentLoginUseVerifiedCodeRequest, HttpServletRequest request);

    BaseResponse<Integer> logout(HttpServletRequest request);


}
