package com.eduonline.backend.controller;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.common.ErrorCode;
import com.eduonline.backend.common.ResultUtils;
import com.eduonline.backend.common.UserLoginMsg;
import com.eduonline.backend.exception.BusinessException;
import com.eduonline.backend.model.dto.user.StudentLoginUseVerifiedCodeRequest;
import com.eduonline.backend.model.entity.Admin;
import com.eduonline.backend.model.entity.Student;
import com.eduonline.backend.model.entity.Teacher;
import com.eduonline.backend.model.vo.user.UserVO;
import com.eduonline.backend.service.AdminService;
import com.eduonline.backend.service.StudentService;
import com.eduonline.backend.service.TeacherService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.eduonline.backend.constant.UserConstant.STUDENT_ROLE;
import static com.eduonline.backend.constant.UserConstant.USER_LOGIN_STATE;
import static com.sun.javafx.font.FontResource.SALT;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/9/1 20:27
 */
@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin
public class GetLoginUserController {

    @Resource
    private AdminService adminService;

    @Resource
    private StudentService studentService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    @GetMapping("/get/login")
    public BaseResponse<UserVO> getLoginUser(HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        if(token == null || token.equals("")) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token为空");
        }
        Object obj = redisTemplate.opsForHash().get(USER_LOGIN_STATE, token);
        UserLoginMsg userLoginMsg;
        UserVO userVO = new UserVO();
        try {
            userLoginMsg = (UserLoginMsg) obj;
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "转换类型异常");
        }
        String phone = userLoginMsg.getPhone();
        int role = userLoginMsg.getRole();
        if(role == 1) {
            LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Student::getPhone, phone);
            Student student = studentService.getOne(wrapper);
            userVO.setAccess("canStudent");
            userVO.setName(student.getUserName());
            userVO.setId(student.getId());
            userVO.setPicImg(student.getPicImg());
            userVO.setPhone(student.getPhone());
            userVO.setMajor(student.getMajor());
            userVO.setEmail(student.getEmail());
            userVO.setSex(student.getSex());
        } else if(role == 2) {
            LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Teacher::getPhone, phone);
            Teacher teacher = teacherService.getOne(wrapper);
            userVO.setAccess("canTeacher");
            userVO.setName(teacher.getName());
            userVO.setId(teacher.getId());
            userVO.setPicImg(teacher.getPicPath());
            userVO.setPhone(teacher.getPhone());
            userVO.setMajor(teacher.getMajor());
            userVO.setEmail(teacher.getEmail());
            userVO.setSex(teacher.getSex());
        } else if(role == 3) {
            LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Admin::getId, phone);
            Admin admin = adminService.getOne(wrapper);
            userVO.setAccess("canAdmin");
            userVO.setName(admin.getName());
            userVO.setId(admin.getId());
            userVO.setPicImg("");
            userVO.setPhone(admin.getPhone());
            userVO.setSex(0);
        }
        return ResultUtils.success(userVO);
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public BaseResponse<Integer> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token == null || token.equals("")) {
            return  ResultUtils.success(1);
        }
        try {
            redisTemplate.opsForHash().delete(USER_LOGIN_STATE, token);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "redis问题");
        }
        return ResultUtils.success(1);
    }

    /**
     * 发送验证码
     * @param phone
     * @return
     */
    // todo 第三方服务已注释
    @PostMapping("/sendVerifiedCode")
    public BaseResponse<Integer> studentSendVerifiedCode(@RequestBody String phone) {
        return studentService.sendVerifiedCode(phone);
    }




}
