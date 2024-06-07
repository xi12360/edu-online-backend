package com.eduonline.backend.model.vo.course;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/9/1 23:29
 */
@Data
public class CourseListVO {
    private List<CourseVO> courseVOList;
    private long totalNum;
}
