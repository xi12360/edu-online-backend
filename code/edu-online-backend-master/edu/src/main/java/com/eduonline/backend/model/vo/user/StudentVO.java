package com.eduonline.backend.model.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: G
 * @time: 2023/8/28 15:51
 */
@Data
public class StudentVO {
    private String id;
    private String phone;
    private String email;
    private String userName;
    private Integer sex;
    private String grade;
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    private int status;
    private String picImg;
    private String major;

}
