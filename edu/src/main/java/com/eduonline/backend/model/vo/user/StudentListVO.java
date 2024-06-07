package com.eduonline.backend.model.vo.user;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: G
 * @time: 2023/9/3 11:18
 */
@Data
public class StudentListVO {
    private List<StudentVO> studentVOList;
    private Long total;
}
