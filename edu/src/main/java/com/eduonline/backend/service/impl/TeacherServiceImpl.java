package com.eduonline.backend.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.common.ErrorCode;
import com.eduonline.backend.common.ResultUtils;
import com.eduonline.backend.common.UserLoginMsg;
import com.eduonline.backend.exception.BusinessException;
import com.eduonline.backend.model.dto.user.StudentLoginUseVerifiedCodeRequest;
import com.eduonline.backend.model.dto.user.TeacherLoginRequest;
import com.eduonline.backend.model.dto.user.TeacherModifyRequest;
import com.eduonline.backend.model.dto.user.TeacherRegisterRequest;
import com.eduonline.backend.model.entity.Admin;
import com.eduonline.backend.model.entity.Student;
import com.eduonline.backend.model.vo.user.TeacherListVO;
import com.eduonline.backend.model.vo.user.TeacherVO;
import com.eduonline.backend.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eduonline.backend.model.entity.Teacher;
import com.eduonline.backend.mapper.TeacherMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.eduonline.backend.constant.UserConstant.*;
import static com.sun.javafx.font.FontResource.SALT;

/**
 * @author Acer
 * @description 针对表【teacher】的数据库操作Service实现
 * @createDate 2023-08-25 16:59:15
 */
@Service
@Slf4j
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
        implements TeacherService {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 登录
     *
     * @param teacherLoginRequest
     * @param request
     * @return
     */
    @Override
    public BaseResponse<String> login(TeacherLoginRequest teacherLoginRequest, HttpServletRequest request) {
        String phone = teacherLoginRequest.getPhone();
        String password = teacherLoginRequest.getPassword();
        password = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getPhone, phone);
        Teacher teacher = getOne(wrapper);
        if (teacher == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "账户不存在");
        }
        if (!teacher.getPassword().equals(password)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "密码不正确");
        }
        if(teacher.getStatus() == 1) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "账号已冻结");
        }
        Teacher safeTeacher = safeTeacher(teacher);

        request.getSession().setAttribute(USER_LOGIN_STATE, safeTeacher);
        request.getSession().setAttribute(TEACHER_ROLE, 1);
        request.getSession().setAttribute(STUDENT_ROLE, 0);
        request.getSession().setAttribute(ADMIN_ROLE, 0);

        //缓存
        UserLoginMsg userLoginMsg = new UserLoginMsg();
        userLoginMsg.setRole(2);
        userLoginMsg.setPhone(phone);
        String token = "teacher_" + RandomUtil.randomString(30);
        redisTemplate.opsForHash().put(USER_LOGIN_STATE, token, userLoginMsg); // 2表示老师
        redisTemplate.expire(STUDENT_ROLE, EXPIRE, TimeUnit.SECONDS); //过期时间

        return ResultUtils.success(token);
    }

    /**
     * 脱敏除去密码
     * @param teacher
     * @return
     */
    private Teacher safeTeacher(Teacher teacher) {
        teacher.setPassword("");
        return teacher;
    }

    /**
     * 注册
     * @param teacherRegisterRequest
     * @return
     */
    @Override
    public BaseResponse<Integer> register(TeacherRegisterRequest teacherRegisterRequest) {
        String password = teacherRegisterRequest.getPassword();
        String checkPwd = teacherRegisterRequest.getCheckPwd();
        String phone = teacherRegisterRequest.getPhone();
        String email = teacherRegisterRequest.getEmail();
        //校验
        if (StringUtils.isAnyBlank(password, checkPwd, phone)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "有参数为空");
        }
        if (password.length() < 6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码过短");
        }
        if (!password.equals(checkPwd)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入密码不一致");
        }

        synchronized (phone.intern()) {
            // 查询用户是否存在
            QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone", phone);
            long count = count(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
            // 插入数据
            Teacher teacher = new Teacher();
            teacher.setPhone(phone);
            teacher.setPassword(encryptPassword);
            teacher.setId("teacher_" + RandomUtil.randomString(30));
            teacher.setName("教师");
            teacher.setEmail(email);
            teacher.setEducation("默认");
            teacher.setSex(0);
            teacher.setMajor("undefined");
            teacher.setIntroduction("新注册的教师");
            teacher.setCreateTime(new Date());
            teacher.setUpdateTime(new Date());
            boolean saveResult = this.save(teacher);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
        }
        return ResultUtils.success(1);
    }

    /**
     * 修改
     * @param teacherModifyRequest
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> modify(TeacherModifyRequest teacherModifyRequest, HttpServletRequest request) {
        if (teacherModifyRequest == null || teacherModifyRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacherModifyRequest, teacher);
        teacher.setPicPath(teacherModifyRequest.getImgUrl());
        //  字段值为null不会更新
        boolean result = updateById(teacher);
        return ResultUtils.success(result ? 1 : 0);
    }

    /**
     * 获取登录用户
     * @param request
     * @return
     */
    @Override
    public Teacher getLoginTeacher(HttpServletRequest request) {
        // 先判断是否已登录
        Object currentObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        Object role = request.getSession().getAttribute(TEACHER_ROLE);
        Integer currentRole = (Integer) role;
        if (currentObj == null || currentRole == null
                || currentRole != 1) {
            return null;
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        Teacher currentTeacher = (Teacher)  currentObj;
        String teacherId = currentTeacher.getId();
        currentTeacher = this.getById(teacherId);
        if (currentTeacher == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentTeacher;
    }

    /**
     * 更改教师状态
     * @param status
     * @param teacherId
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> changeStatus(Integer status, String teacherId, HttpServletRequest request) {
        if(teacherId == null || teacherId.equals("")) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "教师id有误");
        }
        LambdaUpdateWrapper<Teacher> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Teacher::getId, teacherId)
                .set(Teacher::getStatus, status);
        boolean isSuccess = false;
        try {
            isSuccess = update(wrapper);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"数据库操作异常");
        }
        if(isSuccess) {
            return ResultUtils.success(1);
        } else {
            return ResultUtils.error(ErrorCode.OPERATION_ERROR, "参数有误");
        }
    }


    /**
     * 分页查询教师信息
     *
     * @param current
     * @param pageSize
     * @param request
     * @return
     */
    @Override
    public BaseResponse<TeacherListVO> listTeachers(Integer current, Integer pageSize, HttpServletRequest request) {
        Page<Teacher> page = query().page(new Page<>(current, pageSize));
        List<Teacher> teacherList = page.getRecords();
        long total = page.getTotal();
        List<TeacherVO> teacherVOList;
        teacherVOList = teacherList.stream().map(teacher -> {
            TeacherVO teacherVO = new TeacherVO();
            BeanUtils.copyProperties(teacher, teacherVO);
            return teacherVO;
        }).collect(Collectors.toList());
        TeacherListVO teacherListVO = new TeacherListVO();
        teacherListVO.setTeacherVOList(teacherVOList);
        teacherListVO.setTotal(total);
        return ResultUtils.success(teacherListVO);
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> logout(HttpServletRequest request) {
        request.removeAttribute(USER_LOGIN_STATE);
        if(request.getSession().getAttribute(TEACHER_ROLE) != null) {
            request.getSession().removeAttribute(TEACHER_ROLE);
        }


        return ResultUtils.success(1);
    }

    @Override
    public BaseResponse<Integer> sendVerifiedCode(String phone) {
        if (phone == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "电话不能为空");
        }
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getPhone, phone);

        String code = RandomUtil.randomString(6);
        log.info("验证码为:" + code);

        //调用阿里云api发短信
//            try {
//                SMSUtils.sendMessage("","",phone,code);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }

        //验证码缓存到redis中
        redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
        System.out.println(redisTemplate.opsForValue().get(phone).toString());

        return ResultUtils.success(1);
    }

    @Override
    public BaseResponse<String> loginUseVerifiedCode(StudentLoginUseVerifiedCodeRequest studentLoginUseVerifiedCodeRequest, HttpServletRequest request) {
        String code = studentLoginUseVerifiedCodeRequest.getCode();
        String phone = studentLoginUseVerifiedCodeRequest.getPhone();
        Object codeObj = redisTemplate.opsForValue().get(phone);
        if (codeObj != null && codeObj.equals(code)) {
            LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Teacher::getPhone, phone);
            Teacher teacher = getOne(wrapper);
            if(teacher == null) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "请先注册");
            }

            if(teacher.getStatus() == 1) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "账号已冻结");
            }

            request.getSession().setAttribute(USER_LOGIN_STATE, safeTeacher(teacher));
            request.getSession().setAttribute(STUDENT_ROLE, 1);
            redisTemplate.delete(phone);

            //缓存
            UserLoginMsg userLoginMsg = new UserLoginMsg();
            userLoginMsg.setRole(2);
            userLoginMsg.setPhone(phone);
            String token = "teacher_" + RandomUtil.randomString(30);
            redisTemplate.opsForHash().put(USER_LOGIN_STATE, token, userLoginMsg); // 2表示老师
            redisTemplate.expire(STUDENT_ROLE, EXPIRE, TimeUnit.SECONDS); //过期时间

            return ResultUtils.success(token);
        }
        throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "验证码不存在");
    }


}





