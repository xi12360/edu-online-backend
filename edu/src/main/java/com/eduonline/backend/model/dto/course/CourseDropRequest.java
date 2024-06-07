package com.eduonline.backend.model.dto.course;

import lombok.Data;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/9/5 10:44
 */
@Data
public class CourseDropRequest {
    private String courseId;
    private String message;
    private String teacherId;
}
