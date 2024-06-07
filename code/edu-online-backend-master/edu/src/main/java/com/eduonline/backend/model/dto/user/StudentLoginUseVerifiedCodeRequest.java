package com.eduonline.backend.model.dto.user;

import lombok.Data;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/8/30 11:53
 */
@Data
public class StudentLoginUseVerifiedCodeRequest {
    private String phone;
    private String code;
}
