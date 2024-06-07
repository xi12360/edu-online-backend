package com.eduonline.backend.model.vo.course;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/9/8 14:57
 */
@Data
public class CourseInfoListVO {
    private List<CourseInfoVO> courseInfoVOList;
    private int totalNum;
}
