package com.eduonline.backend.model.dto.course;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/8/29 16:29
 */
@Data
public class CourseTestUploadRequest {
    private String courseId;
    private List<CourseTestRequest> courseTestRequestList;
}
