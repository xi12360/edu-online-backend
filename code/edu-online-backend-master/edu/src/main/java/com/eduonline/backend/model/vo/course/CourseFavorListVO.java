package com.eduonline.backend.model.vo.course;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/9/12 16:05
 */
@Data
public class CourseFavorListVO {
    private List<CourseMostFavorVO> courseFavorListVO;
    private int totalNum;
}
