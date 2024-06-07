package com.eduonline.backend.model.dto.course;

import lombok.Data;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/8/29 16:25
 */
@Data
public class CourseCommentRequest {
    private String courseId;
    private String userId;
    private String content;
}
