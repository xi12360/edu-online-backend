package com.eduonline.backend.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/8/28 14:20
 */
@Data
@ApiModel(value = "管理员登录请求类", description = "管理员登录请求类")
public class AdminLoginRequest {
    @ApiModelProperty("管理员id")
    private String phone;

    private String password;

}
