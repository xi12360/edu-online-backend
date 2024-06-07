package com.eduonline.backend.model.dto.user;

import lombok.Data;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/8/28 14:27
 */
@Data
public class TeacherLoginRequest {
    private String phone;
    private String password;
}
