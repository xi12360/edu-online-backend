package com.eduonline.backend.model.vo.course;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/9/4 14:39
 */
@Data
public class CourseTestListVO {
    private List<CourseTestVO> courseTestVOList;
    private int totalNum;
}
