package com.eduonline.backend.controller;

import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.common.ResultUtils;
import com.eduonline.backend.model.dto.sysmsg.SendSysMsgRequest;
import com.eduonline.backend.model.dto.user.AdminLoginRequest;
import com.eduonline.backend.model.entity.Admin;
import com.eduonline.backend.service.AdminService;
import com.eduonline.backend.service.MsgSystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/8/28 15:28
 */
@RestController
@RequestMapping("/admin")
@Slf4j
@CrossOrigin
public class AdminController {

    @Resource
    private AdminService adminService;

    @Resource
    private MsgSystemService msgSystemService;

    /**
     * 管理员登录
     * @param adminLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<String> adminLogin(@RequestBody AdminLoginRequest adminLoginRequest, HttpServletRequest request) {
        return adminService.login(adminLoginRequest, request);
    }

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    @GetMapping("/get/login")
    public BaseResponse<Admin> getLoginAdmin(HttpServletRequest request) {
        Admin admin = adminService.getLoginAdmin(request);
        return ResultUtils.success(admin);
    }

    /**
     * 管理员向用户发送系统消息
     * @param sendSysMsgRequest
     * @param request
     * @return
     */
    @PostMapping("/sysMsg")
    public BaseResponse<Integer> sendSysMsg(
            @RequestBody SendSysMsgRequest sendSysMsgRequest,
            HttpServletRequest request) {
        return msgSystemService.sendSysMsg(sendSysMsgRequest, request);
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public BaseResponse<Integer> logout(HttpServletRequest request) {
        return adminService.logout(request);
    }



}
