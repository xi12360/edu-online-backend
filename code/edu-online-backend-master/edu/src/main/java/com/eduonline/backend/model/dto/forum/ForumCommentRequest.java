package com.eduonline.backend.model.dto.forum;

import lombok.Data;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/8/31 10:46
 */
@Data
public class ForumCommentRequest {
    private String articleId;
    private String userId;
    private String comment;
}
