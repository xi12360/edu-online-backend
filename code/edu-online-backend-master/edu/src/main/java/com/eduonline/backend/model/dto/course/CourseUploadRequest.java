package com.eduonline.backend.model.dto.course;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/8/29 15:50
 */
@Data
public class CourseUploadRequest {

    /**
     * 课程名
     */
    private String courseName;

    /**
     * 项目链接
     */
    private String subjectLink;

    /**
     * 原价格
     */
    private Integer sourcePrice;

    /**
     * 标题
     */
    private String title;

    /**
     * 封面LOGO
     */
    private String logo;

    /**
     * 结束时间
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endTime;

    /**
     * 教师id
     */
    private String teacherId;
}
