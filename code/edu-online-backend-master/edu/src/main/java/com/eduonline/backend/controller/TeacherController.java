package com.eduonline.backend.controller;

import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.common.ResultUtils;
import com.eduonline.backend.model.dto.user.StudentLoginUseVerifiedCodeRequest;
import com.eduonline.backend.model.dto.user.TeacherLoginRequest;
import com.eduonline.backend.model.dto.user.TeacherModifyRequest;
import com.eduonline.backend.model.dto.user.TeacherRegisterRequest;
import com.eduonline.backend.model.entity.Teacher;
import com.eduonline.backend.model.vo.user.TeacherListVO;
import com.eduonline.backend.model.vo.user.TeacherVO;
import com.eduonline.backend.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/8/28 15:26
 */
@RestController
@RequestMapping("/teacher")
@Slf4j
@CrossOrigin
public class TeacherController {
    @Resource
    private TeacherService teacherService;

    /**
     * 用户注册
     * @param teacherRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<Integer> teacherRegister(@RequestBody TeacherRegisterRequest teacherRegisterRequest) {
        return teacherService.register(teacherRegisterRequest);
    }

    /**
     * 用户登录
     * @param teacherLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<String> teacherLogin(@RequestBody TeacherLoginRequest teacherLoginRequest, HttpServletRequest request) {
        return teacherService.login(teacherLoginRequest, request);
    }

    /**
     * 用户修改信息
     * @param teacherModifyRequest
     * @param request
     * @return
     */
    @PostMapping("/modify")
    public BaseResponse<Integer> teacherModify(@RequestBody TeacherModifyRequest teacherModifyRequest, HttpServletRequest request) {
        return teacherService.modify(teacherModifyRequest, request);
    }

    /**
     * 获取当前登录用户
     * @param request
     * @return
     */
    // 此方法已不用
    @GetMapping("/get/login")
    public BaseResponse<TeacherVO> getLoginTeacher(HttpServletRequest request) {
        Teacher teacher = teacherService.getLoginTeacher(request);
        if(teacher == null) {
            return ResultUtils.success(null);
        }
        TeacherVO teacherVO = new TeacherVO();
        BeanUtils.copyProperties(teacher, teacher);
        return ResultUtils.success(teacherVO);
    }

    /**
     * 管理员修改教师状态
     * @param status
     * @param teacherId
     * @param request
     * @return
     */
    @GetMapping ("/status")
    public BaseResponse<Integer> changeStatus(
            @RequestParam(value = "status", defaultValue = "0")Integer status,
            @RequestParam("id") String teacherId,
            HttpServletRequest request) {
        return teacherService.changeStatus(status, teacherId, request);
    }

    /**
     * 分页查询教师信息
     * @param current
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping("/list")
    public BaseResponse<TeacherListVO> list(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            HttpServletRequest request) {
        return teacherService.listTeachers(current, pageSize, request);
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public BaseResponse<Integer> logout(HttpServletRequest request) {
        return teacherService.logout(request);
    }

    /**
     * 发送验证码
     * @param phone
     * @return
     */
    // todo 第三方服务已注释
    @GetMapping("/sendVerifiedCode")
    public BaseResponse<Integer> teacherSendVerifiedCode(@RequestParam("phone") String phone) {
        return teacherService.sendVerifiedCode(phone);
    }

    /**
     * 验证码登录
     *
     * @param studentLoginUseVerifiedCodeRequest
     * @return
     */
    @PostMapping("/login/verifiedCode")
    public BaseResponse<String> teacherLoginUseVerifiedCode(@RequestBody StudentLoginUseVerifiedCodeRequest studentLoginUseVerifiedCodeRequest,
                                                            HttpServletRequest request) {
        return teacherService.loginUseVerifiedCode(studentLoginUseVerifiedCodeRequest, request);
    }
}
