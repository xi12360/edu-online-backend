package com.eduonline.backend.model.vo.user;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/9/3 11:20
 */
@Data
public class TeacherListVO {
    private List<TeacherVO> teacherVOList;
    private long total;
}
