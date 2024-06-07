package com.eduonline.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.model.dto.sysmsg.SendSysMsgRequest;
import com.eduonline.backend.model.entity.MsgSystem;
import com.eduonline.backend.model.vo.msg.MsgSystemListVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author Acer
* @description 针对表【msg_system(系统消息)】的数据库操作Service
* @createDate 2023-08-25 16:59:15
 *
*/
public interface MsgSystemService extends IService<MsgSystem> {

    BaseResponse<Integer> sendSysMsg(SendSysMsgRequest sendSysMsgRequest, HttpServletRequest request);

    BaseResponse<MsgSystemListVO> getMsgSystem(String userId, HttpServletRequest request);

    BaseResponse<Integer> getMsgSystemNum(String userId, HttpServletRequest request);
}
