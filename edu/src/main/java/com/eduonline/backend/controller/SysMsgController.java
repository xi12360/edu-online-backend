package com.eduonline.backend.controller;

import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.model.vo.msg.MsgSystemListVO;
import com.eduonline.backend.service.MsgSystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/9/1 10:43
 */
@RestController
@RequestMapping("/sysMsg")
@Slf4j
@CrossOrigin
public class SysMsgController {

    @Resource
    private MsgSystemService msgSystemService;

    /**
     * 用户取出未读消息
     *
     * @param userId
     * @param request
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<MsgSystemListVO> getMsgSystem(@RequestParam("userId") String userId,
                                                      HttpServletRequest request) {
        return msgSystemService.getMsgSystem(userId, request);
    }

    /**
     * 用户未读消息数量
     * @param userId
     * @param request
     * @return
     */
    @GetMapping("/getNum")
    public BaseResponse<Integer> getMsgSystemNum(@RequestParam("userId") String userId,
                                                        HttpServletRequest request) {
        return msgSystemService.getMsgSystemNum(userId, request);
    }

}
