package com.eduonline.backend.model.vo.course;

import com.eduonline.backend.model.vo.course.CourseFavorVO;
import lombok.Data;

import java.util.List;
@Data
public class ListCourseFavorVO {
    private List<CourseFavorVO> courseFavorVO;
    private long totalNum;
}
