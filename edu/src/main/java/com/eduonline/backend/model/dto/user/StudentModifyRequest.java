package com.eduonline.backend.model.dto.user;

import lombok.Data;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/8/28 15:34
 */
@Data
public class StudentModifyRequest {
    private String id;
    private String phone;
    private String imgUrl;
    private String name;
    private String major;
    private String email;
    private int sex;


}
