package com.eduonline.backend.model.dto.sysmsg;

import lombok.Data;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/8/29 14:58
 */
@Data
public class SendSysMsgRequest {
    private String text;
    private String toId;
}
