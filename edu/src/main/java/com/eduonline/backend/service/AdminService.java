package com.eduonline.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.model.dto.user.AdminLoginRequest;
import com.eduonline.backend.model.entity.Admin;

import javax.servlet.http.HttpServletRequest;

/**
* @author Acer
* @description 针对表【admin(管理员)】的数据库操作Service
* @createDate 2023-08-28 10:41:32
*/
public interface AdminService extends IService<Admin> {

    BaseResponse<String> login(AdminLoginRequest adminLoginRequest, HttpServletRequest httpServletRequest);

    BaseResponse<Integer> logout(HttpServletRequest request);

    Admin getLoginAdmin(HttpServletRequest request);
}
