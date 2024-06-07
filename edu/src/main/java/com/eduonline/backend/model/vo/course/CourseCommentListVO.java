package com.eduonline.backend.model.vo.course;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/9/1 22:30
 */
@Data
public class CourseCommentListVO {
    private List<CourseCommentVO> courseCommentVOList;
    private Long totalNum;
}
