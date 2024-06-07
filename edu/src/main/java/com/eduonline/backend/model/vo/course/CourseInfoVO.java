package com.eduonline.backend.model.vo.course;

import lombok.Data;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/9/8 14:58
 */
@Data
public class CourseInfoVO {
    private String id;
    private String courseId;
    private String videoLink;
    private String infoLink;
    private String content;
    private int chapterNum;
    private String intro;
    private String title;
}
