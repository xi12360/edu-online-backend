package com.eduonline.backend.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.common.ErrorCode;
import com.eduonline.backend.common.ResultUtils;
import com.eduonline.backend.common.UserLoginMsg;
import com.eduonline.backend.exception.BusinessException;
import com.eduonline.backend.mapper.AdminMapper;
import com.eduonline.backend.model.dto.user.AdminLoginRequest;
import com.eduonline.backend.model.entity.Admin;
import com.eduonline.backend.model.entity.Student;
import com.eduonline.backend.service.AdminService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.concurrent.TimeUnit;

import static com.eduonline.backend.constant.UserConstant.*;
import static com.sun.javafx.font.FontResource.SALT;

/**
* @author Acer
* @description 针对表【admin(管理员)】的数据库操作Service实现
* @createDate 2023-08-28 10:41:32
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 登录
     * @param adminLoginRequest
     * @param request
     * @return
     */
    @Override
    public BaseResponse<String> login(AdminLoginRequest adminLoginRequest, HttpServletRequest request) {
        if(adminLoginRequest == null || adminLoginRequest.getPhone() == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"参数为空");
        }
        String id = adminLoginRequest.getPhone();
        String password = adminLoginRequest.getPassword();
        password = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        Admin admin = getById(id);
        if(admin == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"账户不存在");
        }
        if(!admin.getPassword().equals(password)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR,"密码不正确");
        }
        admin.setPassword("");
        //设置登录状态
        request.getSession().setAttribute(USER_LOGIN_STATE, admin);
        request.getSession().setAttribute(ADMIN_ROLE, 1);
        request.getSession().setAttribute(TEACHER_ROLE, 0);
        request.getSession().setAttribute(STUDENT_ROLE, 0);

        //缓存
        UserLoginMsg userLoginMsg = new UserLoginMsg();
        userLoginMsg.setRole(3);
        userLoginMsg.setPhone(id);
        String token = "admin_" + RandomUtil.randomString(30);
        redisTemplate.opsForHash().put(USER_LOGIN_STATE, token, userLoginMsg); // 3表示管理员
        redisTemplate.expire(STUDENT_ROLE, EXPIRE, TimeUnit.SECONDS); //过期时间

        return ResultUtils.success(token);
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> logout(HttpServletRequest request) {
        request.removeAttribute(USER_LOGIN_STATE);
        if(request.getSession().getAttribute(ADMIN_ROLE) != null) {
            request.getSession().removeAttribute(ADMIN_ROLE);
        }
        return ResultUtils.success(1);
    }

    @Override
    public Admin getLoginAdmin(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        Object role = request.getSession().getAttribute(ADMIN_ROLE);

        Integer currentRole = (Integer) role;
        if (userObj == null || currentRole == null
                || currentRole != 1) {
            return null;
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        Admin currentAdmin = (Admin) userObj;
        String adminId = currentAdmin.getId();
        currentAdmin = this.getById(adminId);
        if (currentAdmin == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        currentAdmin.setPassword("");
        return currentAdmin;
    }
}




