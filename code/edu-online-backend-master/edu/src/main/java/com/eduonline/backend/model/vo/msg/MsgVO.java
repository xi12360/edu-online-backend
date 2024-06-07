package com.eduonline.backend.model.vo.msg;

import lombok.Data;

import java.util.Date;

@Data
public class MsgVO {
    private String content;
    private String Id;
    private String toId;
    private int status;
    private Date addTime;
}
