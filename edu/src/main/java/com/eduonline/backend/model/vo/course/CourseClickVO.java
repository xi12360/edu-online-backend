package com.eduonline.backend.model.vo.course;

import lombok.Data;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/9/12 16:08
 */
@Data
public class CourseClickVO {
    private String courseId;
    private int clickNum;
    private String title;
}
