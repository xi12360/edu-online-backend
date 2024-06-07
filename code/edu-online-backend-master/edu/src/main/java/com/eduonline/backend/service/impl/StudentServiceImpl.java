package com.eduonline.backend.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.common.ErrorCode;
import com.eduonline.backend.common.ResultUtils;
import com.eduonline.backend.common.UserLoginMsg;
import com.eduonline.backend.exception.BusinessException;
import com.eduonline.backend.model.dto.user.*;
import com.eduonline.backend.model.entity.Admin;
import com.eduonline.backend.model.vo.user.StudentListVO;
import com.eduonline.backend.model.vo.user.StudentVO;
import com.eduonline.backend.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eduonline.backend.model.entity.Student;
import com.eduonline.backend.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.eduonline.backend.constant.UserConstant.*;
import static com.sun.javafx.font.FontResource.SALT;

/**
 * @author Acer
 * @description 针对表【student】的数据库操作Service实现
 * @createDate 2023-08-25 16:59:15
 */
@Service
@Slf4j
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
        implements StudentService {

    @Resource
    private RedisTemplate redisTemplate;


    /**
     * 账号密码登录
     *
     * @param studentService
     * @param request
     * @return
     */
    @Override
    public BaseResponse<String> login(StudentLoginRequest studentService, HttpServletRequest request) {
        String phone = studentService.getPhone();
        String password = studentService.getPassword();
        password = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getPhone, phone);
        Student student = getOne(wrapper);
        if (student == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "账户不存在");
        }
        if (!student.getPassword().equals(password)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "密码不正确");
        }
        if(student.getStatus() == 1) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "账号已冻结");
        }
        Student safeStudent = safeStudent(student);

        request.getSession().setAttribute(USER_LOGIN_STATE, safeStudent);
        request.getSession().setAttribute(STUDENT_ROLE, 1);
        request.getSession().setAttribute(ADMIN_ROLE, 0);
        request.getSession().setAttribute(TEACHER_ROLE, 0);

        //缓存
        UserLoginMsg userLoginMsg = new UserLoginMsg();
        userLoginMsg.setRole(1);
        userLoginMsg.setPhone(phone);
        String token = "student_" + RandomUtil.randomString(30);
        redisTemplate.opsForHash().put(USER_LOGIN_STATE, token, userLoginMsg); // 1表示学生
        redisTemplate.expire(STUDENT_ROLE, EXPIRE, TimeUnit.SECONDS); //过期时间

        return ResultUtils.success(token);
    }

    /**
     * 脱敏
     *
     * @param student
     * @return
     */
    private Student safeStudent(Student student) {
        student.setPassword("");
        return student;
    }

    /**
     * 注册
     *
     * @param studentRegisterRequest
     * @return
     */
    @Override
    public BaseResponse<Integer> register(StudentRegisterRequest studentRegisterRequest) {
        String password = studentRegisterRequest.getPassword();
        String checkPwd = studentRegisterRequest.getCheckPwd();
        String phone = studentRegisterRequest.getPhone();
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
            QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone", phone);
            long count = count(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 2. 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
            // 3. 分配 accessKey, secretKey
            String accessKey = DigestUtil.md5Hex(SALT + password + RandomUtil.randomNumbers(5));
            String secretKey = DigestUtil.md5Hex(SALT + password + RandomUtil.randomNumbers(8));
            // 4. 插入数据
            Student student = new Student();
            student.setPhone(phone);
            student.setPassword(encryptPassword);
//            user.setAccessKey(accessKey);
//            user.setSecretKey(secretKey);
            student.setId("student_" + RandomUtil.randomString(30));
            student.setUserName("student_" + RandomUtil.randomNumber());
            boolean saveResult = this.save(student);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
        }
        return ResultUtils.success(1);
    }

    /**
     * 修改
     *
     * @param studentModifyRequest
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> modify(StudentModifyRequest studentModifyRequest, HttpServletRequest request) {
        if (studentModifyRequest == null || studentModifyRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Student student = new Student();
        BeanUtils.copyProperties(studentModifyRequest, student);
        student.setPicImg(studentModifyRequest.getImgUrl());
        student.setUserName(studentModifyRequest.getName());
        //  字段值为null不会更新
        boolean result = updateById(student);
        return ResultUtils.success(result ? 1 : 0);
    }

    /**
     * 获取登录学生
     *
     * @param request
     * @return
     */
    @Override
    public Student getLoginStudent(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        Object role = request.getSession().getAttribute(STUDENT_ROLE);

        Integer currentRole = (Integer) role;
        if (userObj == null || currentRole == null
                || currentRole != 1) {
            return null;
        }

        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        Student currentStudent = (Student) userObj;
        String studentId = currentStudent.getId();
        currentStudent = this.getById(studentId);
        if (currentStudent == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentStudent;
    }

    /**
     * 管理员修改学生状态
     *
     * @param status
     * @param studentId
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> changeStatus(Integer status, String studentId, HttpServletRequest request) {
        if (studentId == null || studentId.equals("")) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "学生id有误");
        }
        LambdaUpdateWrapper<Student> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Student::getId, studentId)
                .set(Student::getStatus, status);
        boolean isSuccess = false;
        try {
            isSuccess = update(wrapper);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "数据库操作异常");
        }
        if (isSuccess) {
            return ResultUtils.success(1);
        } else {
            return ResultUtils.error(ErrorCode.OPERATION_ERROR, "参数有误");
        }
    }

    /**
     * 分页查询学生信息
     *
     * @param current
     * @param pageSize
     * @param request
     * @return
     */
    @Override
    public BaseResponse<StudentListVO> listStudents(Integer current, Integer pageSize, HttpServletRequest request) {
        Page<Student> page = query().page(new Page<>(current, pageSize));
        List<Student> studentList = page.getRecords();
        long total = page.getTotal();
        List<StudentVO> studentVOList;
        studentVOList = studentList.stream().map(student -> {
            StudentVO studentVO = new StudentVO();
            BeanUtils.copyProperties(student, studentVO);
            return studentVO;
        }).collect(Collectors.toList());
        StudentListVO studentListVO = new StudentListVO();
        studentListVO.setStudentVOList(studentVOList);
        studentListVO.setTotal(total);
        return ResultUtils.success(studentListVO);
    }

    /**
     * 发送验证码
     *
     * @param phone
     * @return
     */
    // todo 未完成第三方api的密钥
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

    /**
     * 验证码登录
     *
     * @param studentLoginUseVerifiedCodeRequest
     * @param request
     * @return
     */
    @Override
    public BaseResponse<String> loginUseVerifiedCode(StudentLoginUseVerifiedCodeRequest studentLoginUseVerifiedCodeRequest,
                                                     HttpServletRequest request) {
        String code = studentLoginUseVerifiedCodeRequest.getCode();
        String phone = studentLoginUseVerifiedCodeRequest.getPhone();
        Object codeObj = redisTemplate.opsForValue().get(phone);
        if (codeObj != null && codeObj.equals(code)) {
            LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Student::getPhone, phone);
            Student student = getOne(wrapper);

            if(student.getStatus() == 1) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "账号已冻结");
            }

            //新用户自动注册 密码为123456
            if(student == null) {
                student = new Student();
                student.setId("student_" + RandomUtil.randomString(30));
                student.setPhone(phone);
                student.setPassword(DigestUtils.md5DigestAsHex((SALT + "123456").getBytes()));
                student.setUserName("student_" + RandomUtil.randomString(10));
                save(student);
            }
            request.getSession().setAttribute(USER_LOGIN_STATE, safeStudent(student));
            request.getSession().setAttribute(STUDENT_ROLE, 1);
            redisTemplate.delete(phone);

            //缓存
            UserLoginMsg userLoginMsg = new UserLoginMsg();
            userLoginMsg.setRole(1);
            userLoginMsg.setPhone(phone);
            String token = "student_" + RandomUtil.randomString(30);
            redisTemplate.opsForHash().put(USER_LOGIN_STATE, token, userLoginMsg); // 1表示学生
            redisTemplate.expire(STUDENT_ROLE, EXPIRE, TimeUnit.SECONDS); //过期时间

            return ResultUtils.success(token);
        }
        throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "验证码不存在");
    }


    /**
     * 登出
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> logout(HttpServletRequest request) {
        request.removeAttribute(USER_LOGIN_STATE);
        if(request.getSession().getAttribute(STUDENT_ROLE) != null) {
            request.getSession().removeAttribute(STUDENT_ROLE);
        }

        return ResultUtils.success(1);
    }
}




