package com.eduonline.backend.model.vo.course;

import lombok.Data;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/9/12 16:12
 */
@Data
public class CourseMostFavorVO {
    private String courseId;
    private int praiseNum;
    private String title;
}
