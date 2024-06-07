package com.eduonline.backend.model.vo.msg;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: G
 * @time: 2023/9/11 15:29
 */
@Data
public class MsgSystemListVO {
    private List<MsgSystemVO> msgSystemVOList;
    private int totalNum;
}
