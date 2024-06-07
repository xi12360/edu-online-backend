package com.eduonline.backend.model.dto.user;

import lombok.Data;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/8/28 14:28
 */
@Data
public class StudentRegisterRequest {
    private String phone;
    private String password;
    private String checkPwd;
    private String email;
}
