package com.eduonline.backend.model.vo.course;

import com.eduonline.backend.model.vo.user.TeacherVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/8/29 16:06
 */
@Data
public class CourseVO {
    /**
     * 课程id
     */
    private String courseId;

    /**
     * 课程名
     */
    private String courseName;

    /**
     * 状态
     */
    private int status;

    /**
     * 项目链接
     */
    private String subjectLink;

    /**
     * 原价格
     */
    private int sourcePrice;

    /**
     * 当前价格
     */
    private int currentPrice;

    /**
     * 标题
     */
    private String title;

    /**
     * 课程号
     */
    private String lessonNum;

    /**
     * 封面LOGO
     */
    private String logo;

    /**
     * 更新时间
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    /**
     * 购买数量
     */
    private int buyCount;

    /**
     * 浏览数量
     */
    private int clickNum;

    /**
     * 结束时间
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endTime;

    /**
     * 教师id
     */
    private TeacherVO teacherVO;
}
