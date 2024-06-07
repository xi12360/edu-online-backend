package com.eduonline.backend.model.dto.forum;

import lombok.Data;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/8/29 16:45
 */
@Data
public class ReplyForumCommentRequest {
    private String commentId;
    private String articleId;
    private String userId;
    private String comment;
    private String otherId;

}
