package com.eduonline.backend.model.vo.article;

import com.eduonline.backend.model.vo.course.CourseVO;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/9/1 23:01
 */
@Data
public class ListAuditCourseVO {
    private List<CourseVO> auditCourseVOList;
    private Long totalNum;
}
