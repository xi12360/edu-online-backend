package com.eduonline.backend.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/9/6 11:17
 */
@Data
public class UserLoginMsg implements Serializable {
    private int role;
    private String phone;
}
