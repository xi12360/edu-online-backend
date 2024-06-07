package com.eduonline.backend.model.vo.msg;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/9/1 10:46
 */
@Data
public class MsgSystemVO {
    private String id;
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date addTime;
    private String content;
    private Integer status;
    private String toId;
}
