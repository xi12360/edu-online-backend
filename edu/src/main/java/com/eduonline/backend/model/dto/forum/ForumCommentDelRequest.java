package com.eduonline.backend.model.dto.forum;

import lombok.Data;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/8/31 9:14
 */
@Data
public class ForumCommentDelRequest {
    private String commentID;
    private String userId;
}
