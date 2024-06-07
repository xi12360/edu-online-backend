package com.eduonline.backend.model.vo.course;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/9/12 16:06
 */
@Data
public class CourseClickListVO {
    private List<CourseClickVO> courseClickListVO;
    private int totalNum;
}
