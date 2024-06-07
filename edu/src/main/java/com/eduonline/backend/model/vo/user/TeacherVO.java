package com.eduonline.backend.model.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/8/28 15:56
 */
@Data
public class TeacherVO {
    private String id;
    private String name;
    private String education;
    private String major;
    private String picPath;
    private int status;
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    private String introduction;
    private String phone;
    private int sex;
    private String email;
}
