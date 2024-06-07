package com.eduonline.backend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.common.ErrorCode;
import com.eduonline.backend.common.ResultUtils;
import com.eduonline.backend.exception.BusinessException;
import com.eduonline.backend.model.dto.sysmsg.SendSysMsgRequest;
import com.eduonline.backend.model.vo.msg.MsgSystemListVO;
import com.eduonline.backend.model.vo.msg.MsgSystemVO;
import com.eduonline.backend.service.MsgSystemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eduonline.backend.model.entity.MsgSystem;
import com.eduonline.backend.mapper.MsgSystemMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Acer
 * @description 针对表【msg_system(系统消息)】的数据库操作Service实现
 * @createDate 2023-08-25 16:59:15
 */
@Service
public class MsgSystemServiceImpl extends ServiceImpl<MsgSystemMapper, MsgSystem>
        implements MsgSystemService {

    /**
     * 管理员或其他服务向用户发送系统消息
     *
     * @param sendSysMsgRequest
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> sendSysMsg(SendSysMsgRequest sendSysMsgRequest, HttpServletRequest request) {
        MsgSystem msgSystem = new MsgSystem();
        String toId = sendSysMsgRequest.getToId();
        String text = sendSysMsgRequest.getText();
        msgSystem.setId("msg_system" + RandomUtil.randomString(30));
        msgSystem.setToId(toId);
        msgSystem.setContent(text);
        try {
            save(msgSystem);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "操作数据库出现错误");
        }
        return ResultUtils.success(1);
    }

    /**
     * 接收系统消息
     *
     * @param userId
     * @param request
     * @return
     */
    @Override
    public BaseResponse<MsgSystemListVO> getMsgSystem(String userId, HttpServletRequest request) {
        //取出未读消息
        LambdaQueryWrapper<MsgSystem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MsgSystem::getToId, userId)
                .eq(MsgSystem::getStatus, 0);
        List<MsgSystem> msgList = list(wrapper);
        List<MsgSystemVO> msgSystemVOList;
        msgSystemVOList = msgList.stream().map(msgSystem -> {
            MsgSystemVO msgSystemVO = new MsgSystemVO();
            BeanUtil.copyProperties(msgSystem, msgSystemVO);
            return msgSystemVO;
        }).collect(Collectors.toList());

        MsgSystemListVO msgSystemListVO = new MsgSystemListVO();
        msgSystemListVO.setMsgSystemVOList(msgSystemVOList);
        msgSystemListVO.setTotalNum(msgSystemListVO.getTotalNum());

        //更新状态为已读
        List<MsgSystem> updatedMsgList = new ArrayList<>();
        msgList.forEach(msgSystem -> {
            msgSystem.setStatus(1);
            updatedMsgList.add(msgSystem);
        });
        updateBatchById(updatedMsgList);

        return ResultUtils.success(msgSystemListVO);
    }

    /**
     * 未读系统消息数量
     * @param userId
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> getMsgSystemNum(String userId, HttpServletRequest request) {
        //取出未读消息
        LambdaQueryWrapper<MsgSystem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MsgSystem::getToId, userId)
                .eq(MsgSystem::getStatus, 0);
        long count = count(wrapper);
        return ResultUtils.success((int)count);
    }


}




