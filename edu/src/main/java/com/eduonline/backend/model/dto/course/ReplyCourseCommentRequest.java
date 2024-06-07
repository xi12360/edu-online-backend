package com.eduonline.backend.model.dto.course;

import lombok.Data;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/8/29 16:46
 */
@Data
public class ReplyCourseCommentRequest {
    private String commentId;//父级ID
    private String courseId;
    private String userId;
    private String comment;
    private String otherId;//就是TOID
}
