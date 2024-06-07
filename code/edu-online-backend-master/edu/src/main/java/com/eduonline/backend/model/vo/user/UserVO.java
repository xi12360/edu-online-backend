package com.eduonline.backend.model.vo.user;

import lombok.Data;

/**
 * @description:
 * @author: G
 * @time: 2023/9/1 20:30
 */
@Data
public class UserVO {
    private String access;
    private String id;
    private String name;
    private String picImg;
    private String phone;
    private String email;
    private String major;
    private int sex;
}
