package com.eduonline.backend.model.dto.course;

import lombok.Data;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/9/9 15:33
 */
@Data
public class CourseInfoUploadRequest {
    private String courseId;
    private String videoLink;
    private String infoLink;
    private String content;
    private String intro;
    private String title;
}
