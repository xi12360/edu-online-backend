package com.eduonline.backend.model.dto.user;

import lombok.Data;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/8/28 14:25
 */
@Data
public class StudentLoginRequest {
    private String phone;
    private String password;
}
