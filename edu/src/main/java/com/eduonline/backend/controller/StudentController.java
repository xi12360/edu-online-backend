package com.eduonline.backend.controller;

import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.common.ResultUtils;
import com.eduonline.backend.model.dto.user.*;
import com.eduonline.backend.model.entity.Student;
import com.eduonline.backend.model.vo.user.StudentListVO;
import com.eduonline.backend.model.vo.user.StudentVO;
import com.eduonline.backend.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/8/28 11:02
 */
@RestController
@RequestMapping("/student")
@Slf4j
@CrossOrigin
public class StudentController {

    @Resource
    private StudentService studentService;

    /**
     * 用户登录
     *
     * @param studentLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<String> studentLogin(@RequestBody StudentLoginRequest studentLoginRequest, HttpServletRequest request) {
        return studentService.login(studentLoginRequest, request);
    }

    /**
     * 发送验证码
     * @param phone
     * @return
     */
    // todo 第三方服务已注释
    @GetMapping("/sendVerifiedCode")
    public BaseResponse<Integer> studentSendVerifiedCode(@RequestParam("phone") String phone) {
        return studentService.sendVerifiedCode(phone);
    }

    /**
     * 验证码登录
     *
     * @param studentLoginUseVerifiedCodeRequest
     * @return
     */
    @PostMapping("/login/verifiedCode")
    public BaseResponse<String> studentLoginUseVerifiedCode(@RequestBody StudentLoginUseVerifiedCodeRequest studentLoginUseVerifiedCodeRequest,
                                                             HttpServletRequest request) {
        return studentService.loginUseVerifiedCode(studentLoginUseVerifiedCodeRequest, request);
    }

    /**
     * 用户注册
     *
     * @param studentRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<Integer> studentRegister(@RequestBody StudentRegisterRequest studentRegisterRequest) {
        return studentService.register(studentRegisterRequest);
    }

    /**
     * 用户修改信息
     *
     * @param studentModifyRequest
     * @param request
     * @return
     */
    @PostMapping("/modify")
    public BaseResponse<Integer> studentModify(@RequestBody StudentModifyRequest studentModifyRequest, HttpServletRequest request) {
        return studentService.modify(studentModifyRequest, request);
    }

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    // 此方法已不用
    @GetMapping("/get/login")
    public BaseResponse<StudentVO> getLoginStudent(HttpServletRequest request) {
        Student student = studentService.getLoginStudent(request);
        if(student == null) {
            return ResultUtils.success(null);
        }
        StudentVO studentVO = new StudentVO();
        BeanUtils.copyProperties(student, studentVO);
        return ResultUtils.success(studentVO);
    }

    /**
     * 管理员修改学生状态
     *
     * @param status
     * @param studentId
     * @param request
     * @return
     */
    @GetMapping("/status")
    public BaseResponse<Integer> changeStatus(
            @RequestParam(value = "status", defaultValue = "0") Integer status,
            @RequestParam("id") String studentId,
            HttpServletRequest request) {
        return studentService.changeStatus(status, studentId, request);
    }

    /**
     * 分页查询学生信息
     *
     * @param current
     * @param pageSize
     * @param request
     * @return
     */
    // todo 改变返回类加总数
    @GetMapping("/list")
    public BaseResponse<StudentListVO> list(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            HttpServletRequest request) {
        return studentService.listStudents(current, pageSize, request);
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public BaseResponse<Integer> logout(HttpServletRequest request) {
        return studentService.logout(request);
    }

}
